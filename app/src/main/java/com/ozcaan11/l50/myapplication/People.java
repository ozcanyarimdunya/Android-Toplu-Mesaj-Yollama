package com.ozcaan11.l50.myapplication;

/**
 * Author : l50 - Özcan YARIMDÜNYA (@ozcaan11)
 * Date   : 27.04.2016 - 10:29
 */

public class People {

    String ad = null;
    String soyad = null;
    String telefon = null;
    boolean selected = false;

    public People(String ad, String soyad, String telefon, boolean selected) {
        super();
        this.ad = ad;
        this.soyad = soyad;
        this.telefon = telefon;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
