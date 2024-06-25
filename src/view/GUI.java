package view;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.PodatakZaTabelu;
import model.UcitavanjeFajla;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GUI {
    private Label pretraga;
    private TextField poljeZaPretragu;
    private CheckBox nepoznatoOd;
    private CheckBox nepoznatoZa;
    private Button filtriraj;
    private ComboBox<String> gradovi;
    private Button dodeliGrad;
    private Button ispis;
    private Label ispisNaDnu;
    private static TableView<PodatakZaTabelu> tabela;
    public void start(Stage primaryStage) throws Exception {
        UcitavanjeFajla f = new UcitavanjeFajla();  //zbog static bloka

        primaryStage.setTitle("NATASA ZIVANOVIC");
        primaryStage.setWidth(800);
        primaryStage.setHeight(500);
        primaryStage.show();

        // Definicija svega sto mi treba za boxeve
        // - - - - - - - - - - - - - - - - - - -  - - -  - - - - -
        pretraga = new Label();
        pretraga.setText("Pretraga:");

        poljeZaPretragu = new TextField();
        nepoznatoOd = new CheckBox();
        nepoznatoOd.setText("Nepoznato OD");


        nepoznatoZa = new CheckBox();
        nepoznatoZa.setText("Nepoznato ZA");


        FilteredList filtrirajPakete = new FilteredList<>(UcitavanjeFajla.getObservableListaYaTabelu(), e->true);

        gradovi = new ComboBox<>();
        for (String x : UcitavanjeFajla.getListaGradova()) {
            gradovi.getItems().add(x);
        }
        gradovi.setValue("Beograd");



        ispisNaDnu = new Label();
        ispisNaDnu.setText("Morate izabrati grad za dodelu.");


        TableView<PodatakZaTabelu> tab = new TableView<>();
        TableColumn<PodatakZaTabelu, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableView<PodatakZaTabelu> tabela = new TableView<>();
        tabela.setPrefWidth(600);
        tabela.setPrefHeight(800);

        TableColumn<PodatakZaTabelu, String> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(200);

        TableColumn<PodatakZaTabelu, String> odCol = new TableColumn<>("Od");
        odCol.setCellValueFactory(new PropertyValueFactory<>("od"));
        idCol.setPrefWidth(200);

        TableColumn<PodatakZaTabelu, String> zaCol = new TableColumn<>("Za");
        zaCol.setCellValueFactory(new PropertyValueFactory<>("za"));
        idCol.setPrefWidth(200);

        tabela.getColumns().addAll(idCol, odCol, zaCol);
        for (PodatakZaTabelu x: UcitavanjeFajla.getListaPodatakaZaTabelu()){
            tabela.getItems().add(x);
        }
        //tabela.setItems(filtrirajPakete);

        filtriraj = new Button();
        filtriraj.setText("Filtriraj");
        filtriraj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String slovo = poljeZaPretragu.getText();
                if(!UcitavanjeFajla.getObservableListaYaTabelu().isEmpty()){
                    UcitavanjeFajla.getObservableListaYaTabelu().clear();
                }

                for(int i=0; i<UcitavanjeFajla.getListaPodatakaZaTabelu().size();i++){
                    if((slovo.equalsIgnoreCase((UcitavanjeFajla.getListaPodatakaZaTabelu()).get(i).getId().substring(0,1))) || (slovo.equalsIgnoreCase(UcitavanjeFajla.getListaPodatakaZaTabelu().get(i).getOd().substring(0,1))) || (slovo.equalsIgnoreCase(UcitavanjeFajla.getListaPodatakaZaTabelu().get(i).getOd().substring(0,1))) || (slovo.equalsIgnoreCase(UcitavanjeFajla.getListaPodatakaZaTabelu().get(i).getId().substring(1,2)))){
                        UcitavanjeFajla.getObservableListaYaTabelu().add(UcitavanjeFajla.getListaPodatakaZaTabelu().get(i));
                        System.out.println("IMAAAA");
                    }else System.out.println("Nema grada sa tim slovom");
                }



                for(int i=0; i<UcitavanjeFajla.getObservableListaYaTabelu().size();i++) {
                    if (nepoznatoOd.isSelected() && nepoznatoZa.isSelected()) {
                        System.out.println("Both checkboxes are selected.");
                    } else if (nepoznatoOd.isSelected()) {
                        System.out.println("Option 1 is selected.");
                        for (int k = 0; k < UcitavanjeFajla.getObservableListaYaTabelu().size(); k++) {
                            if (UcitavanjeFajla.getObservableListaYaTabelu().get(k).getOd().equals("X")) {
                                continue;
                            }else{
                                UcitavanjeFajla.getObservableListaYaTabelu().remove(k);
                            }
                        }
                    } else if (nepoznatoZa.isSelected()) {
                        System.out.println("Option 2 is selected.");
                        for (int j = UcitavanjeFajla.getObservableListaYaTabelu().size() - 1; j >= 0; j--) {
                            if (!UcitavanjeFajla.getObservableListaYaTabelu().get(j).getZa().equals("X")) {
                                UcitavanjeFajla.getObservableListaYaTabelu().remove(j);
                            }
                        }
                    } else {
                        System.out.println("Neither checkbox is selected.");
                    }


                }
                tabela.setItems(filtrirajPakete);
                filtrirajPakete.setPredicate(e -> true); // Postavljanje osnovnog filtera
                poljeZaPretragu.clear();
            }

        });
        dodeliGrad = new Button();
        dodeliGrad.setText("Dodeli grad");
        dodeliGrad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PodatakZaTabelu p = tabela.getSelectionModel().getSelectedItem();
                if(!p.getZa().equals("X") && !p.getOd().equals("X")){
                    System.out.println("Nije moguce dodeliti grad paketu kome su oba grada poznata.");
                    ispisNaDnu.setText("Nije moguce dodeliti grad paketu kome su oba grada poznata.");

                }
                int indeks = UcitavanjeFajla.getObservableListaYaTabelu().indexOf(p);
                if (p == null) {
                    ispisNaDnu.setText("Niste izabrali nijedan red.");
                    return;
                }
                if (indeks == -1) {
                    ispisNaDnu.setText("Izabrani red ne postoji u listi.");
                    return;
                }
                if (p.getOd().equals("X")) {
                    String od = gradovi.getValue();
                    if (od.substring(0, 1).equals(p.getId().substring(0, 1))) {
                        PodatakZaTabelu novo = new PodatakZaTabelu(p.getId(), od, p.getZa());
                        UcitavanjeFajla.getObservableListaYaTabelu().set(indeks, novo);
                        System.out.println("Dodat je od");
                        ispisNaDnu.setText("Uspesno dodat grad");

                    }else{
                        ispisNaDnu.setText("Izabrani grad ne odgovara vrednosti Id-a.");
                    }
                }
                if (p.getZa().equals("X")) {
                    String za = gradovi.getValue();
                    if (za.substring(0, 1).equals(p.getId().substring(1, 2))) {
                        PodatakZaTabelu novo = new PodatakZaTabelu(p.getId(), p.getOd(), za);
                        UcitavanjeFajla.getObservableListaYaTabelu().set(indeks, novo);
                        System.out.println("Dodat je za");
                        ispisNaDnu.setText("Uspesno dodat grad");

                    }

                }
            }
        });

        ispis = new Button();
        ispis.setText("Ispis");
        ispis.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("ispis.txt"))) {
                    List<PodatakZaTabelu> nepoznatoOdPaketi = new ArrayList<>();
                    List<PodatakZaTabelu> nepoznatoZaPaketi = new ArrayList<>();
                    List<PodatakZaTabelu> sredjeniPaketi = new ArrayList<>();

                    for (PodatakZaTabelu p : UcitavanjeFajla.getObservableListaYaTabelu()) {
                        if (p.getOd().equals("X")) {
                            nepoznatoOdPaketi.add(p);
                        } else if (p.getZa().equals("X")) {
                            nepoznatoZaPaketi.add(p);
                        } else {
                            sredjeniPaketi.add(p);
                        }
                    }

                    // Sortiranje paketa
                    Collections.sort(nepoznatoOdPaketi, Comparator.comparing(PodatakZaTabelu::getOd).thenComparing(PodatakZaTabelu::getZa).thenComparing(PodatakZaTabelu::getId));
                    Collections.sort(nepoznatoZaPaketi, Comparator.comparing(PodatakZaTabelu::getZa).thenComparing(PodatakZaTabelu::getOd).thenComparing(PodatakZaTabelu::getId));
                    Collections.sort(sredjeniPaketi, Comparator.comparing(PodatakZaTabelu::getOd).thenComparing(PodatakZaTabelu::getZa).thenComparing(PodatakZaTabelu::getId));

                    // Ispis u fajl
                    if (!nepoznatoOdPaketi.isEmpty()) {
                        writer.write("Paketi sa nepoznatim polazistem:\n");
                        for (PodatakZaTabelu p : nepoznatoOdPaketi) {
                            writer.write(p.getOd() + "-" + p.getZa() + "," + p.getId() + "\n");
                        }
                        writer.newLine();
                    }

                    if (!nepoznatoZaPaketi.isEmpty()) {
                        writer.write("Paketi sa nepoznatim odredistem:\n");
                        for (PodatakZaTabelu p : nepoznatoZaPaketi) {
                            writer.write(p.getOd() + "-" + p.getZa() + "," + p.getId() + "\n");
                        }
                        writer.newLine();
                    }

                    if (!sredjeniPaketi.isEmpty()) {
                        writer.write("Sredjeni paketi:\n");
                        for (PodatakZaTabelu p : sredjeniPaketi) {
                            writer.write(p.getOd() + "-" + p.getZa() + "," + p.getId() + "\n");
                        }
                        writer.newLine();
                    }

                    System.out.println("Podaci su uspešno upisani u fajl.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });




        // - - - - - - - - - - - - - - - - - - -  - - -  - - - - -

        //       MANJI HBOXEVI ZA VRH
        HBox pretragaHBox = new HBox();
        pretragaHBox.getChildren().addAll(pretraga, poljeZaPretragu);
        pretragaHBox.setSpacing(10);

        HBox nepoznatoOdHbox = new HBox();
        nepoznatoOdHbox.getChildren().add(nepoznatoOd);
        nepoznatoOdHbox.setSpacing(10);

        HBox nepoznatoZaHBox = new HBox();
        nepoznatoZaHBox.getChildren().add(nepoznatoZa);
        nepoznatoZaHBox.setSpacing(10);

        HBox dugmeFiltriraj = new HBox();
        dugmeFiltriraj.getChildren().addAll(filtriraj);
        dugmeFiltriraj.setSpacing(10);

        // - - - - - - - - - - - - - - - - - - -  - - -  - - - - -
        //    VRH
        HBox vrh = new HBox();
        vrh.getChildren().addAll(pretragaHBox, nepoznatoOdHbox, nepoznatoZaHBox, dugmeFiltriraj);
        vrh.setSpacing(10);
        vrh.setAlignment(Pos.CENTER_RIGHT);
        // - - - - - - - - - - - - - - - - - - -  - - -  - - - - -
        // Definišemo HBox za dugmad
        VBox dugmadBox = new VBox();
        dugmadBox.setAlignment(Pos.CENTER);
        dugmadBox.setSpacing(10);


        dugmadBox.getChildren().addAll(dodeliGrad, ispis);

        //     LEVO
        VBox levi = new VBox();
        levi.getChildren().addAll(gradovi,dugmadBox);
        levi.setSpacing(10);
        levi.setPadding(new Insets(10, 10, 10, 10));
        levi.setAlignment(Pos.CENTER_RIGHT);
        // - - - - - - - - - - - - - - - - - - -  - - -  - - - - -
        //     DNO
        HBox dno = new HBox();
        dno.getChildren().add(ispisNaDnu);
        dno.setAlignment(Pos.BOTTOM_LEFT);
        // - - - - - - - - - - - - - - - - - - -  - - -  - - - - -
        //     TABELA
        tabela.setMaxHeight(320);
        HBox tabelaCentar = new HBox(tabela);
        tabelaCentar.setAlignment(Pos.CENTER);
        tabelaCentar.setPrefHeight(320);
        tabelaCentar.setMinWidth(400);
        // - - - - - - - - - - - - - - - - - - -  - - -  - - - - -

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setTop(vrh);
        borderPane.setBottom(dno);
        borderPane.setLeft(levi);
        borderPane.setCenter(tabelaCentar);

        Scene scena = new Scene(borderPane, 800, 500);
        primaryStage.setScene(scena);
        primaryStage.show();


    }




    public Label getPretraga() {
        return pretraga;
    }

    public TextField getPoljeZaPretragu() {
        return poljeZaPretragu;
    }

    public CheckBox getNepoznatoOd() {
        return nepoznatoOd;
    }

    public CheckBox getNepoznatoZa() {
        return nepoznatoZa;
    }

    public Button getFiltriraj() {
        return filtriraj;
    }

    public ComboBox<String> getGradovi() {
        return gradovi;
    }

    public Button getDodeliGrad() {
        return dodeliGrad;
    }

    public Button getIspis() {
        return ispis;
    }

    public Label getIspisNaDnu() {
        return ispisNaDnu;
    }

    public static TableView<PodatakZaTabelu> getTabela() {
        return tabela;
    }
}
