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
        // exception for too many selection 
        allProducts.add(new CartElement(this));
        addRow1(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getAvailable(), allProducts);
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
    }
    
    public boolean quantityVerification(Product product) {
        int counter = 0;
        counter = allProducts.stream().filter((element) -> (element.getProductPanel().find(product.getId()) != null)).map((_item) -> 1).reduce(counter, Integer::sum);
        
        return counter >= product.getAvailable();
    }
    
    public int getTotal() {
        int sum = 0;
        for (CartElement element : allProducts) {
            sum += element.getProductPanel().getPrice();
        }
        return sum;
    }
}
