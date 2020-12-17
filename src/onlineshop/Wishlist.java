/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineshop;

import com.oracle.jrockit.jfr.Producer;
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
public class Wishlist extends Table {

    private final List<WishlistElement> allProducts;
//    private final List<onlineshop.CartElement> displayedProducts;
    
    /**
     * Creates new from StoragePanel
     */
    //
    
    // constructor:
    public Wishlist() {
        allProducts = new ArrayList();
//        displayedProducts = new ArrayList();
    }

    public void addToWishlist(Product product) {
        allProducts.add(new WishlistElement(this));
        addNewRow(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getAvailable(), allProducts);
        main.setWhishlistTotal();
    }
    
    public void removeFromWishlist(WishlistElement whishlistElement) {
        resetTable();
        allProducts.remove(whishlistElement);
        List<WishlistElement> modifiedList = new ArrayList();
        for (WishlistElement element : allProducts) {
            modifiedList.add(element);
            Product product = element.getProductPanel();
            addNewRow(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getAvailable(), modifiedList);
        }
        main.setWhishlistTotal();
    }
    
    public boolean find(Product product) {
        for (WishlistElement element : allProducts) {
            if (element.getProductPanel().find(product.getId()) != null) {
                return true;
            }
        }
        return false;
    }

    public String getTotal() {
        int sum = 0;
        for (WishlistElement element : allProducts) {
            sum += element.getProductPanel().getPrice();
        }
        return String.valueOf(sum);
    }
    
    public void loadWishlist() {
        
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("./src/onlineshop/Files/wishlist.json"));
            JSONArray storageList = (JSONArray) jsonObject.get("wishlist");
            Iterator<JSONObject> it = storageList.iterator();
            
            while (it.hasNext()) {
                JSONObject prod = (JSONObject) it.next();

                int id = Integer.parseInt(prod.get("id").toString());
                String name = prod.get("name").toString();
                int price = Integer.parseInt(prod.get("price").toString());
                String category = prod.get("category").toString();
                int available = Integer.parseInt(prod.get("available").toString());
                
                addToWishlist(new Product(id, name, price, category, available));
            }
        } catch (Exception e) {
            throwMessage(e, "Fajl beolvasasi hiba", JOptionPane.WARNING_MESSAGE);
        }
    
    }
    
    public void purchase(List<Product> products) {
        
        for (Product product : products) {
            Product prod = find(product.getId());
            if (prod != null) {
                prod.setAvailable(prod.getAvailable() - 1);
            }
        }
    }
    
    private Product find(int id) {
        for (WishlistElement element : allProducts) {
            if (element.getProductPanel().getId() == id) {
                return element.getProductPanel();
            }
        }
        return null;
    }
    
    public void saveWishlist() {
        JSONArray wishlistList = new JSONArray();
        
        for (WishlistElement element : allProducts) {
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
        result.put("wishlist", wishlistList);
        
        FileWriter file;
        try {
            file = new FileWriter("./src/onlineshop/Files/wishlist.json");
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
    public void addNewRow(int id, String name, int price, String category, int available, List<WishlistElement> list) {
        
        WishlistElement lastElement = list.get(list.size() - 1);
        lastElement.setLabels(id, name, price, category, available);
        addRowSettings(list.size());
        // verifying how many elements the list has
        if (list.size() == 1) lastElement.setLocation(margin, 6);
        else lastElement.setLocation(margin, list.get(list.size() - 2).getY() + height + margin);
        
        // treat horizontal scroll
        if (this.getSize().width < minWidth) lastElement.setSize(minWidth, height);
        else lastElement.setSize(this.getSize().width - 25, height);
        
        addToWishlistContainer(lastElement);
        lastElement.setVisible(true);
    }
}
