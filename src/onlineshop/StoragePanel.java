/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineshop;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author dnyyy
 */
public class StoragePanel extends javax.swing.JPanel {

    /**
     * Creates new from StoragePanel
     */
    //
    private final List<onlineshop.ShopProductElement> testPanels;
    private String filename = "./src/onlineshop/Files/storage.json";
    // constructor:
    public StoragePanel() {
        testPanels = new ArrayList();
        initComponents();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        
    }

    public void build() {
        readProductsFromFile(filename);
    }
    
    private void readProductsFromFile(String filepath) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filepath));
            JSONArray storageList = (JSONArray) jsonObject.get("storage");
            Iterator<JSONObject> it = storageList.iterator();
            
            while (it.hasNext()) {
                JSONObject prod = (JSONObject) it.next();
                
//                "id": 1, "name": "alma", "price": 100, "category": "gyumolcs", "available": 15
                int id = Integer.parseInt(prod.get("id").toString());
                String name = prod.get("name").toString();
                int price = Integer.parseInt(prod.get("price").toString());
                String category = prod.get("category").toString();
                int available = Integer.parseInt(prod.get("available").toString());
                
                addRow(id, name, price, category, available);
            }
            
        } catch (Exception e) {
            throwMessage(e, "Fajl beolvasas", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // add a new row to the table
    public void addRow(int id, String name, int price, String category, int available) {
        testPanels.add(new onlineshop.ShopProductElement());
        int height = 31;
        int minWidth = 622;
        int margin = 4;
        getLastPanel().setLabels(id, name, price, category, available);
        
        // verifying how many elements the list has
        if (testPanels.size() == 1) {
            getLastPanel().setLocation(margin, 6);
            container.setPreferredSize(new Dimension(this.getPreferredSize().width, container.getPreferredSize().height + 12));
        } else {
            getLastPanel().setLocation(margin, getPenultPanel().getY() + height + margin);
        }
        
        // treat horizontal scroll
        if (this.getSize().width < minWidth) {
            container.setPreferredSize(new Dimension(minWidth, container.getPreferredSize().height + height + margin));
            container.setSize(minWidth, container.getPreferredSize().height + height + margin);
            getLastPanel().setSize(minWidth, height);
        } else {
//            jScrollPane1.setSize(new Dimension(this.getWidth(), this.getHeight()));
//            jScrollPane1.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
            container.setPreferredSize(new Dimension(jScrollPane1.getPreferredSize().width - 25, container.getPreferredSize().height + height + margin));
            container.setSize(this.getSize().width - 25, this.getSize().height + height + margin);
            getLastPanel().setSize(this.getSize().width - 25, height);
        }
        
        
//        container.setSize(25, 0);
        System.out.println("Asd: " + jScrollPane1.getWidth());
        System.out.println(container.getPreferredSize().width);
        System.out.println(container.getSize().width);
        System.out.println(getLastPanel().getSize().width);
        
        container.add(getLastPanel());
        getLastPanel().setVisible(true);
    }

    // get the last element of list
    private onlineshop.ShopProductElement getLastPanel() {
        return testPanels.get(testPanels.size() - 1);
    }
    // get the penult element of list
    private onlineshop.ShopProductElement getPenultPanel() {
        if (testPanels.size() > 1) {
            return testPanels.get(testPanels.size() - 2);
        } else {
            return testPanels.get(testPanels.size() - 1);
        }
    }
    
    public void throwMessage(Exception e, String errorTitle, int messageType) {
        JOptionPane optionPane = new JOptionPane(e.getMessage(), messageType);
        JDialog dialog = optionPane.createDialog(errorTitle);
        dialog.setLocationByPlatform(true);
        dialog.setLocationRelativeTo(this);
        dialog.setAlwaysOnTop(true); // to show top of all other application
        dialog.setVisible(true); // to visible the dialog
    }
    
    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        container = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        setMinimumSize(new java.awt.Dimension(622, 200));
        setPreferredSize(new java.awt.Dimension(622, 200));
        setSize(new java.awt.Dimension(622, 200));

        jScrollPane1.setBorder(null);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(0, 0));
        jScrollPane1.setSize(new java.awt.Dimension(0, 0));

        container.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout containerLayout = new javax.swing.GroupLayout(container);
        container.setLayout(containerLayout);
        containerLayout.setHorizontalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        containerLayout.setVerticalGroup(
            containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(container);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel container;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
