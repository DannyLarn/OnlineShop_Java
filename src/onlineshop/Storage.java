/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineshop;

import java.io.FileReader;
import java.util.*;
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
    // finalize the purchase it removes the give quantity of Products from Storage by the List of cart
    public void purchase(List<Product> cartElements) throws Exception {
        for (Product cartElement : cartElements) {
            Product foundProd = getElementById(cartElement.getId());
            if (foundProd != null) {
                
                if (foundProd.getAvailable() == 0) {
                    cartElements.remove(foundProd.getId());
                    throw new Exception("A(z) " + foundProd.getName() + " idokozben elfogyott.");
                }
                
                foundProd.setAvailable(foundProd.getAvailable() - cartElement.getAvailable());
            }
        }
    }
    
    // var functions:
    // inspect every arguments of Products and return with a List<String[]>
    public List<String[]> searchByEverything(String searchValue) {
        return search("Everything", searchValue);
    }
    public List<String[]> searchByName(String searchValue) {
        return search("Name", searchValue);
    }
    public List<String[]> searchByCategory(String searchValue) {
        return search("Category", searchValue);
    }
    // return a Products of List<String[]> with the given search type
    public List<String[]> search(String functionString, String searchValue) {
        List<String[]> response = new ArrayList<>();
        boolean hasError = false;
        
        for (Product prod : products) {
            String array[] = new String[5];
            
            switch (functionString) {
                case "Everything": array = prod.searchByEverything(searchValue); break;
                case "Category": array = prod.searchByCategory(searchValue); break;
                case "Name": array = prod.searchByName(searchValue); break;
                default: hasError = true; break;
            }
            
            if (array != null) {
                response.add(array);
            }
        }
        if (hasError) System.out.println("Storage search function error!");
        
        return response;
    }
    // return a Product which id equals the functions parameter id
    public Product getElementById(int id) {
        for (Product prod : products)
            if (prod.getId() == id)
                return prod;
        
        return null;
    }
    
    // testing functions:
    // list the name of all products to output
    public void testList() {
        products.forEach(prod -> {
            prod.logAllData();
        });
    }
}
