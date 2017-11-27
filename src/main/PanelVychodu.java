/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import logika.HerniPlan;
import logika.Hra;
import logika.IHra;
import util.ObserverZmenyProstoru;

/**
 *
 * @author Tomáš Vrzák
 */
public class PanelVychodu implements ObserverZmenyProstoru
{
     private HerniPlan plan;
     private IHra hra;
    ListView<String> list;
    ObservableList<String> data;

    public PanelVychodu(HerniPlan plan, IHra hra) {
       this.plan = plan;
       this.hra = hra;
       plan.zaregistrujPozorovatele(this);
       init();       
    }

    private void init() {
        list = new ListView<>();
        data = FXCollections.observableArrayList();
        list.setItems(data);
        list.setPrefWidth(200);
        
        
        
        aktualizuj();
        /*
        String vychody = plan.getAktualniProstor().seznamVychodu();

        String[] oddeleneVychody = vychody.split(" ");
        for (int i = 1; i < oddeleneVychody.length; i++) {
            data.add(oddeleneVychody[i]);            
        }*/
}
    
     public ListView<String> getList() {        
         return list;
        
    }
     
     @Override
         public void aktualizuj() {
        String vychody = plan.getAktualniProstor().seznamVychodu();
        data.clear();
        String[] oddeleneVychody = vychody.split(" ");
        for (int i = 1; i < oddeleneVychody.length; i++) {
            data.add(oddeleneVychody[i]);
        }
        /*
        // Handle ListView selection changes.
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            //plan.setAktualniProstor(plan.getAktualniProstor().vratSousedniProstor(newValue.toString()));
            hra.zpracujPrikaz("jdi "+newValue.toString());
            //plan.upozorniPozorovatele();
        });*/
        
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            hra.zpracujPrikaz("jdi "+list.getSelectionModel().getSelectedItem()); 
            list.getSelectionModel().clearSelection();
        }
    });
        
    }
         /**
     * Metoda zaregistruje pozorovatele k hernímu plánu při spuštění nové hry.
     * @param plan
     */
    public void nastaveniHernihoPlanu (HerniPlan plan, IHra hra){
        this.plan = plan;
        this.hra = hra;
        plan.zaregistrujPozorovatele(this);                      
        this.aktualizuj();
        
        
        
    }



}
