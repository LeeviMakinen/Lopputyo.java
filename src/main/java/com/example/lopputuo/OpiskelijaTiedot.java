package com.example.lopputuo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Luokka jossa on kaikki pääohjelmassa tarvittava
 */
public class OpiskelijaTiedot implements Serializable {
    /**
     * Oppilaan nimi
     */
    String nimi;

    /**
     * Oppilaan kurssi
     *
     */
    String kurssi;

    /**
     * Opiskelijan opintonumero
     */
    int opNumero;

    /**
     * Opiskelijan pisteet
     */
    double pisteet;

    /**
     * Opiskelijan arvosana
     */
    int arvosana;

    /**
     * Parametriton konstruktori
     */
    public OpiskelijaTiedot(){
    }

    /**
     * Konstruktori
     * @param nimi String nimi
     * @param kurssi String kurssi
     * @param opNumero int opNumero
     * @param pisteet double pisteet
     * @param arvosana int arvosana
     */
    public OpiskelijaTiedot (String nimi, String kurssi, int opNumero, double pisteet, int arvosana){
        this.nimi = nimi;
        this.kurssi = kurssi;
        this.opNumero = opNumero;
        this.pisteet = pisteet;
        this.arvosana = arvosana;
    }

    /**
     * Palauttaa nimen
     * @return nimi String
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Asettaa nimen
     * @param nimi String
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    /**
     * Palauttaa kurssin
     * @return kurssi String
     */
    public String getKurssi() {
        return kurssi;
    }

    /**
     * Asettaa kurssin
     * @param kurssi String
     */
    public void setKurssi(String kurssi) {
        this.kurssi = kurssi;
    }

    /**
     * Palauttaa opintonumeron
     * @return opNumero int
     */
    public int getOpNumero() {
        return opNumero;
    }

    /**
     * Asettaa opintonumeron
     * @param opNumero int
     */
    public void setOpNumero(int opNumero) {
        this.opNumero = opNumero;
    }

    /**
     * Palauttaa pisteet
     * @return pisteet double
     */
    public double getPisteet() {
        return pisteet;
    }

    /**
     * Asettaa pisteet
     * @param pisteet double
     */
    public void setPisteet(double pisteet) {
        this.pisteet = pisteet;
    }

    /**
     * Palauttaa arvosanan
     * @return arvosana int
     */
    public int getArvosana() {
        return arvosana;
    }

    /**
     * Asettaa sanat
     * @param arvosana int
     */
    public void setArvosana(int arvosana) {
        this.arvosana = arvosana;
    }


    /**
     * Käytetään pääohjelmassa kun tarkastetaan onko samoja arvoja
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpiskelijaTiedot that = (OpiskelijaTiedot) o;
        return opNumero == that.opNumero && Double.compare(that.pisteet, pisteet) == 0 && arvosana == that.arvosana && nimi.equals(that.nimi) && kurssi.equals(that.kurssi);
    }

    /**
     * Käytetään pääohjelmassa kun tarkastetaan onko samoja arvoja
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(nimi, kurssi, opNumero, pisteet, arvosana);
    }

    /**
     *
     * @return Palauttaa printattavan version pääohjelmaan
     */
    @Override
    public String toString() {
        return "\n" +
                "Opiskelijantiedot: \n" +
                "Kurssi: " + kurssi + "\n" +
                "Nimi: " + nimi + "\n" +
                "Opiskelijanumero: " + opNumero + "\n" +
                "Pisteet: " + pisteet + "\n" +
                "Arvosana: " + arvosana + "\n";
    }
}
