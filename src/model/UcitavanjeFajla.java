package model;

import model.PodatakZaTabelu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UcitavanjeFajla {
    private static List<String> listaGradova;
    private static List<PodatakZaTabelu> listaPodatakaZaTabelu;
    private final static ObservableList<PodatakZaTabelu> observableListaYaTabelu;

    public static List<PodatakZaTabelu> getListaPodatakaZaTabelu() {
        return listaPodatakaZaTabelu;
    }

    public static ObservableList<PodatakZaTabelu> getObservableListaYaTabelu() {
        return observableListaYaTabelu;
    }

    static {
        listaPodatakaZaTabelu= new ArrayList<>(); //null point e
        listaGradova = new ArrayList<>();
        observableListaYaTabelu= FXCollections.observableArrayList((listaPodatakaZaTabelu));

        ucitaj();
    }

    public static List<String> getListaGradova() {
        return listaGradova;
    }
    //nije htelo da radi  jer su bile \ umesto / u adresi



    public static void ucitaj(){
        //ucitavanje gradova za combobox
        String line;

        try {
            FileReader fr = new FileReader("C:/Users/natas/Desktop/projektni-zadatak-ZeroOne17-hub/src/model/grad.txt");
            BufferedReader bf = new BufferedReader(fr);

            while ((line = bf.readLine()) != null) {
                listaGradova.add(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        //obs lista
        ObservableList<String> obsLista = FXCollections.observableList(getListaGradova());

        String line1;
        try {
            FileReader fr = new FileReader("C:/Users/natas/Desktop/projektni-zadatak-ZeroOne17-hub/src/model/paketi.txt");
            BufferedReader bf = new BufferedReader(fr);

            while ((line1 = bf.readLine()) != null) {
                String[] tokens = line1.split(",");
                if (tokens.length == 2) {
                    String[] subTokens = tokens[0].split("-");
                    if (subTokens.length == 2) {
                        String od = subTokens[0];
                        String za = subTokens[1];
                        String id = tokens[1];

                        PodatakZaTabelu p = new PodatakZaTabelu(id, od, za);
                        listaPodatakaZaTabelu.add(p);
                    }
                }
                for(PodatakZaTabelu p: listaPodatakaZaTabelu){
                    System.out.println(p.toString());
                }

            }
        }catch (IOException e) {
            e.printStackTrace();
        }





    }



}
