/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineshop;

import java.awt.Dimension;
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
public class Cart extends Table {

    private final List<CartElement> allProducts;
    
    /**
     * Creates new from StoragePanel
     */
    //
    
    // constructor:
    public Cart() {
        allProducts = new ArrayList();
    }

    public void addToCart(Product product) {
        // exception for too many selection 
        allProducts.add(new CartElement(this));
        addRow1(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getAvailable(), allProducts);
        main.setCartTotal();
    }
    
    public void removeFromCart(CartElement cartElement) {
        resetTable();
        allProducts.remove(cartElement);
        List<CartElement> modifiedList = new ArrayList();
        for (CartElement element : allProducts) {
            modifiedList.add(element);
            Product product = element.getProductPanel();
            // new list needed
            addRow1(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getAvailable(), modifiedList);
        }
        main.setCartTotal();
    }
    
    public int getCartSize() {
        return allProducts.size();
    }
    
    public boolean quantityVerification(Product product) {
        int counter = 0;
        counter = allProducts.stream().filter((element) -> (element.getProductPanel().find(product.getId()) != null)).map((_item) -> 1).reduce(counter, Integer::sum);
        
        return counter >= product.getAvailable();
    }
    
    public String getTotal() {
        int sum = 0;
        for (CartElement element : allProducts) {
            sum += element.getProductPanel().getPrice();
        }
        return String.valueOf(sum);
    }
    
    public List<Product> getProducts() {
        List<Product> products = new ArrayList();
        for (CartElement element : allProducts) {
            products.add(element.getProductPanel());
        }
        return products;
    }
    
    @Override
    public void removeAll() {
        resetTable();
        allProducts.removeAll(allProducts);
    }
    
    public void loadCart() {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("./src/onlineshop/Files/cart.json"));
            JSONArray storageList = (JSONArray) jsonObject.get("cart");
            Iterator<JSONObject> it = storageList.iterator();
            
            while (it.hasNext()) {
                JSONObject prod = (JSONObject) it.next();

                int id = Integer.parseInt(prod.get("id").toString());
                String name = prod.get("name").toString();
                int price = Integer.parseInt(prod.get("price").toString());
                String category = prod.get("category").toString();
                int available = Integer.parseInt(prod.get("available").toString());
                
                addToCart(new Product(id, name, price, category, available));
            }
        } catch (Exception e) {
            throwMessage(e, "Fajl beolvasasi hiba", JOptionPane.WARNING_MESSAGE);
        }
    
    }
    
    public void saveCart() {
        JSONArray wishlistList = new JSONArray();
        
        for (CartElement element : allProducts) {
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
        result.put("cart", wishlistList);
        
        FileWriter file;
        try {
            file = new FileWriter("./src/onlineshop/Files/cart.json");
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
}
