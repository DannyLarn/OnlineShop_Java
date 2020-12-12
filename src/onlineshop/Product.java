package onlineshop;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dnyyy
 */
public class Product {
    // variables:
    private final int id;
    private final String name;
    private final int price;
    private final String category;
    private final int available;

    // constructors:
    public Product(int id, String name, int price, String category, int available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
    }
    
    // getters for variables:
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public String getCategory() {
        return category;
    }
    public int getAvailable() {
        return available;
    }
    
    // void functions:
    
    // var functions:
    public boolean equals(Product prod) {
        return name.equals(prod.getName()) && price == prod.getPrice() &&
                category.equals(prod.getCategory()) && available == prod.getAvailable();
    }
    public String[] search(String searchText) {
        
        if (contains(id, searchText) || contains(name, searchText) || contains(price, searchText) ||
                contains(category, searchText) || contains(available, searchText)) {
            
            String response[] = {
                String.valueOf(id),
                name,
                String.valueOf(price),
                category,
                String.valueOf(available)
            };
            
            return response;
        }
        
        return null;
    }
    private boolean contains(int value, String searchText) {
        return String.valueOf(value).toLowerCase().contains(searchText.toLowerCase());
    }
    private boolean contains(String value, String searchText) {
        return value.toLowerCase().contains(searchText.toLowerCase());
    }
    
    // test funcitons:
    public void logAllData() {
        System.out.printf("(%d) %s: %d, %s, %d\n", id, name, price, category, available);
    }
}
