/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import logika.HerniPlan;
import util.ObserverZmenyProstoru;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author Tomáš Vrzák
 */
public class ZobrazeniProstoru implements ObserverZmenyProstoru {
    
    HerniPlan plan ;
    private FlowPane horniCelyPanel;
    TextArea popisTextArea ;
    Circle tecka ;
    
        public ZobrazeniProstoru(HerniPlan plan) {
        this.plan = plan;
        plan.zaregistrujPozorovatele(this);
        init();
    }
        
    public void init() {
        
        popisTextArea = new TextArea();
        popisTextArea.setPrefWidth(350);
        popisTextArea.setText(plan.getAktualniProstor().dlouhyPopis());
        popisTextArea.setEditable(false);
        
        horniCelyPanel = new FlowPane();
        
        horniCelyPanel.getChildren().add(popisTextArea);
        horniCelyPanel.getChildren().add(nastavHorniPanel());
        
        aktualizuj(); 

        
        
    }    


    private AnchorPane nastavHorniPanel()
      {
        AnchorPane obrazekPane = new AnchorPane();
        Image obrazekImage = new Image(main.class.getResourceAsStream("/sources/plan.PNG"), 800, 400, false, false); 

        ImageView obrazekImageView = new ImageView(obrazekImage);

        tecka = new Circle(10, Paint.valueOf("green"));
        AnchorPane.setTopAnchor(tecka, plan.getAktualniProstor().getTop());
        AnchorPane.setLeftAnchor(tecka, plan.getAktualniProstor().getLeft());

        obrazekPane.getChildren().add(obrazekImageView);
        obrazekPane.getChildren().add(tecka);

        return obrazekPane;
      }
    
    public FlowPane getPanel () {
        return horniCelyPanel;
    }
    
        @Override
    public void aktualizuj() {
        // aktualizuj popis prostoru
	popisTextArea.setText(plan.getAktualniProstor().dlouhyPopis());
        //aktualizuj umisteni tecky  
        AnchorPane.setTopAnchor(tecka, plan.getAktualniProstor().getTop());
        AnchorPane.setLeftAnchor(tecka, plan.getAktualniProstor().getLeft());    

}
         /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry.
     * 
     * @param plan
     */
    public void nastaveniHernihoPlanu (HerniPlan plan){
        this.plan = plan;
        plan.zaregistrujPozorovatele(this);
        this.aktualizuj();
    }  
    
}
