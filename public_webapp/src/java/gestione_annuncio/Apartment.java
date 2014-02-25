/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestione_annuncio;

import java.util.ArrayList;

/**
 *
 * @author Piero
 */
public class Apartment implements java.io.Serializable {
    public String id_apartment;
    public String user_owner;
    public String address;
    public String civico;
    public String citta;
    public String tipologia;
    public String tipo_cucina;
    public ArrayList<String> img_url;
    public String bagni;
    public String camere_letto;
    public String n_piano;
    public String ascensore;
    public String garage;
    public String terrazzo;
    public String posti_totali;
    public String posti_liberi;
    public String prezzo;
    public String spese_acqua;
    public String spese_gas;
    public String spese_luce;
    public String spese_cond;
    public String ness_spesa;
    
}