/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineshop;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author dnyyy
 */
public class Storage {
    // variables:
    private final List<Product> products;
    
    // constructors:
    public Storage() {
        products = new ArrayList();
        readProductsFromFile("./src/onlineshop/Files/storage.json");
    }
    
    // void functions:
    // read all datas from storage.json and load into the list
    private void readProductsFromFile(String filepath) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filepath));
            JSONArray storageList = (JSONArray) jsonObject.get("storage");
            Iterator<JSONObject> it = storageList.iterator();
            
            while (it.hasNext()) {
                JSONObject prod = (JSONObject) it.next();
                
//                "id": 1, "name": "alma", "price": 100, "category": "gyumolcs", "available": 15
                int id = Integer.parseInt(prod.get("id").toString());
                String name = prod.get("name").toString();
                int price = Integer.parseInt(prod.get("price").toString());
                String category = prod.get("category").toString();
                int available = Integer.parseInt(prod.get("available").toString());
                
                upload(new Product(id, name, price, category, available));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // upload the list of storage with a new product
    private void upload(Product product) {
        products.add(product);
    }
    
    
    // var functions:
    // return a list of string arrays if the searched text is included in the id, name, price, category or available
    public List<String[]> searchByEverything(String searchValue) {
        List<String[]> response = new ArrayList<>();
        
        for (Product prod : products) {
            String array[] = prod.searchByEverything(searchValue);
            if (array != null) {
                response.add(array);
            }
        }
        return response;
    }
    public List<String[]> searchByName(String searchValue) {
        
        
        
        return null;
    }
    public Product getElementById(int id) {
        for (Product prod : products)
            if (prod.getId() == id)
                return prod;
        
        return null;
    }
    
    // testing functions:
    // list the name of all products to output
    public void testList() {
        products.forEach((prod) -> {
            prod.logAllData();
        });
    }
}
