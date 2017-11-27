/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import logika.Hra;
import logika.IHra;
import uiText.TextoveRozhrani;

/**
 *
 * @author Tomáš Vrzák
 */
public class main extends Application {

    IHra hra;
    TextArea centerTextArea;
    BorderPane border;
    BorderPane leftBorder;
    TextField prikazTextField;
    ZobrazeniProstoru zobrazeniProstoru;
    PanelVychodu panelVychodu;
    OknoBatohu oknoBatohu;
    OknoProstoru oknoProstoru;
    MenuBar menuBar;

    @Override
    public void start(Stage primaryStage) {

        hra = new Hra();
        nastavTextArea();
        border = new BorderPane();
        leftBorder = new BorderPane();

        border.setCenter(centerTextArea);

        border.setBottom(nastavDolniPanel());

        zobrazeniProstoru = new ZobrazeniProstoru(hra.getHerniPlan());
        border.setTop(zobrazeniProstoru.getPanel());
        
        oknoBatohu = new OknoBatohu(hra);
        border.setLeft(oknoBatohu);
        
        oknoProstoru = new OknoProstoru(hra);
        leftBorder.setLeft(oknoProstoru);

        panelVychodu = new PanelVychodu(hra.getHerniPlan(), hra);
        border.setRight(panelVychodu.getList());
        
        leftBorder.setCenter(border);
        
        initMenu();
        VBox vBox = new VBox();
        vBox.getChildren().addAll(menuBar,leftBorder);

        Scene scene = new Scene(vBox, 1800, 800);

        primaryStage.setTitle("Hra - Strašidelný dům");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            launch(args);
        } else if (args[0].equals("-text")) {
            IHra hra = new Hra();
            TextoveRozhrani ui = new TextoveRozhrani(hra);
            ui.hraj();
        } else {
            System.out.println("Neplatny parametr");
        }
    }

    private void nastavTextArea() {
        centerTextArea = new TextArea();
        centerTextArea.setText(hra.vratUvitani());
        centerTextArea.setEditable(false);
    }

    private void initMenu() {

        menuBar = new MenuBar();

        Menu menuSoubor = new Menu("Soubor");
        Menu menuNapoveda = new Menu("Nápověda");
                
        
        MenuItem novaHra = new MenuItem("Nova hra", new ImageView(new Image(main.class.getResourceAsStream("/sources/new.gif"))));
        novaHra.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        novaHra.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                hra = new Hra();
                zobrazeniProstoru.nastaveniHernihoPlanu(hra.getHerniPlan());
                panelVychodu.nastaveniHernihoPlanu(hra.getHerniPlan(), hra);
                oknoBatohu.novaHra(hra);
                oknoProstoru.novaHra(hra);
                centerTextArea.setText(hra.vratUvitani());
                prikazTextField.requestFocus();

            }
        });

        MenuItem konec = new MenuItem("_Konec");
        konec.setMnemonicParsing(true);

        konec.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        
        MenuItem napoveda = new MenuItem("Nápověda");
        napoveda.setAccelerator(KeyCombination.keyCombination("Ctrl+I"));
        napoveda.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               Stage stage = new Stage();               
               stage.setTitle("nápověda");
               WebView webView = new WebView();
               webView.getEngine().load(
               main.class.getResource("/sources/napoveda.html").toExternalForm());
               stage.setScene(new Scene(webView,1200,665));
               stage.show();
            }
        });

        menuSoubor.getItems().addAll(novaHra, new SeparatorMenuItem(), konec);
        menuNapoveda.getItems().addAll(new SeparatorMenuItem(),napoveda);

        menuBar.getMenus().add(menuSoubor);
        menuBar.getMenus().addAll(menuNapoveda);
    }

    private FlowPane nastavDolniPanel() {
        FlowPane dolniFlowPane = new FlowPane();
        Label zadejPrikazLabel = new Label("Zadej prikaz: ");
        zadejPrikazLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        prikazTextField = new TextField();
        prikazTextField.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String radek = prikazTextField.getText();
                String text = hra.zpracujPrikaz(radek);
                centerTextArea.appendText("\n\n" + radek + "\n");
                centerTextArea.appendText("\n" + text + "\n");
                prikazTextField.setText("");
                if (hra.konecHry()) {
                    prikazTextField.setEditable(false);
                }
            }

        });

        dolniFlowPane.getChildren().addAll(zadejPrikazLabel, prikazTextField);
        dolniFlowPane.setAlignment(Pos.CENTER);
        return dolniFlowPane;
    }
}
