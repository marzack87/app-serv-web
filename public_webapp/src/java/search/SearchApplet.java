/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package search;

import static java.awt.FlowLayout.LEFT;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.logging.*;
import javax.swing.*;
import netscape.javascript.JSObject;


/**
 *
 * @author andreabuscarini
 */
public class SearchApplet extends JApplet implements ActionListener{

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    
    JTextField JTF_address;
    JTextField JTF_nro;
    JTextField JTF_cognomeprop;
    JTextField JTF_nomeprop;
    JComboBox JCB_tipomenu;
    JComboBox JCB_nrobagnimenu;
    JComboBox JCB_nrocamlettomenu;
    JComboBox JCB_postitotalimenu;
    JComboBox JCB_postiliberimenu;
    JCheckBox JCB_garage;
    JCheckBox JCB_terrazzo;
    JCheckBox JCB_servizi_parabola;
    JCheckBox JCB_servizi_spacqua;
    JCheckBox JCB_servizi_spcondominiali;
    JCheckBox JCB_servizi_spluce;
    JTextField JTF_costomin;
    JTextField JTF_costomax;
    JTextArea JTA_control;
    
    
    @Override
    public void init() {
        //intestazione
        JLabel JL_cerca = new JLabel("Cerca un annuncio");
        JPanel JP_nord = new JPanel();
        JP_nord.add(JL_cerca);
        getContentPane().add(JP_nord,BorderLayout.NORTH);
        
        //cerca indirizzo
        JLabel JL_address = new JLabel("Via: ");
        JTF_address = new JTextField(20);
        JTF_address.setBackground(Color.WHITE);
        JTF_address.setEditable(true);
        JTF_address.setVisible(true);
        JLabel JL_nro = new JLabel("n°: ");
        JTF_nro = new JTextField(3);
        JTF_nro.setBackground(Color.WHITE);
        JTF_nro.setEditable(true);
        JTF_nro.setVisible(true);
        
        JPanel  JP_address = new JPanel();
        
        JP_address.add(JL_address);
        JP_address.add(JTF_address);
        JP_address.add(JL_nro);
        JP_address.add(JTF_nro);
        LayoutManager inarow = new FlowLayout(LEFT);
        JP_address.setLayout(inarow);
        
        
        //cerca nome prop
        JLabel JL_cognomeprop = new JLabel("Cognome Proprietario: ");
        JTF_cognomeprop = new JTextField(10);
        JTF_cognomeprop.setBackground(Color.WHITE);
        JTF_cognomeprop.setEditable(true);
        JTF_cognomeprop.setVisible(true);
        JLabel JL_nomeprop = new JLabel("Nome Proprietario: ");
        JTF_nomeprop = new JTextField(10);
        JTF_nomeprop.setBackground(Color.WHITE);
        JTF_nomeprop.setEditable(true);
        JTF_nomeprop.setVisible(true);
        
        JPanel JP_prop = new JPanel();
        
        JP_prop.add(JL_cognomeprop);
        JP_prop.add(JTF_cognomeprop);
        JP_prop.add(JL_nomeprop);
        JP_prop.add(JTF_nomeprop);
        JP_prop.setLayout(inarow);
        
        //cerca tipologia edificio
        JLabel JL_tipo = new JLabel("Tipologia: ");
        JCB_tipomenu = new JComboBox();
        JCB_tipomenu.addItem("Seleziona");
        JCB_tipomenu.addItem("Appartamento");
        JCB_tipomenu.addItem("Villetta");
        JCB_tipomenu.addItem("Casa Indipendente");
        
        JPanel JP_tipo = new JPanel();
        
        JP_tipo.add(JL_tipo);
        JP_tipo.add(JCB_tipomenu);
        JP_tipo.setLayout(inarow);
        
        //cerca per caratteristiche
        JLabel JL_nrobagni = new JLabel("n° bagni");
        JCB_nrobagnimenu = new JComboBox();
        JCB_nrobagnimenu.addItem("Seleziona");
        JCB_nrobagnimenu.addItem("1");
        JCB_nrobagnimenu.addItem("2");
        JCB_nrobagnimenu.addItem("3");
        JCB_nrobagnimenu.addItem("4");
        JCB_nrobagnimenu.addItem("5");
        
        JLabel JL_nrocamletto = new JLabel("n° camere da letto");
        JCB_nrocamlettomenu = new JComboBox();
        JCB_nrocamlettomenu.addItem("Seleziona");
        JCB_nrocamlettomenu.addItem("1");
        JCB_nrocamlettomenu.addItem("2");
        JCB_nrocamlettomenu.addItem("3");
        JCB_nrocamlettomenu.addItem("4");
        JCB_nrocamlettomenu.addItem("5");
        
        JPanel JP_carat = new JPanel();
        JP_carat.add(JL_nrobagni);
        JP_carat.add(JCB_nrobagnimenu);
        JP_carat.add(JL_nrocamletto);
        JP_carat.add(JCB_nrocamlettomenu);
        JP_carat.setLayout(inarow);
        
        
        //carca per posti
        JLabel JL_postitot = new JLabel("Posti Totali");
        JCB_postitotalimenu = new JComboBox();
        JCB_postitotalimenu.addItem("Seleziona");
        JCB_postitotalimenu.addItem("1");
        JCB_postitotalimenu.addItem("2");
        JCB_postitotalimenu.addItem("3");
        JCB_postitotalimenu.addItem("4");
        JCB_postitotalimenu.addItem("5");
        JCB_postitotalimenu.addItem("6");
        JCB_postitotalimenu.addItem("7");
        JCB_postitotalimenu.addItem("8");
        JCB_postitotalimenu.addItem("9");
        JCB_postitotalimenu.addItem("10");
        
        JLabel JL_postilib = new JLabel("Posti Liberi");
        JCB_postiliberimenu = new JComboBox();
        JCB_postiliberimenu.addItem("Seleziona");
        JCB_postiliberimenu.addItem("1");
        JCB_postiliberimenu.addItem("2");
        JCB_postiliberimenu.addItem("3");
        JCB_postiliberimenu.addItem("4");
        JCB_postiliberimenu.addItem("5");
        JCB_postiliberimenu.addItem("6");
        JCB_postiliberimenu.addItem("7");
        JCB_postiliberimenu.addItem("8");
        JCB_postiliberimenu.addItem("9");
        JCB_postiliberimenu.addItem("10");
        
        JPanel JP_posti = new JPanel();
        JP_posti.add(JL_postitot);
        JP_posti.add(JCB_postitotalimenu);
        JP_posti.add(JL_postilib);
        JP_posti.add(JCB_postiliberimenu);
        JP_posti.setLayout(inarow);
       
        //cerca per extra
        JLabel JL_extra_garage = new JLabel("Garage:");
        JCB_garage = new JCheckBox();
        JLabel JL_extra_terrazzo = new JLabel("Terrazzo:");
        JCB_terrazzo = new JCheckBox();
        
        JPanel JP_extra = new JPanel();
        JP_extra.add(JL_extra_garage);
        JP_extra.add(JCB_garage);
        JP_extra.add(JL_extra_terrazzo);
        JP_extra.add(JCB_terrazzo);
        JP_extra.setLayout(inarow);
        
        //cerca per servizi
        JCB_servizi_parabola = new JCheckBox("Parabola");
        JCB_servizi_spacqua = new JCheckBox("Spese Acqua Incluse");
        JCB_servizi_spluce = new JCheckBox("Spese Luce Incluse");
        JCB_servizi_spcondominiali = new JCheckBox("Spese Condominiali Incluse");
        
        JPanel JP_servizi = new JPanel();
        JP_servizi.add(JCB_servizi_parabola);
        JP_servizi.add(JCB_servizi_spacqua);
        JP_servizi.add(JCB_servizi_spluce);
        JP_servizi.add(JCB_servizi_spcondominiali);
        JP_servizi.setLayout(new GridLayout(0,1));
        
        //cerca per costo
        JLabel JL_costomin = new JLabel("Costo minimo per persona");
        JTF_costomin = new JTextField(5);
        JTF_costomin.setEditable(true);
        JTF_costomin.setVisible(true);
        
        JLabel JL_costomax = new JLabel("Costo massimo per persona");
        JTF_costomax = new JTextField(5);
        JTF_costomax.setEditable(true);
        JTF_costomax.setVisible(true);
        
        JPanel JP_costo = new JPanel();
        JP_costo.add(JL_costomin);
        JP_costo.add(JTF_costomin);
        JP_costo.add(JL_costomax);
        JP_costo.add(JTF_costomax);
        
        JTA_control = new JTextArea();
        
        //panel generale
        JPanel JP_generalpanel = new JPanel();
        JP_generalpanel.add(JP_address);
        JP_generalpanel.add(JP_prop);
        JP_generalpanel.add(JP_tipo);
        JP_generalpanel.add(JP_carat);
        JP_generalpanel.add(JP_posti);
        JP_generalpanel.add(JP_extra);
        JP_generalpanel.add(JP_servizi);
        JP_generalpanel.add(JP_costo);
        JP_generalpanel.add(JTA_control);
        
        JP_generalpanel.setLayout(new GridLayout(0,1));
        
        
        getContentPane().add(JP_generalpanel,BorderLayout.LINE_START);
        
        JButton JB_new = new JButton("Nuova Ricerca");
        JB_new.addActionListener(this);
        JButton JB_search = new JButton("Cerca");
        JB_search.addActionListener(this);
        
        JPanel JP_buttonpanel = new JPanel();
        
        JP_buttonpanel.add(JB_new);
        JP_buttonpanel.add(JB_search);
        JP_buttonpanel.setVisible(true);
        
        getContentPane().add(JP_buttonpanel, BorderLayout.SOUTH);

    }
    
