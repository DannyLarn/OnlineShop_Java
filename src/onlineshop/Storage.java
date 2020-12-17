/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineshop;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author dnyyy
 */
public class Storage extends Table {

    private final List<StorageElement> allProducts;
    private final List<StorageElement> displayedProducts;
    private Cart cart;
    private Wishlist whishlist;
    private final String filename = "./src/onlineshop/Files/storage.json";
    
    /**
     * Creates new from StoragePanel
     */
    //
    // constructor:
    public Storage() {
        allProducts = new ArrayList();
        displayedProducts = new ArrayList();
    }

    public void build(Cart cart, Wishlist whishlist) {
        this.cart = cart;
        this.whishlist = whishlist;
        readProductsFromFile(filename);
    }
    
    private void readProductsFromFile(String filepath) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filepath));
            JSONArray storageList = (JSONArray) jsonObject.get("storage");
            Iterator<JSONObject> it = storageList.iterator();
            
            while (it.hasNext()) {
                JSONObject prod = (JSONObject) it.next();

                int id = Integer.parseInt(prod.get("id").toString());
                String name = prod.get("name").toString();
                int price = Integer.parseInt(prod.get("price").toString());
                String category = prod.get("category").toString();
                int available = Integer.parseInt(prod.get("available").toString());
                
                allProducts.add(new StorageElement(cart, whishlist));
                addRow(id, name, price, category, available, allProducts);
            }
            
        } catch (Exception e) {
            throwMessage(e, "Fajl beolvasas", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void saveStorage() {
        JSONArray wishlistList = new JSONArray();
        
        for (StorageElement element : allProducts) {
            Product product = element.getProductPanel();
            JSONObject obj = new JSONObject();
            
            obj.put("id", product.getId());
            obj.put("name", product.getName());
            obj.put("price", product.getPrice());
            obj.put("category", product.getCategory());
            obj.put("available", product.getAvailable());
            
            wishlistList.add(obj);
        }
        JSONObject result = new JSONObject();
        result.put("storage", wishlistList);
        
        FileWriter file;
        try {
            file = new FileWriter("./src/onlineshop/Files/storage.json");
            file.write(result.toJSONString());
            file.close();
        } catch(IOException e) {
            throwError(e, "Fajl kiiras problema", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void throwError(Exception e, String errorTitle, int messageType) {
        JOptionPane optionPane = new JOptionPane(e.getMessage(), messageType);
        JDialog dialog = optionPane.createDialog(errorTitle);
        dialog.setLocationByPlatform(true);
        dialog.setLocationRelativeTo(this);
        dialog.setAlwaysOnTop(true); // to show top of all other application
        dialog.setVisible(true); // to visible the dialog
    }
    
    private void search(String search, String type) {
        displayedProducts.removeAll(displayedProducts);
        resetTable();
        
        for (StorageElement panel : allProducts) {
            Product prod = panel.getProductPanel();
            if ((type.equals("name") && prod.searchByName(search)) || (type.equals("category") && prod.searchByCategory(search))) {
                displayedProducts.add(panel);
                addRow(prod.getId(), prod.getName(), prod.getPrice(), prod.getCategory(), prod.getAvailable(), displayedProducts);
            }
        }
    }
        
    private Product find(int id) {
        for (StorageElement element : allProducts) {
            if (element.getProductPanel().find(id) != null) {
                return element.getProductPanel();
            }
        }
        return null;
    }

    private void writeTotal(List<Product> products) {
        JSONArray totalList = new JSONArray();
        
        for (Product product : products) {
            JSONObject obj = new JSONObject();
            
            obj.put("id", product.getId());
            obj.put("name", product.getName());
            obj.put("price", product.getPrice());
            obj.put("category", product.getCategory());
            obj.put("available", product.getAvailable());
            
            totalList.add(obj);
        }
        JSONObject sum = new JSONObject();
        sum.put("total", cart.getTotal());
        totalList.add(sum);
        
        JSONObject result = new JSONObject();
        result.put("total", totalList);
        
        FileWriter file;
        try {
            file = new FileWriter("./src/onlineshop/Files/total.json");
            file.write(result.toJSONString());
            file.close();
        } catch(IOException e) {
            throwError(e, "Fajl kiiras problema", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void purchase(List<Product> products) {
        writeTotal(products);
        for (Product product : products) {
            Product prod = find(product.getId());
            if (prod != null) {
                prod.setAvailable(prod.getAvailable() - 1);
            }
        }
        saveStorage();
    }
    
    public void searchByName(String search) {
        search(search, "name");
    }
    
    public void searchByCategory(String search) {
        search(search, "category");
    }
                   
}
