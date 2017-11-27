package main;

import util.ObserverBatoh;
import java.text.Normalizer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import logika.Batoh;
import logika.IHra;
import logika.Vec;
import util.ObserverBatoh;
import util.ObserverZmenyProstoru;

/**
 * Reprezentuje ListView s východy
 * @author vrzt01
 * @created listopad 2017
 */
public class OknoProstoru extends ListView implements ObserverZmenyProstoru {
    
    private IHra hra;
    private ObservableList<String> dataProstoru = FXCollections.observableArrayList();
    
    
    
    /**
     * Konstruktor
     * @param hra 
     */
    public OknoProstoru (IHra hra) {
        this.hra = hra;                
        init();
        
        this.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> observable,
              String oldValue, String newValue) {
              if(observable.getValue() != null) {
                hra.zpracujPrikaz("seber "+observable.getValue());
              }
            
          }
        });
        /*
        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                //hra.getHerniPlan().getAktualniProstor().vlozVec(hra.getBatoh().ziskejVec(newValue.toString()));
                //hra.getBatoh().odeberVecZBatohu(newValue.toString());
                hra.zpracujPrikaz("seber "+newValue.toString());
                //hra.getHerniPlan().upozorniPozorovatele();
                //hra.getBatoh().upozorniPozorovatele(); 
            }
                       
        });*/
    }
    
    /**
     * Resetuje obsah seznamu při započetí nové hry
     * @param hra 
     */
    public void novaHra(IHra hra) {
        this.hra.getHerniPlan().odregistrujPozorovatele(this);
        this.hra = hra;
        this.hra.getHerniPlan().zaregistrujPozorovatele(this);
        aktualizuj();
        // Handle ListView selection changes.
        
    }
    
    public void init() {
        this.setItems(dataProstoru);
        this.setPrefWidth(300);
        hra.getHerniPlan().zaregistrujPozorovatele(this);
        
        this.setCellFactory(param -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    String imageNazev = Normalizer.normalize(name, Normalizer.Form.NFD);
                    imageNazev = imageNazev.replaceAll("[^\\p{ASCII}]", "");
                    imageView.setImage(new Image("/sources/"+imageNazev+".png"));
                    setText(name);
                    setGraphic(imageView);
                }
            }
        });                                        
        
        aktualizuj();
        
        
    }

    @Override
    public void aktualizuj() {
        dataProstoru.clear();
        for(Vec vec : hra.getHerniPlan().getAktualniProstor().seznamVeciVProstoru()) {
            dataProstoru.add(vec.getNazev());
        }       
        
       
    }
    
     
    
}
