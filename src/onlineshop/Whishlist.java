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

/**
 *
 * @author dnyyy
 */
public class Whishlist extends Table {

    private final List<WhishlistElement> allProducts;
//    private final List<onlineshop.CartElement> displayedProducts;
    
    /**
     * Creates new from StoragePanel
     */
    //
    
    // constructor:
    public Whishlist() {
        allProducts = new ArrayList();
//        displayedProducts = new ArrayList();
    }

    public void addToWhishlist(Product product) {
        allProducts.add(new WhishlistElement(this));
        addRow2(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getAvailable(), allProducts);
    }
    
    public void removeFromWhishlist(WhishlistElement whishlistElement) {
        resetTable();
        allProducts.remove(whishlistElement);
        List<WhishlistElement> modifiedList = new ArrayList();
        for (WhishlistElement element : allProducts) {
            modifiedList.add(element);
            Product product = element.getProductPanel();
            // new list needed
            addRow2(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getAvailable(), modifiedList);
        }
    }
    
    public boolean find(Product product) {
        for (WhishlistElement element : allProducts) {
            if (element.getProductPanel().find(product.getId()) != null) {
                return true;
            }
        }
        return false;
    }
}
