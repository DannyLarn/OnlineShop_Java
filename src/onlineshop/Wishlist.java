/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineshop;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dnyyy
 */
public class Wishlist {
    // variables:
    private final List<Product> whislistElements;
    private final Storage storage;
    
    // constructors:
    public Wishlist(Storage storage) {
        this.storage = storage;
        whislistElements = new ArrayList();
    }
    
    // void functions:
    public void add(int id) {
        Product added = storage.getElementById(id);
        
        if (added != null) {
            whislistElements.add(added);
        }
    }
    
    // var functions:
        
}
