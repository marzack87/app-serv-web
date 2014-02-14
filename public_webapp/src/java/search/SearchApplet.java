/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package search;

import java.awt.*;
import static java.awt.FlowLayout.LEFT;
import javax.swing.*;

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
        JTextField txtaddress = new JTextField(20);
        txtaddress.setBackground(Color.WHITE);
        txtaddress.setEditable(true);
        txtaddress.setVisible(true);
        JLabel nro = new JLabel("n°: ");
        JTextField txtnro = new JTextField(3);
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
        JTextField txtcognomeprop = new JTextField(10);
        txtaddress.setBackground(Color.WHITE);
        txtaddress.setEditable(true);
        txtaddress.setVisible(true);
        JLabel nomeprop = new JLabel("Nome Proprietario: ");
        JTextField txtnomeprop = new JTextField(10);
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
        JComboBox tipomenu = new JComboBox();
        tipomenu.addItem("Seleziona");
        tipomenu.addItem("Appartamento");
        tipomenu.addItem("Villetta");
        tipomenu.addItem("Casa Indipendente");
        
        JPanel ptipo = new JPanel();
        
        ptipo.add(tipo);
        ptipo.add(tipomenu);
        ptipo.setLayout(inarow);
        
        //cerca per caratteristiche
        JLabel nrobagni = new JLabel("n° bagni");
        JComboBox nrobagnimenu = new JComboBox();
        nrobagnimenu.addItem("Seleziona");
        nrobagnimenu.addItem("1");
        nrobagnimenu.addItem("2");
        nrobagnimenu.addItem("3");
        nrobagnimenu.addItem("4");
        nrobagnimenu.addItem("5");
        
        JLabel nrocamletto = new JLabel("n° camere da letto");
        JComboBox nrocamlettomenu = new JComboBox();
        nrocamlettomenu.addItem("Seleziona");
        nrocamlettomenu.addItem("1");
        nrocamlettomenu.addItem("2");
        nrocamlettomenu.addItem("3");
        nrocamlettomenu.addItem("4");
        nrocamlettomenu.addItem("5");
        
        JPanel pcarat = new JPanel();
        pcarat.add(nrobagni);
        pcarat.add(nrobagnimenu);
        pcarat.add(nrocamletto);
        pcarat.add(nrocamlettomenu);
        pcarat.setLayout(inarow);
        
        
        //carca per posti
        JLabel postitot = new JLabel("Posti Totali");
        JComboBox postitotalimenu = new JComboBox();
        postitotalimenu.addItem("Seleziona");
        postitotalimenu.addItem("1");
        postitotalimenu.addItem("2");
        postitotalimenu.addItem("3");
        postitotalimenu.addItem("4");
        postitotalimenu.addItem("5");
        postitotalimenu.addItem("6");
        postitotalimenu.addItem("7");
        postitotalimenu.addItem("8");
        postitotalimenu.addItem("9");
        postitotalimenu.addItem("10");
        
        JLabel postilib = new JLabel("Posti Liberi");
        JComboBox postiliberimenu = new JComboBox();
        postiliberimenu.addItem("Seleziona");
        postiliberimenu.addItem("1");
        postiliberimenu.addItem("2");
        postiliberimenu.addItem("3");
        postiliberimenu.addItem("4");
        postiliberimenu.addItem("5");
        postiliberimenu.addItem("6");
        postiliberimenu.addItem("7");
        postiliberimenu.addItem("8");
        postiliberimenu.addItem("9");
        postiliberimenu.addItem("10");
        
        JPanel pposti = new JPanel();
        pposti.add(postitot);
        pposti.add(postitotalimenu);
        pposti.add(postilib);
        pposti.add(postiliberimenu);
        pposti.setLayout(inarow);
        
        //cerca per extra
        JLabel extra_garage = new JLabel("Garage:");
        JCheckBox cb_garage = new JCheckBox();
        JLabel extra_terrazzo = new JLabel("Terrazzo:");
        JCheckBox cb_terrazzo = new JCheckBox();
        
        JPanel pextra = new JPanel();
        pextra.add(extra_garage);
        pextra.add(cb_garage);
        pextra.add(extra_terrazzo);
        pextra.add(cb_terrazzo);
        pextra.setLayout(inarow);
        
        //cerca per servizi
        
        JCheckBox servizi_parabola = new JCheckBox("Parabola");
        JCheckBox servizi_spacqua = new JCheckBox("Spese Acqua Incluse");
        JCheckBox servizi_spluce = new JCheckBox("Spese Luce Incluse");
        JCheckBox servizi_spcondominiali = new JCheckBox("Spese Condominiali Incluse");
        
        JPanel pservizi = new JPanel();
        pservizi.add(servizi_parabola);
        pservizi.add(servizi_spacqua);
        pservizi.add(servizi_spluce);
        pservizi.add(servizi_spcondominiali);
        pservizi.setLayout(new GridLayout(0,1));
        
        //cerca per costo
        JLabel costomin = new JLabel("Costo minimo per persona");
        JTextField txtcostomin = new JTextField(5);
        txtcostomin.setEditable(true);
        txtcostomin.setVisible(true);
        
        JLabel costomax = new JLabel("Costo massimo per persona");
        JTextField txtcostomax = new JTextField(5);
        txtcostomax.setEditable(true);
        txtcostomax.setVisible(true);
        
        JPanel pcosto = new JPanel();
        pcosto.add(costomin);
        pcosto.add(txtcostomin);
        pcosto.add(costomax);
        pcosto.add(txtcostomax);
        
        
        //panel generale
        JPanel panel = new JPanel();
        panel.add(paddress);
        panel.add(pprop);
        panel.add(ptipo);
        panel.add(pcarat);
        panel.add(pposti);
        panel.add(pextra);
        panel.add(pservizi);
        panel.add(pcosto);
        
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
