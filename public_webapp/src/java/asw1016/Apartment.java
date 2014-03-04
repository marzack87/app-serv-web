/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package asw1016;

import java.util.ArrayList;

/**
 *
 * @author Piero
 */
public class Apartment {
    private String id;
    private String proprietario;
    private String indirizzo;
    private String civico;
    private String citta;
    private String tipologia;
    private String tipo_cucina;
    private ArrayList<String> img_url;
    private String bagni;
    private String camere_letto;
    private String piano;
    private String ascensore;
    private String garage;
    private String terrazzo;
    private String postiTotali;
    private String postiLiberi;
    private String prezzo;
    private String speseAcqua;
    private String speseGas;
    private String speseLuce;
    private String speseCond;
    private String noSpese;

    public Apartment() {
        img_url = new ArrayList<String> ();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getTipo_cucina() {
        return tipo_cucina;
    }

    public void setTipo_cucina(String tipo_cucina) {
        this.tipo_cucina = tipo_cucina;
    }

    public ArrayList<String> getImg_url() {
        return img_url;
    }

    public void setImg_url(ArrayList<String> img_url) {
        this.img_url = img_url;
    }

    public void addImg_url(String img_url) {
        this.img_url.add(img_url);
    }
    
    public String getBagni() {
        return bagni;
    }

    public void setBagni(String bagni) {
        this.bagni = bagni;
    }

    public String getCamere_letto() {
        return camere_letto;
    }

    public void setCamere_letto(String camere_letto) {
        this.camere_letto = camere_letto;
    }

    public String getPiano() {
        return piano;
    }

    public void setPiano(String piano) {
        this.piano = piano;
    }

    public String getAscensore() {
        return ascensore;
    }

    public void setAscensore(String ascensore) {
        this.ascensore = ascensore;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public String getTerrazzo() {
        return terrazzo;
    }

    public void setTerrazzo(String terrazzo) {
        this.terrazzo = terrazzo;
    }

    public String getPostiTotali() {
        return postiTotali;
    }

    public void setPostiTotali(String postiTotali) {
        this.postiTotali = postiTotali;
    }

    public String getPostiLiberi() {
        return postiLiberi;
    }

    public void setPostiLiberi(String postiLiberi) {
        this.postiLiberi = postiLiberi;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public String getSpeseAcqua() {
        return speseAcqua;
    }

    public void setSpeseAcqua(String speseAcqua) {
        this.speseAcqua = speseAcqua;
    }

    public String getSpeseGas() {
        return speseGas;
    }

    public void setSpeseGas(String speseGas) {
        this.speseGas = speseGas;
    }

    public String getSpeseLuce() {
        return speseLuce;
    }

    public void setSpeseLuce(String speseLuce) {
        this.speseLuce = speseLuce;
    }

    public String getSpeseCond() {
        return speseCond;
    }

    public void setSpeseCond(String speseCond) {
        this.speseCond = speseCond;
    }

    public String getNoSpese() {
        return noSpese;
    }

    public void setNoSpese(String noSpese) {
        this.noSpese = noSpese;
    }
    
}