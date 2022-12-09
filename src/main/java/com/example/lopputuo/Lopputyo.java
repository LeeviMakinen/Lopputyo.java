package com.example.lopputuo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Täysin ensimmäinen ohjelmointityö ikinä. Tässä vaiheessa ohjelmointia takana kirjaimellisesti yksi kurssi.
 * Paljon parannettavaa, mutta jätän tämän esille muistona, sekä nostalgisista syistä
 *
 *
 * Graafinen pääohjelma kurssin arvosanalaskurille, metodeineen
 * @author Leevi-Kalle Mäkinen
 * @version 1.0 2022/03/11
 */
public class Lopputyo extends Application implements Metodit{
    /**
     * Tehdään lista, jota käytetään myöhemmin kun luetaan useita tallennettuja olioita
     */
    ArrayList<Object> opiskelija2 = new ArrayList<>();

    /**
     * Eri tekstikenttiä ja nappeja
     */
    private TextField maxPisteet = new TextField();
    private TextField oppilaanNimi = new TextField();
    private TextField kurssinNimi = new TextField();
    private TextField oppilaanOpNumero = new TextField();
    private TextField oppilaanPisteet = new TextField();
    private TextField oppilaanArvosana = new TextField();


    private Button btTallenna = new Button("Tallenna dat tiedostoon");
    private Button btNaytaTallennetut = new Button("Näytä lista oppilaista");

    private Button btTyhjennaRuutu = new Button("Tyhjennä ruutu");
    private Button btLaske = new Button("Laske arvosana");
    private Button btPoistaTiedot = new Button("Poista dat tiedosto");

