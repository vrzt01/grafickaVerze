/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;


import java.util.*;
import util.ObserverBatoh;
import util.ObserverZmenyProstoru;
import util.SubjektZmenyBatohu;

/*******************************************************************************
 * Instance třídy Batoh představují ...
 *
  * @author    Tomáš Vrzák
 * @version   LS 2016/17
 */
public class Batoh implements SubjektZmenyBatohu
{
    private Map<String, Vec> batoh;
    private List<ObserverBatoh> seznamPozorovatelu;
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     */
    public Batoh ()
    {
        this.batoh = new HashMap<>();
        seznamPozorovatelu = new ArrayList<>();
    }
    
    /**
     *  Zobrazuje velikost batohu.
     *  
     *  @return int aktuální velikost (počet věcí v batohu) batohu
     */ 
    public int  velikostBatohu(){
        return batoh.size();
    }
    
    /**
     *  Vkládá věc do batohu.
     *  
     *  @param název vkládané věci
     */     
    public void vlozVecDoBatohu(Vec neco){
        batoh.put(neco.getNazev(),neco);
    }        
    
    /**
     *  Odebírá věc z batohu.
     *  
     *  @param název vkládané věci
     *  @return Vec odebíraná věc
     */ 
    public Vec odeberVecZBatohu(String nazev){
        return batoh.remove(nazev);
    }
    
    /**
     *  Vrátí věc z batohu
     *  
     *  @param String název věci k vrácení
     *  @return věc, která se má vrátit
     */
    public Vec ziskejVec(String nazevVeci){   
        Vec hledanaVec = null;
        for (String nazev : batoh.keySet()) {
            hledanaVec = batoh.get(nazevVeci);         
        }          
        return hledanaVec;
    }
    
    /**
     *  Zobrazí obsah batohu.
     *  
     *  @return String text obsahu
     */
    public String obsahBatohu(){
        String vracenyText = null;
        if (batoh.isEmpty()) {
            vracenyText = "Batoh je prázdný.";
        } else {
            vracenyText = "V batohu máš tyto věci: | ";
            for (String nazev : batoh.keySet()) {                
                vracenyText += nazev + " | ";            
            }
        }
        return vracenyText;
    }
    
    public Set<Vec> seznamVeciVBatohu () {        
        Set<Vec> veci = new HashSet<>();
        for (String nazev : batoh.keySet()) {                
            Vec v = batoh.get(nazev);
            veci.add(v);
        }
        return veci;
    }
    
    
    public void zaregistrujPozorovatele(ObserverBatoh pozorovatel)
      {
        seznamPozorovatelu.add(pozorovatel);
      }

    public void odregistrujPozorovatele(ObserverBatoh pozorovatel)
      {
        seznamPozorovatelu.remove(pozorovatel);
      }

    public void upozorniPozorovatele()
      {
        for (ObserverBatoh pozorovatel : seznamPozorovatelu) {
            pozorovatel.aktualizuj();
        }
      }
    
}
    
