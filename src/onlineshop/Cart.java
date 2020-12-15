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
    public void add(int id, int quantity) throws Exception {
        Product added = storage.getElementById(id);
        
        if (added != null) {
            Product foundInCart = find(id);
            
            if ((quantity > added.getAvailable() && added.getAvailable() != 0)) {
                throw new Exception("A megadott mennyiseg nem elerheto!");
            } else if (added.getAvailable() == 0) {
                throw new Exception("A kivalasztott termek jelenleg nem elerheto!");
            } else if (foundInCart != null) {
                foundInCart.setAvailable(foundInCart.getAvailable() + quantity);
                System.out.println(foundInCart.getAvailable());
                storage.testList();
            } else if (foundInCart != null && quantity + foundInCart.getAvailable() > added.getAvailable()) {
                
            } else {
                cartElements.add(new Product(id, added.getName(), added.getPrice(), added.getCategory(), quantity));
            }
        }
    }
    // remove a Product from cart by its id
    public void remove(int id) {
        cartElements.remove(id);
    }
    // finalize the purchose it removes the given quantity of Product from Storage
    public void purchase() throws Exception {
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
    public Product find(int id) {
        for (Product prod : cartElements)
            if (prod.getId() == id)
                return prod;
        
        return null;
    }
    // get the total price of cart
    public int getTotal() {
        int sum = 0;
        sum = cartElements.stream().map((prod) -> prod.getPrice()).reduce(sum, Integer::sum);
        return sum;
    }
}