    /**
     * Opiskelija olio
     */
    private OpiskelijaTiedot opiskelija = new OpiskelijaTiedot();
    /**
     * @param alkuIkkuna
     * ohjelma
     */
    @Override
    public void start(Stage alkuIkkuna) {
        /**
         * paneeleita
         */
        BorderPane paakehys = new BorderPane();
        GridPane tekstiPaneeli = new GridPane();
        tekstiPaneeli.setHgap(7);
        tekstiPaneeli.setVgap(7);
        /**
         * Pidetään huoli, ettei näytettävänä laskun tuloksena tulevaa tekstikenttää voida muokata.
         */
        oppilaanArvosana.setEditable(false);
        /**
         * Sijoitetaan tekstipaneeliin sisältöä
         */
        tekstiPaneeli.add(new Label("Kurssin maksimi pisteet: "),0,0);
        tekstiPaneeli.add(maxPisteet, 1,0);
        tekstiPaneeli.add(new Label("Kurssin nimi: "),0,1);
        tekstiPaneeli.add(kurssinNimi, 1,1);
        tekstiPaneeli.add(new Label("Oppilaan nimi: "),0,2);
        tekstiPaneeli.add(oppilaanNimi, 1, 2);
        tekstiPaneeli.add(new Label("Opintonumero: "),0,3);
        tekstiPaneeli.add(oppilaanOpNumero, 1, 3);
        tekstiPaneeli.add(new Label("Kurssilta saadut pisteet: "), 0,4);
        tekstiPaneeli.add(oppilaanPisteet, 1, 4);
        tekstiPaneeli.add(new Label("Kurssin arvosana: "), 0,5);
        tekstiPaneeli.add(oppilaanArvosana, 1,5);
        tekstiPaneeli.add(btLaske,1,7);
        /**
         * Laitetaan kirjoituksen aloituskohta oikealle
         *
         */
        maxPisteet.setAlignment(Pos.CENTER_RIGHT);
        kurssinNimi.setAlignment(Pos.BOTTOM_RIGHT);
        oppilaanNimi.setAlignment(Pos.CENTER_RIGHT);
        oppilaanOpNumero.setAlignment(Pos.CENTER_RIGHT);
        oppilaanPisteet.setAlignment(Pos.CENTER_RIGHT);
        oppilaanArvosana.setAlignment(Pos.CENTER_RIGHT);
        /**
         * Sijoitetaan nappeja halutulle paikalle ja etäisyydelle
         */
        HBox hBox = new HBox(btTallenna, btNaytaTallennetut, btTyhjennaRuutu,btPoistaTiedot);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        /**
         * Sijoitetaan paneeleja kehykseen
         */
        paakehys.setCenter(tekstiPaneeli);
        paakehys.setBottom(hBox);
        /**
         * Tehdään tekstialusta, jossa näytetään tietoa. Estetään käyttäjältä mahdollisuus muokata sitä ja sijoitetaan kohdalleen
         */
        TextArea tf = new TextArea();
        tf.setEditable(false);
        tekstiPaneeli.add(tf,1,6);
        /**
         *  Seuraavassa kolmessa kohdassa määrätään, ettei tekstikenttiin joissa vaaditaan numeroita voida laittaa kirjaimia.
         */
        maxPisteet.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                maxPisteet.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        oppilaanOpNumero.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                oppilaanOpNumero.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        oppilaanPisteet.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                oppilaanPisteet.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        /**
         * Laske nappi käyttää metodeja, joilla lasketaan arvosana, sekä muutetaan Opiskelija olion parametrit syötteen mukaiseksi.
         * Sen lisäksi näytetään opiskelijan uudet muutetut tiedot tekstikentässä.
         */
        btLaske.setOnAction(e-> {
            laskeArvosana();
            muutaNimi();
            muutaKurssinNimi();
            muutaOpNumero();
            muutaPisteet();
            muutaArvosana();
            tf.setText(String.valueOf(opiskelija));
        });
        /**
         * Käytetään metodia, jolla olio tallennetaan dat tiedostoksi. Sen jälkeen ohjelma poistaa näkyviltä aiemmin täytettyt tekstikentät
         * @param talletaTiedosto
         */
        btTallenna.setOnAction(e-> {
            talletaTiedosto();
            maxPisteet.clear();
            kurssinNimi.clear();
            oppilaanNimi.clear();
            oppilaanOpNumero.clear();
            oppilaanPisteet.clear();
            oppilaanArvosana.clear();

        });
        /**
         * Nappi näyttää tekstikentässä dat tiedostoon tallennettujen olioiden arvot tekstimuodossa, eli käytännössä printtaa ne esille
         */
        btNaytaTallennetut.setOnAction(e->
                tf.setText(String.valueOf(lueTiedosto())));

        /**
         * Nappi tyhjentää tekstikentän, mutta ei tee tiedostoille mitään
         */
        btTyhjennaRuutu.setOnAction(e->
                tf.clear());

        /**
         * Nappi tyhjentää tekstikentän, sekä poistaa dat tiedoston, jossa oliot ovat tallennettu
         */
        btPoistaTiedot.setOnAction(e-> {
            tf.clear();
            try {
                new FileOutputStream("opiskelija.dat").close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        /**
         * Kehys jne
         */
        Scene kehys = new Scene(paakehys, 650,450);
        alkuIkkuna.setTitle("Kurssin arvosanalaskuri");
        alkuIkkuna.setScene(kehys);
        alkuIkkuna.show();
    }
    /**
     * Metodi muuttaa oppilaan nimeksi tekstin, minkä käyttäjä kirjoittaa tekstikenttään
     */
    public void muutaNimi(){
        opiskelija.setNimi(oppilaanNimi.getText());
    }
    /**
     * Metodi muuttaa kurssin nimeksi tekstin, minkä käyttäjä kirjoittaa tekstikenttään
     */
    public void muutaKurssinNimi(){
        opiskelija.setKurssi(kurssinNimi.getText());
    }
    /**
     * Metodi muuttaa tekstikentästä saadun tekstin numeroksi, ja muuttaa opiskelijan opiskelijanumeroksi sen mukaan
     */
    public void muutaOpNumero(){
        opiskelija.setOpNumero(Integer.parseInt(oppilaanOpNumero.getText()));
    }
    /**
     * Metodi muuttaa tekstikentästä saadun tekstin numeroksi, ja muuttaa opiskelijan pisteet sen mukaan
     */
    public void muutaPisteet(){
        opiskelija.setPisteet(Double.parseDouble(oppilaanPisteet.getText()));
    }
    /**
     * Laskee arvosanan käyttäen 50% läpipääsyrajaa.
     * Ottaa kurssin max pisteet maxpisteet tekstiruudusta ja muuttaa ne numeroiksi
     * Ottaa oppilaan pisteet oppilaanpisteet tekstiruudusta ja muuttaa ne numeroiksi
     * Laskee arvosanan ja asettaa oppilaanArvosana tekstiruutuun lasketun arvosanan
     */
    public void laskeArvosana() {
        int arvosana=0;
        double pisteet;
        double kurssinMaxPisteet;

        kurssinMaxPisteet = Integer.parseInt(maxPisteet.getText());

        pisteet = Double.parseDouble(oppilaanPisteet.getText());
        if (pisteet>= kurssinMaxPisteet*0.9) {
            arvosana = 5;
        }else if (pisteet>= kurssinMaxPisteet*0.8) {
            arvosana = 4;
        }else if (pisteet>= kurssinMaxPisteet*0.7) {
            arvosana = 3;
        }else if (pisteet >=kurssinMaxPisteet*0.6) {
            arvosana = 2;
        }else if (pisteet >= kurssinMaxPisteet*0.5) {
            arvosana = 1;
        }
        oppilaanArvosana.setText(String.valueOf(arvosana));
    }
    /**
     * Ottaa oppilaanArvosana tekstikentästä äsken sinne sijoitetun arvosanan ja muuttaa sen numeroksi.
     * Asettaa oppilaan olion parametrinä olevaksi arvosanaksi kyseisen arvon.
     */
    public void muutaArvosana(){
        opiskelija.setArvosana(Integer.parseInt(oppilaanArvosana.getText()));
    }
    /**
     * Tallentaa tiedoston oliotiedostoksi.
     * Tarkistaa, ettei olio ole sama, kuin joku aikaisemmin tallennetuista olioista.
     * Metodissa käytetään myös lueTiedosto metodia, koska ilman sitä tietyssä tilanteessa ensimmäinen olio ei toimi oikein :D
     * Poikkeuksienkäsittelyssä käytetty hieman
     * @author Erkki Pesonen
     * @version 1.0 2022/02/24 harjoitustyödemossa ollutta koodia, omaan käyttöön sovellettuna
     */
    public void talletaTiedosto() {

        try {
            FileOutputStream tiedosto = new FileOutputStream("opiskelija.dat");
            ObjectOutputStream oliotiedosto = new ObjectOutputStream(tiedosto);
            opiskelija2.add(opiskelija);
            Set<Object>setOfopiskelija2=new LinkedHashSet<>(opiskelija2);
            opiskelija2.clear();
            opiskelija2.addAll(setOfopiskelija2);
            oliotiedosto.writeObject(opiskelija2);

            System.out.println("kirjoitettiin tiedostoon");
            lueTiedosto();
            tiedosto.close();
        } catch (IOException e) {
            System.out.println("Virhe tiedostoon kirjoittamisessa. ");
        }
    }
    /**
     * @return opiskelija2
     * Tarkistaa, onko kyseistä tiedostoa olemassa. Jos tiedosto on olemassa, lisätään opiskelija2 listaan kaikki luetut objektit
     * ja palautetaan kysyinen lista. Tämän palautuksen String arvoa käytetään, jotta tallennetut tiedot pystytään näyttämään
     * Poikkeuksienkäsittelyssä käytetty hieman
     * @author Erkki Pesonen
     * @version 1.0 2022/02/24 harjoitustyödemossa ollutta koodia, omaan käyttöön sovellettuna
     */
    public ArrayList<Object> lueTiedosto() {
        File opiskelijaTiedosto = new File("opiskelija.dat");
        if (opiskelijaTiedosto.exists()) {
            try {
                FileInputStream tiedosto = new FileInputStream("opiskelija.dat");
                ObjectInputStream tiedostoOlio = new ObjectInputStream(tiedosto);
                this.opiskelija2 = (ArrayList<Object>) tiedostoOlio.readObject();
                System.out.println("luen tiedostoa");
                tiedosto.close();
            } catch (EOFException e) {
                opiskelija2.removeAll(opiskelija2);
                System.err.println("Virhe. Tiedostosta yritettiin lukea tiedoston lopun ohi.");
            } catch (IOException e) {
                System.err.println("Virhe. Tiedosto löytyi, mutta lukeminen päättyi virheeseen.");
            } catch (ClassNotFoundException e) {
                System.err.println("Virhe. Sarjallistettua luokkaa ei löytynyt.");
            }
        } else {
            System.out.println("Tiedostoa ei löytynyt.");
        }
        return this.opiskelija2;
    }


    public static void main(String[] args) {
        launch(args);
    }
}