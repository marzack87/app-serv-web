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
public class SearchApplet extends JApplet {

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    @Override
    public void init() {
        //intestazione
        JLabel JL_cerca = new JLabel("Cerca un annuncio");
        JPanel JP_nord = new JPanel();
        JP_nord.add(JL_cerca);
        getContentPane().add(JP_nord,BorderLayout.NORTH);
        
        //cerca indirizzo
        JLabel JL_address = new JLabel("Via: ");
        JTextField JTF_address = new JTextField(20);
        JTF_address.setBackground(Color.WHITE);
        JTF_address.setEditable(true);
        JTF_address.setVisible(true);
        JLabel JL_nro = new JLabel("n°: ");
        JTextField JTF_nro = new JTextField(3);
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
        JTextField JTF_cognomeprop = new JTextField(10);
        JTF_cognomeprop.setBackground(Color.WHITE);
        JTF_cognomeprop.setEditable(true);
        JTF_cognomeprop.setVisible(true);
        JLabel JL_nomeprop = new JLabel("Nome Proprietario: ");
        JTextField JTF_nomeprop = new JTextField(10);
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
        JComboBox JCB_tipomenu = new JComboBox();
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
        JComboBox JCB_nrobagnimenu = new JComboBox();
        JCB_nrobagnimenu.addItem("Seleziona");
        JCB_nrobagnimenu.addItem("1");
        JCB_nrobagnimenu.addItem("2");
        JCB_nrobagnimenu.addItem("3");
        JCB_nrobagnimenu.addItem("4");
        JCB_nrobagnimenu.addItem("5");
        
        JLabel JL_nrocamletto = new JLabel("n° camere da letto");
        JComboBox JCB_nrocamlettomenu = new JComboBox();
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
        JComboBox JCB_postitotalimenu = new JComboBox();
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
        JComboBox JCB_postiliberimenu = new JComboBox();
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
        JCheckBox JCB_garage = new JCheckBox();
        JLabel JL_extra_terrazzo = new JLabel("Terrazzo:");
        JCheckBox JCB_terrazzo = new JCheckBox();
        
        JPanel JP_extra = new JPanel();
        JP_extra.add(JL_extra_garage);
        JP_extra.add(JCB_garage);
        JP_extra.add(JL_extra_terrazzo);
        JP_extra.add(JCB_terrazzo);
        JP_extra.setLayout(inarow);
        
        //cerca per servizi
        JCheckBox JCB_servizi_parabola = new JCheckBox("Parabola");
        JCheckBox JCB_servizi_spacqua = new JCheckBox("Spese Acqua Incluse");
        JCheckBox JCB_servizi_spluce = new JCheckBox("Spese Luce Incluse");
        JCheckBox JCB_servizi_spcondominiali = new JCheckBox("Spese Condominiali Incluse");
        
        JPanel JP_servizi = new JPanel();
        JP_servizi.add(JCB_servizi_parabola);
        JP_servizi.add(JCB_servizi_spacqua);
        JP_servizi.add(JCB_servizi_spluce);
        JP_servizi.add(JCB_servizi_spcondominiali);
        JP_servizi.setLayout(new GridLayout(0,1));
        
        //cerca per costo
        JLabel JL_costomin = new JLabel("Costo minimo per persona");
        JTextField JTF_costomin = new JTextField(5);
        JTF_costomin.setEditable(true);
        JTF_costomin.setVisible(true);
        
        JLabel JL_costomax = new JLabel("Costo massimo per persona");
        JTextField JTF_costomax = new JTextField(5);
        JTF_costomax.setEditable(true);
        JTF_costomax.setVisible(true);
        
        JPanel JP_costo = new JPanel();
        JP_costo.add(JL_costomin);
        JP_costo.add(JTF_costomin);
        JP_costo.add(JL_costomax);
        JP_costo.add(JTF_costomax);
        
        JTextArea JTA_control = new JTextArea();
        
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
        
        
        ButtonListener buttonL = new ButtonListener(JTF_address, JTF_nro, 
                                                    JTF_cognomeprop, JTF_nomeprop,
                                                    JCB_tipomenu,
                                                    JCB_nrobagnimenu, JCB_nrocamlettomenu,
                                                    JCB_postitotalimenu, JCB_postiliberimenu,
                                                    JCB_garage, JCB_terrazzo,
                                                    JCB_servizi_parabola, JCB_servizi_spacqua, JCB_servizi_spcondominiali, JCB_servizi_spluce,
                                                    JTF_costomin, JTF_costomax, JTA_control);
        
        JButton JB_back = new JButton("Indietro");
        JB_back.addActionListener(buttonL);
        JButton JB_new = new JButton("Nuova Ricerca");
        JB_new.addActionListener(buttonL);
        JButton JB_search = new JButton("Cerca");
        JB_search.addActionListener(buttonL);
        
        JPanel JP_buttonpanel = new JPanel();
        
        JP_buttonpanel.add(JB_back);
        JP_buttonpanel.add(JB_new);
        JP_buttonpanel.add(JB_search);
        JP_buttonpanel.setVisible(true);
        
        getContentPane().add(JP_buttonpanel, BorderLayout.SOUTH);

        
    }

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
        private JCheckBox sev_spluce;
        private JTextField costomin;
        private JTextField costomax;
        private JTextArea control;
        
        private ButtonListener(JTextField JTF_address, JTextField JTF_nro, JTextField JTF_cognomeprop, 
                               JTextField JTF_nomeprop, JComboBox JCB_tipomenu, JComboBox JCB_nrobagnimenu, 
                               JComboBox JCB_nrocamlettomenu, JComboBox JCB_postitotalimenu, 
                               JComboBox JCB_postiliberimenu, JCheckBox JCB_garage, JCheckBox JCB_terrazzo, 
                               JCheckBox JCB_servizi_parabola, JCheckBox JCB_servizi_spacqua, 
                               JCheckBox JCB_servizi_spcondominiali, JCheckBox JCB_servizi_spluce, 
                               JTextField JTF_costomin, JTextField JTF_costomax, JTextArea JTA_control) {
            printer = new ResultPrinter();
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
            sev_spluce = JCB_servizi_spluce;
            costomin = JTF_costomin;
            costomax = JTF_costomax;
            control = JTA_control;
        }
        
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
        
            //trova la sorgente dell'evento
            JButton b = (JButton) e.getSource();
            String selected = b.getText();
            switch (selected) {
                case ("Indietro"):
                    //vai alla pagina precendente index
                    try {
                        String localhost = InetAddress.getLocalHost().getHostName() + "/index.jsp";
                        URL currentHome = new URL(localhost);
                        getAppletContext().showDocument(currentHome);
                    } catch (        UnknownHostException | MalformedURLException ex) {
                        Logger.getLogger(SearchApplet.class.getName()).log(Level.SEVERE, null, ex);
                    }   break;
                case ("Nuova Ricerca"):
                    //azzera i campi e i risultati
                    //eviterei di reloadare l'applet se possibile senza sbroccare
                    break;
                case ("Cerca"):
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
                                
                    
                    break;
            }
        
       
        }
    }

    class ResultPrinter{
        
        public ResultPrinter(){
            
        }
        
        public void printRes(){
           
//            JSObject.getWindow(this).eval();
            
        }
    }
}

