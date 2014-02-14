/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package search;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import static java.awt.FlowLayout.LEFT;
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
        
        //intestazione
        JLabel label = new JLabel("Cerca un annuncio");
        JPanel nord = new JPanel();
        nord.add(label);
        getContentPane().add(nord,BorderLayout.NORTH);
        
        //cerca indirizzo
        JLabel address = new JLabel("Via: ");
        JTextArea txtaddress = new JTextArea(1,20);
        txtaddress.setBackground(Color.WHITE);
        txtaddress.setEditable(true);
        txtaddress.setVisible(true);
        JLabel nro = new JLabel("n°: ");
        JTextArea txtnro = new JTextArea(1,3);
        txtnro.setBackground(Color.WHITE);
        txtnro.setEditable(true);
        txtnro.setVisible(true);
        
        JPanel  paddress = new JPanel();
        
        paddress.add(address);
        paddress.add(txtaddress);
        paddress.add(nro);
        paddress.add(txtnro);
        LayoutManager inarow = new FlowLayout(LEFT);
        paddress.setLayout(inarow);
        
        
        //cerca nome prop
        JLabel cognomeprop = new JLabel("Cognome Proprietario: ");
        JTextArea txtcognomeprop = new JTextArea(1,20);
        txtaddress.setBackground(Color.WHITE);
        txtaddress.setEditable(true);
        txtaddress.setVisible(true);
        JLabel nomeprop = new JLabel("Nome Proprietario: ");
        JTextArea txtnomeprop = new JTextArea(1,20);
        txtaddress.setBackground(Color.WHITE);
        txtaddress.setEditable(true);
        txtaddress.setVisible(true);
        
        JPanel pprop = new JPanel();
        
        pprop.add(cognomeprop);
        pprop.add(txtcognomeprop);
        pprop.add(nomeprop);
        pprop.add(txtnomeprop);
        pprop.setLayout(inarow);
        
        //cerca tipologia edificio
        JLabel tipo = new JLabel("Tipologia: ");
        
        
        //panel generale
        JPanel panel = new JPanel();
        panel.add(paddress);
        panel.add(pprop);
        panel.setLayout(new GridLayout(0,1));
        
        getContentPane().add(panel,BorderLayout.LINE_START);
        
        
        
        
        JButton b1 = new JButton("Primo");
        //b1.addActionListener(new FirstButtonL(tx));
        JButton b2 = new JButton("Secondo");
        //b2.addActionListener(new SecondButtonL(tx));
        JButton b3 = new JButton("Cancella");
        //b3.addActionListener(new CancelButtonL(tx));
        
        
        //Container c = new Container();
        //c.setLayout(new FlowLayout());
        
        
        

        //panel.add(tx, "Center");
        
        
        JPanel bpanel = new JPanel();
        
        bpanel.add(b1);
        bpanel.add(b2);
        bpanel.add(b3);
        bpanel.setVisible(true);
        
        getContentPane().add(bpanel, BorderLayout.SOUTH);

        
    }

    // TODO overwrite start(), stop() and destroy() methods
}
