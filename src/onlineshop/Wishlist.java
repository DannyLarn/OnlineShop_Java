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

    public void addToWhishlist(Product product) {
        allProducts.add(new WishlistElement(this));
        addRow2(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getAvailable(), allProducts);
        main.setWhishlistTotal();
    }
    
    public void removeFromWhishlist(WishlistElement whishlistElement) {
        resetTable();
        allProducts.remove(whishlistElement);
        List<WishlistElement> modifiedList = new ArrayList();
        for (WishlistElement element : allProducts) {
            modifiedList.add(element);
            Product product = element.getProductPanel();
            // new list needed
            addRow2(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getAvailable(), modifiedList);
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
}
