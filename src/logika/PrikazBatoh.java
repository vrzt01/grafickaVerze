/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;



/*******************************************************************************
 * Instance třídy PrikazBatoh představují ...
 *
 * @author    Tomáš Vrzák
 * @version   LS 2016/17
 */
public class PrikazBatoh implements IPrikaz
{
     //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "batoh";
    private Batoh batoh;
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     */
    public PrikazBatoh(Batoh batoh)
    {        
        this.batoh = batoh; 
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     *  Provádí příkaz "batoh". 
     *  
     *  Zobrazuje aktuální obsah batohu.
     *  
     *  @param parametry - nejsou potřeba žádné
     *  @return zpráva, kterou vypíše hra hráči
     */ 
    public String proved(String... parametry){
        if (parametry.length >= 1) {
            return "Staci zadat pouze nazev prikazu.";
        } else {
           return batoh.obsahBatohu();
        }
        }
    

    /**
     *  Metoda vrací název příkazu, jedná se o slovo, které příkaz vyvolá.
     *  
     *  @return nazev prikazu
     */
    public String getNazev(){
        return NAZEV;
    }
}
