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
public class Cart extends Table {

    private final List<CartElement> allProducts;
//    private final List<onlineshop.CartElement> displayedProducts;
    
    /**
     * Creates new from StoragePanel
     */
    //
    
    // constructor:
    public Cart() {
        allProducts = new ArrayList();
//        displayedProducts = new ArrayList();
    }

    public void addToCart(Product product) {
        allProducts.add(new CartElement());
        addRow1(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getAvailable(), allProducts);
    }
                   
}
