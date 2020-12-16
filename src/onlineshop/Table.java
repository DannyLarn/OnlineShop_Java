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
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author dnyyy
 */
public class Table extends javax.swing.JPanel {

    /**
     * Creates new from StoragePanel
     */
    //
    
    protected MainWindow main;
    
    // constructor:
    public Table() {
        initComponents();
        Scroll.getVerticalScrollBar().setUnitIncrement(16);
    }
    
    public void setMain(MainWindow main) {
        this.main = main;
    }
    
    // add a new row to the table
    public void addRow(int id, String name, int price, String category, int available, List<StorageElement> list) {
        
        int height = 31;
        int minWidth = 622;
        int margin = 4;
        StorageElement lastElement = list.get(list.size() - 1);
        lastElement.setLabels(id, name, price, category, available);
        
        // verifying how many elements the list has
        if (list.size() == 1) {
            lastElement.setLocation(margin, 6);
            productContainer.setPreferredSize(new Dimension(this.getPreferredSize().width, productContainer.getPreferredSize().height + 12));
        } else {
            lastElement.setLocation(margin, list.get(list.size() - 2).getY() + height + margin);
        }
        
        // treat horizontal scroll
        if (this.getSize().width < minWidth) {
            productContainer.setPreferredSize(new Dimension(minWidth, productContainer.getPreferredSize().height + height + margin));
            productContainer.setSize(minWidth, productContainer.getPreferredSize().height + height + margin);
            lastElement.setSize(minWidth, height);
        } else {
            productContainer.setPreferredSize(new Dimension(Scroll.getPreferredSize().width - 25, productContainer.getPreferredSize().height + height + margin));
            productContainer.setSize(this.getSize().width - 25, this.getSize().height + height + margin);
            lastElement.setSize(this.getSize().width - 25, height);
        }
        
        productContainer.add(lastElement);
        lastElement.setVisible(true);
    }
    
    public void addRow1(int id, String name, int price, String category, int available, List<CartElement> list) {
        
        int height = 31;
        int minWidth = 622;
        int margin = 4;
        CartElement lastElement = list.get(list.size() - 1);
        lastElement.setLabels(id, name, price, category, available);
        
        // verifying how many elements the list has
        if (list.size() == 1) {
            lastElement.setLocation(margin, 6);
            productContainer.setPreferredSize(new Dimension(this.getPreferredSize().width, productContainer.getPreferredSize().height + 12));
        } else {
            lastElement.setLocation(margin, list.get(list.size() - 2).getY() + height + margin);
        }
        
        // treat horizontal scroll
        if (this.getSize().width < minWidth) {
            productContainer.setPreferredSize(new Dimension(minWidth, productContainer.getPreferredSize().height + height + margin));
            productContainer.setSize(minWidth, productContainer.getPreferredSize().height + height + margin);
            lastElement.setSize(minWidth, height);
        } else {
            productContainer.setPreferredSize(new Dimension(Scroll.getPreferredSize().width - 25, productContainer.getPreferredSize().height + height + margin));
            productContainer.setSize(this.getSize().width - 25, this.getSize().height + height + margin);
            lastElement.setSize(this.getSize().width - 25, height);
        }
        
        productContainer.add(lastElement);
        lastElement.setVisible(true);
    }
    
    public void addRow2(int id, String name, int price, String category, int available, List<WhishlistElement> list) {
        
        int height = 31;
        int minWidth = 622;
        int margin = 4;
        WhishlistElement lastElement = list.get(list.size() - 1);
        lastElement.setLabels(id, name, price, category, available);
        
        // verifying how many elements the list has
        if (list.size() == 1) {
            lastElement.setLocation(margin, 6);
            productContainer.setPreferredSize(new Dimension(this.getPreferredSize().width, productContainer.getPreferredSize().height + 12));
        } else {
            lastElement.setLocation(margin, list.get(list.size() - 2).getY() + height + margin);
        }
        
        // treat horizontal scroll
        if (this.getSize().width < minWidth) {
            productContainer.setPreferredSize(new Dimension(minWidth, productContainer.getPreferredSize().height + height + margin));
            productContainer.setSize(minWidth, productContainer.getPreferredSize().height + height + margin);
            lastElement.setSize(minWidth, height);
        } else {
            productContainer.setPreferredSize(new Dimension(Scroll.getPreferredSize().width - 25, productContainer.getPreferredSize().height + height + margin));
            productContainer.setSize(this.getSize().width - 25, this.getSize().height + height + margin);
            lastElement.setSize(this.getSize().width - 25, height);
        }
        
        productContainer.add(lastElement);
        lastElement.setVisible(true);
    }
    
    public void throwMessage(Exception e, String errorTitle, int messageType) {
        JOptionPane optionPane = new JOptionPane(e.getMessage(), messageType);
        JDialog dialog = optionPane.createDialog(errorTitle);
        dialog.setLocationByPlatform(true);
        dialog.setLocationRelativeTo(this);
        dialog.setAlwaysOnTop(true); // to show top of all other application
        dialog.setVisible(true); // to visible the dialog
    }
    
    public void resetTable() {
        productContainer.removeAll();
        productContainer.repaint();
        productContainer.setPreferredSize(new Dimension(0, 0));
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Scroll = new javax.swing.JScrollPane();
        productContainer = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        setMinimumSize(new java.awt.Dimension(622, 200));
        setPreferredSize(new java.awt.Dimension(622, 200));
        setSize(new java.awt.Dimension(622, 200));

        Scroll.setBorder(null);
        Scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        Scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        productContainer.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout productContainerLayout = new javax.swing.GroupLayout(productContainer);
        productContainer.setLayout(productContainerLayout);
        productContainerLayout.setHorizontalGroup(
            productContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        productContainerLayout.setVerticalGroup(
            productContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        Scroll.setViewportView(productContainer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Scroll)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Scroll)
        );
    }// </editor-fold>//GEN-END:initComponents

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Scroll;
    private javax.swing.JPanel productContainer;
    // End of variables declaration//GEN-END:variables
}
