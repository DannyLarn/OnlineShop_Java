package onlineshop;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dnyyy
 */
public class Cart {
    // variables:
    private final List<Product> cartElements;
    private final Storage storage;
    
    // constructors:
    public Cart(Storage storage) {
        this.storage = storage;
        cartElements = new ArrayList();
    }
    
    // void functions:
    // add a Product by its id
    public void add(int id, int quantity) {
        Product added = storage.getElementById(id);
        
        if (added != null) {
            if (quantity > added.getAvailable()) {
                quantity = added.getAvailable();
            }
            cartElements.add(new Product(id, added.getName(), added.getPrice(), added.getCategory(), quantity));
        }
    }
    // remove a Product from cart by its id
    public void remove(int id) {
        cartElements.remove(id);
    }
    // finalize the purchose it removes the given quantity of Product from Storage
    public void purchase() {
        storage.purchase(cartElements);
        cartElements.removeAll(cartElements);
    }
    
    // var functions:
    // return the List<String[]> of Products from cart
    public List<String[]> getCartElements() {
        List<String[]> response = new ArrayList();
        for (Product prod : cartElements) {
            response.add(prod.outputArray());
        }
        return response;
    }
    // get the total price of cart
    public int getTotal() {
        int sum = 0;
        sum = cartElements.stream().map((prod) -> prod.getPrice()).reduce(sum, Integer::sum);
        return sum;
    }
}
