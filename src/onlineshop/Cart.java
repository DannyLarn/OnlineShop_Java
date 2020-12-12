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
    public void add(int id) {
        Product added = storage.getElementById(id);
        
        if (added != null) {
            cartElements.add(added);
        }
    }
    
    // var functions:
    
}
