/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package search;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author andreabuscarini
 */
public class SearchApplet extends JApplet {

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    public void init() {
        
        //createGUI();
        //JTextArea tx = new JTextArea("scrivi qui..");
        //tx.setLineWrap(true);
        //tx.setSize(200, 200);
        //tx.setEditable(false);
        //tx.setVisible(true);
        
        JLabel label = new JLabel("Crea un nuovo annuncio");
        JPanel nord = new JPanel();
        
        JButton b1 = new JButton("Primo");
        //b1.addActionListener(new FirstButtonL(tx));
        JButton b2 = new JButton("Secondo");
        //b2.addActionListener(new SecondButtonL(tx));
        JButton b3 = new JButton("Cancella");
        //b3.addActionListener(new CancelButtonL(tx));
        
        
        //Container c = new Container();
        //c.setLayout(new FlowLayout());
        
        nord.add(label);
        getContentPane().add(nord,BorderLayout.NORTH);
        

        //panel.add(tx, "Center");
        
        
        JPanel panel = new JPanel();
        
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.setVisible(true);
        
        getContentPane().add(panel, BorderLayout.CENTER);

        
    }

    // TODO overwrite start(), stop() and destroy() methods
}