    public void actionPerformed(ActionEvent e) {
        
            //trova la sorgente dell'evento
            JButton b = (JButton) e.getSource();
            String selected = b.getText();
            if(selected == "Nuova Ricerca"){
                 //azzera i campi e i risultati
                 //eviterei di reloadare l'applet se possibile senza sbroccare
                JTF_address.setText("");
                JTF_nro.setText("");
                JTF_cognomeprop.setText("");
                JTF_nomeprop.setText("");
                JCB_tipomenu.setSelectedIndex(-1);
                JCB_nrobagnimenu.setSelectedIndex(-1);
                JCB_nrocamlettomenu.setSelectedIndex(-1);
                JCB_postitotalimenu.setSelectedIndex(-1);
                JCB_postiliberimenu.setSelectedIndex(-1);
                JCB_garage.setSelected(false);
                JCB_terrazzo.setSelected(false);
                JCB_servizi_parabola.setSelected(false);
                JCB_servizi_spacqua.setSelected(false);
                JCB_servizi_spcondominiali.setSelected(false);
                JCB_servizi_spluce.setSelected(false);
                JTF_costomin.setText("");
                JTF_costomax.setText("");
                JTA_control.setText("");
   
            }else if(selected == "Cerca"){
                    //prendi i paramentri settati
                    String parameter[] = new String[17];
                    
                    //recupero l'indirizzo
                    if(JTF_address.getText() == null){
                        parameter[0] = "";
                    }else{
                        parameter[0] = JTF_address.getText();
                    }
                    
                    //recupero numero
                    if(JTF_nro.getText() == null){
                        parameter[1] = "";
                    }else{
                        parameter[1] = JTF_nro.getText();
                    }
                    
                    //recupero cognomeprop
                    if(JTF_cognomeprop.getText() == null){
                        parameter[2] = "";
                    }else{
                        parameter[2] = JTF_cognomeprop.getText();
                    }
                    
                    //recupero nomeprop
                    if(JTF_nomeprop.getText() == null){
                        parameter[3] = "";
                    }else{
                        parameter[3] = JTF_nomeprop.getText();
                    }
                    
                    //recupero tipomenu
                    if(JCB_tipomenu.getSelectedIndex() == -1 || JCB_tipomenu.getSelectedIndex() == 0){
                        parameter[4] = "";
                    }else{
                        switch (JCB_tipomenu.getSelectedIndex()){
                            case 1: parameter[4] = "Appartamento";
                            case 2: parameter[4] = "Villetta";
                            case 3: parameter[4] = "Casa Indipendente";
                        }
                    }
                    //recupero nro bagni
                    switch (JCB_nrobagnimenu.getSelectedIndex()){
                        case (-1): parameter[5] = "";
                        case (0):  parameter[5] = "";
                        case 1: parameter[5] = "1";
                        case 2: parameter[5] = "2";
                        case 3: parameter[5] = "3";
                        case 4: parameter[5] = "4";
                        case 5: parameter[5] = "5";                    
                    }
                    
                    //recupero nro cam letto
                    switch (JCB_nrocamlettomenu.getSelectedIndex()){
                        case (-1): parameter[6] = "";
                        case (0):  parameter[6] = "";
                        case 1: parameter[6] = "1";
                        case 2: parameter[6] = "2";
                        case 3: parameter[6] = "3";
                        case 4: parameter[6] = "4";
                        case 5: parameter[6] = "5";                    
                    }
                    
                     //recupero posti totali
                    switch (JCB_postitotalimenu.getSelectedIndex()){
                        case (-1): parameter[7] = "";
                        case (0):  parameter[7] = "";
                        case 1: parameter[7] = "1";
                        case 2: parameter[7] = "2";
                        case 3: parameter[7] = "3";
                        case 4: parameter[7] = "4";
                        case 5: parameter[7] = "5"; 
                        case 6: parameter[7] = "6";
                        case 7: parameter[7] = "7";
                        case 8: parameter[7] = "8";
                        case 9: parameter[7] = "9";
                        case 10: parameter[7] = "10";
                    }
                    
                     //recupero posti liberi
                    switch (JCB_postiliberimenu.getSelectedIndex()){
                        case (-1): parameter[8] = "";
                        case (0):  parameter[8] = "";
                        case 1: parameter[8] = "1";
                        case 2: parameter[8] = "2";
                        case 3: parameter[8] = "3";
                        case 4: parameter[8] = "4";
                        case 5: parameter[8] = "5"; 
                        case 6: parameter[8] = "6";
                        case 7: parameter[8] = "7";
                        case 8: parameter[8] = "8";
                        case 9: parameter[8] = "9";
                        case 10: parameter[8] = "10";
                    }
                    
                    //recupero garage
                    if(JCB_garage.isSelected()){
                        parameter[9] = "garage";
                    }else{
                        parameter[9] = "";
                    }
                    
                    //recupero terrazzo
                    if(JCB_terrazzo.isSelected()){
                        parameter[10] = "terrazzo";
                    }else{
                        parameter[10] = "";
                    }
                    
                    //recupero serv parabola
                    if(JCB_servizi_parabola.isSelected()){
                        parameter[11] = "parabola";
                    }else{
                        parameter[11] = "";
                    }
                    
                    //recupero serv sp acqua
                    if(JCB_servizi_spacqua.isSelected()){
                        parameter[12] = "speseacqua";
                    }else{
                        parameter[12] = "";
                    }
                    
                    //recupero spese condominiali
                    if(JCB_servizi_spcondominiali.isSelected()){
                        parameter[13] = "spesecond";
                    }else{
                        parameter[13] = "";
                    }
                    
                    //recupero spese luce
                    if(JCB_servizi_spluce.isSelected()){
                        parameter[14] = "speseluce";
                    }else{
                        parameter[14] = "";
                    }
                    
                    //recupero costo min
                    if(JTF_costomin.getText() == null){
                        parameter[15] = "";
                    }else{
                        parameter[15] = JTF_costomin.getText();
                    }
                    
                    //recupero costo max
                    if(JTF_costomax.getText() == null){
                        parameter[16] = "";
                    }else{
                        parameter[16] = JTF_costomax.getText();
                    }
                    
                    String stamp = "";
                    for(int i=0; i<parameter.length; i++){
                        stamp = stamp + parameter[i] + " - ";
                    }
                    JTA_control.setText(stamp);
                    
                    JSObject.getWindow(this).eval("getReq("+parameter+");");
                    
            }
        }
}
/*
    // TODO overwrite start(), stop() and destroy() methods
    
    class ButtonListener implements ActionListener {
       
        private ResultPrinter printer;
        private JTextField address;
        private JTextField nro;
        private JTextField cognomeprop;
        private JTextField nomeprop;
        private JComboBox tipomenu;
        private JComboBox nrobagnimenu;
        private JComboBox nrocamletto;
        private JComboBox postitotalimenu;
        private JComboBox postiliberimenu;
        private JCheckBox garage;
        private JCheckBox terrazzo;
        private JCheckBox serv_parabola;
        private JCheckBox serv_spacqua;
        private JCheckBox serv_spcond;
        private JCheckBox serv_spluce;
        private JTextField costomin;
        private JTextField costomax;
//        private JTextArea control;
        
        private ButtonListener(JTextField JTF_address, JTextField JTF_nro, JTextField JTF_cognomeprop, 
                               JTextField JTF_nomeprop, JComboBox JCB_tipomenu, JComboBox JCB_nrobagnimenu, 
                               JComboBox JCB_nrocamlettomenu, JComboBox JCB_postitotalimenu, 
                               JComboBox JCB_postiliberimenu, JCheckBox JCB_garage, JCheckBox JCB_terrazzo, 
                               JCheckBox JCB_servizi_parabola, JCheckBox JCB_servizi_spacqua, 
                               JCheckBox JCB_servizi_spcondominiali, JCheckBox JCB_servizi_spluce, 
                               JTextField JTF_costomin, JTextField JTF_costomax) {
            ResultPrinter printer;
            address = JTF_address;
            nro = JTF_nro;
            cognomeprop = JTF_cognomeprop;
            nomeprop = JTF_nomeprop;
            tipomenu = JCB_tipomenu;
            nrobagnimenu = JCB_nrobagnimenu;
            nrocamletto = JCB_nrocamlettomenu;
            postitotalimenu = JCB_postitotalimenu;
            postiliberimenu = JCB_postiliberimenu;
            garage = JCB_garage;
            terrazzo = JCB_terrazzo;
            serv_parabola = JCB_servizi_parabola;
            serv_spacqua = JCB_servizi_spacqua;
            serv_spcond = JCB_servizi_spcondominiali;
            serv_spluce = JCB_servizi_spluce;
            costomin = JTF_costomin;
            costomax = JTF_costomax;
            //control = JTA_control;
        }
        
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
        
            //trova la sorgente dell'evento
            JButton b = (JButton) e.getSource();
            String selected = b.getText();
            if(selected == "Nuova Ricerca"){
                    //azzera i campi e i risultati
                    //eviterei di reloadare l'applet se possibile senza sbroccare
            }else if(selected == "Cerca"){
                    //prendi i paramentri settati
                    String parameter[] = new String[17];
                    
                    //recupero l'indirizzo
                    if(address.getText() == null){
                        parameter[0] = "";
                    }else{
                        parameter[0] = address.getText();
                    }
                    
                    //recupero numero
                    if(nro.getText() == null){
                        parameter[1] = "";
                    }else{
                        parameter[1] = nro.getText();
                    }
                    
                    //recupero cognomeprop
                    if(cognomeprop.getText() == null){
                        parameter[2] = "";
                    }else{
                        parameter[2] = cognomeprop.getText();
                    }
                    
                    //recupero nomeprop
                    if(nomeprop.getText() == null){
                        parameter[3] = "";
                    }else{
                        parameter[3] = nomeprop.getText();
                    }
                    
                    //recupero tipomenu
                    if(tipomenu.getSelectedIndex() == -1 || tipomenu.getSelectedIndex() == 0){
                        parameter[4] = "";
                    }else{
                        switch (tipomenu.getSelectedIndex()){
                            case 1: parameter[4] = "Appartamento";
                            case 2: parameter[4] = "Villetta";
                            case 3: parameter[4] = "Casa Indipendente";
                        }
                    }
                    //recupero nro bagni
                    switch (nrobagnimenu.getSelectedIndex()){
                        case (-1): parameter[5] = "";
                        case (0):  parameter[5] = "";
                        case 1: parameter[5] = "1";
                        case 2: parameter[5] = "2";
                        case 3: parameter[5] = "3";
                        case 4: parameter[5] = "4";
                        case 5: parameter[5] = "5";                    
                    }
                    
                    //recupero nro cam letto
                    switch (nrocamletto.getSelectedIndex()){
                        case (-1): parameter[6] = "";
                        case (0):  parameter[6] = "";
                        case 1: parameter[6] = "1";
                        case 2: parameter[6] = "2";
                        case 3: parameter[6] = "3";
                        case 4: parameter[6] = "4";
                        case 5: parameter[6] = "5";                    
                    }
                    
                     //recupero posti totali
                    switch (postitotalimenu.getSelectedIndex()){
                        case (-1): parameter[7] = "";
                        case (0):  parameter[7] = "";
                        case 1: parameter[7] = "1";
                        case 2: parameter[7] = "2";
                        case 3: parameter[7] = "3";
                        case 4: parameter[7] = "4";
                        case 5: parameter[7] = "5"; 
                        case 6: parameter[7] = "6";
                        case 7: parameter[7] = "7";
                        case 8: parameter[7] = "8";
                        case 9: parameter[7] = "9";
                        case 10: parameter[7] = "10";
                    }
                    
                     //recupero posti liberi
                    switch (postiliberimenu.getSelectedIndex()){
                        case (-1): parameter[8] = "";
                        case (0):  parameter[8] = "";
                        case 1: parameter[8] = "1";
                        case 2: parameter[8] = "2";
                        case 3: parameter[8] = "3";
                        case 4: parameter[8] = "4";
                        case 5: parameter[8] = "5"; 
                        case 6: parameter[8] = "6";
                        case 7: parameter[8] = "7";
                        case 8: parameter[8] = "8";
                        case 9: parameter[8] = "9";
                        case 10: parameter[8] = "10";
                    }
                    
                    //recupero garage
                    if(garage.isSelected()){
                        parameter[9] = "garage";
                    }else{
                        parameter[9] = "";
                    }
                    
                    //recupero terrazzo
                    if(terrazzo.isSelected()){
                        parameter[10] = "terrazzo";
                    }else{
                        parameter[10] = "";
                    }
                    
                    //recupero serv parabola
                    if(serv_parabola.isSelected()){
                        parameter[11] = "parabola";
                    }else{
                        parameter[11] = "";
                    }
                    
                    //recupero serv sp acqua
                    if(serv_spacqua.isSelected()){
                        parameter[12] = "speseacqua";
                    }else{
                        parameter[12] = "";
                    }
                    
                    //recupero spese condominiali
                    if(serv_spcond.isSelected()){
                        parameter[13] = "spesecond";
                    }else{
                        parameter[13] = "";
                    }
                    
                    //recupero spese luce
                    if(serv_spluce.isSelected()){
                        parameter[14] = "speseluce";
                    }else{
                        parameter[14] = "";
                    }
                    
                    //recupero costo min
                    if(costomin.getText() == null){
                        parameter[15] = "";
                    }else{
                        parameter[15] = costomin.getText();
                    }
                    
                    //recupero costo max
                    if(costomax.getText() == null){
                        parameter[16] = "";
                    }else{
                        parameter[16] = costomax.getText();
                    }
                   
                    JSObject.getWindow(this).eval("getReq("+parameter+");");
                    
            }
        
       
        }
    }

    class ResultPrinter{
        
        private String[] param;
        
        public ResultPrinter(String[] p){
            param = p;
        }
        
        public void printRes(){
           
            JSObject.getWindow(this).eval("getReq("+param+");");
            
        }
    }
}
*/