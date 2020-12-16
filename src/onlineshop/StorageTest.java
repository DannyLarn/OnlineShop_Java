/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineshop;

import java.awt.Dimension;
import java.io.FileReader;
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
public class StorageTest extends Table {

    private final List<onlineshop.StorageElement> allProducts;
    private final List<onlineshop.StorageElement> displayedProducts;
    private final String filename = "./src/onlineshop/Files/storage.json";
    
    /**
     * Creates new from StoragePanel
     */
    //
    // constructor:
    public StorageTest() {
        allProducts = new ArrayList();
        displayedProducts = new ArrayList();
    }

    public void build() {
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
                
                allProducts.add(new onlineshop.StorageElement());
                addRow(id, name, price, category, available, allProducts);
            }
            
        } catch (Exception e) {
            throwMessage(e, "Fajl beolvasas", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void search(String search, String type) {
        displayedProducts.removeAll(displayedProducts);
        resetTable();
        
        for (onlineshop.StorageElement panel : allProducts) {
            onlineshop.TestPanel prod = panel.getProductPanel();
            if ((type.equals("name") && prod.searchByName(search)) || (type.equals("category") && prod.searchByCategory(search))) {
                displayedProducts.add(panel);
                addRow(prod.getId(), prod.getName(), prod.getPrice(), prod.getCategory(), prod.getAvailable(), displayedProducts);
            }
        }
    }
        
    public void searchByName(String search) {
        search(search, "name");
    }
    
    public void searchByCategory(String search) {
        search(search, "category");
    }
                   
}
