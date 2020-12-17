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
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author dnyyy
 */
public class Storage extends Table {
    
    // variables:
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
    // set the actual cart and wishlist and read datas from file
    public void build(Cart cart, Wishlist whishlist) {
        this.cart = cart;
        this.whishlist = whishlist;
        readProductsFromFile(filename);
    }

    // return a Product if the given id is in the Storage otherwise returns null
    private Product find(int id) {
        for (StorageElement element : allProducts) {
            if (element.getProductPanel().find(id) != null) {
                return element.getProductPanel();
            }
        }
        return null;
    }
    
    // calls the appropriate function by type and search for Products
    private void search(String search, String type) {
        displayedProducts.removeAll(displayedProducts);
        resetTable();
        
        for (StorageElement panel : allProducts) {
            Product prod = panel.getProductPanel();
            if ((type.equals("name") && prod.searchByName(search)) || (type.equals("category") && prod.searchByCategory(search))) {
                displayedProducts.add(panel);
                addNewRow(prod.getId(), prod.getName(), prod.getPrice(), prod.getCategory(), prod.getAvailable(), displayedProducts);
            }
        }
    }

    // calls the main search function set by name
    public void searchByName(String search) {
        search(search, "name");
    }
    
    // calls the main search function set by category
    public void searchByCategory(String search) {
        search(search, "category");
    }

    // removes the given elements from storage if the user buy something
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
    
    // read Product's datas from json file and set the Tables's value
    private void readProductsFromFile(String filepath) {
        // declare the parser
        JSONParser parser = new JSONParser();
        try {
            // read file into a json object with parser
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filepath));
            JSONArray storageList = (JSONArray) jsonObject.get("storage");
            Iterator<JSONObject> it = storageList.iterator();
            
            // filter the json file and add datas to storage
            while (it.hasNext()) {
                JSONObject prod = (JSONObject) it.next();

                int id = Integer.parseInt(prod.get("id").toString());
                String name = prod.get("name").toString();
                int price = Integer.parseInt(prod.get("price").toString());
                String category = prod.get("category").toString();
                int available = Integer.parseInt(prod.get("available").toString());
                
                // add tot storage
                allProducts.add(new StorageElement(cart, whishlist));
                addNewRow(id, name, price, category, available, allProducts);
            }
            
        } catch (Exception e) {
            throwMessage(e, "Fajl beolvasas", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // save datas of Storage into the right json file
    public void saveStorage() {
        // prepare json
        JSONArray wishlistList = new JSONArray();
        
        for (StorageElement element : allProducts) {
            // get products from StorageElement
            Product product = element.getProductPanel();
            // write prepared json object into file
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
        
        // write json obj into file
        FileWriter file;
        try {
            file = new FileWriter("./src/onlineshop/Files/storage.json");
            file.write(result.toJSONString());
            file.close();
        } catch(IOException e) {
            throwMessage(e, "Fajl kiiras problema", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // write the purchased products and its total intot a json file
    private void writeTotal(List<Product> products) {
        // prepare json
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
        
        // write prepared json object into file
        FileWriter file;
        try {
            file = new FileWriter("./src/onlineshop/Files/total.json");
            file.write(result.toJSONString());
            file.close();
        } catch(IOException e) {
            throwMessage(e, "Fajl kiiras problema", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // add a new row to the table and the given list by the appropriate product parameters
    public void addNewRow(int id, String name, int price, String category, int available, List<StorageElement> list) {
        
        StorageElement lastElement = list.get(list.size() - 1);
        lastElement.setLabels(id, name, price, category, available);
        addRowSettings(list.size());
        // verifying how many elements the list has
        if (list.size() == 1) lastElement.setLocation(margin, 6);
        else lastElement.setLocation(margin, list.get(list.size() - 2).getY() + height + margin);
        
        // treat horizontal scroll
        if (this.getSize().width < minWidth) lastElement.setSize(minWidth, height);
        else lastElement.setSize(this.getSize().width - 25, height);
        
        addStorageElement(lastElement);
        lastElement.setVisible(true);
    }
}
