/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

/*******************************************************************************
 * Instance třídy PrikazSeber představují ...
 *
 * @author    Tomáš Vrzák
 * @version   LS 2016/17
 */
public class PrikazSeber implements IPrikaz
{
    //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "seber";
    private HerniPlan plan;
    private Batoh batoh;
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     */
    public PrikazSeber(HerniPlan plan, Batoh batoh)
    {
        this.plan = plan;
        this.batoh = batoh;
    }

    /**
     *  Metoda pro provedení příkazu "seber"
     *  
     *  Sbíraná věc musí v prostoru existovat a musí být přenositelná.
     *  Velikost batohu je 6. Pokud je plný, věc se nesebere a zůstává v aktuálním prostoru.
     *  Jinak se věc sebere a přidá do batohu.
     *  
     *  @param název věci, která se má sebrat do batohu
     *  @return String vrácený text hráči
     */
    public String proved(String... parametry){
        String nazevSbiraneVeci = null; // = parametry[0] + " " + parametry[1] + " " + parametry[2];
        if (parametry.length == 1) {
            nazevSbiraneVeci = parametry[0];           
        }
        if (parametry.length == 2) {
            nazevSbiraneVeci = parametry[0] + " " + parametry[1];           
        }
        if (parametry.length == 3) {
            nazevSbiraneVeci = parametry[0] + " " + parametry[1] + " " + parametry[2];           
        }

        Prostor aktualni = plan.getAktualniProstor();        
        Vec sbiranaVec = aktualni.odeberVec(nazevSbiraneVeci);
        String vracenyText = null;

        if(parametry.length == 0){        
            vracenyText = "Co chceš sebrat?";
        } else {                                   

            if(sbiranaVec == null){
                vracenyText = "Tato věc tu není nebo nemůžeš sebrat tento objekt.";
            } else { 
                if(aktualni.getNazev().equals("půda") && aktualni.ziskejPostavu("ghůl").getAgresivni()){
                    aktualni.ziskejPostavu("ghůl").setKonec(true);
                    vracenyText ="Sežral tě agresivní a hladový ghůl";
                }
                else{
                    if(sbiranaVec.jePrenositelna()){ 
                        if (batoh.velikostBatohu() < 6) {
                            vracenyText = "Sebráno do batohu!\n";
                            batoh.vlozVecDoBatohu(sbiranaVec);                                             
                        } else { 
                            vracenyText = "Batoh je plný!";
                        }

                    } else{
                        aktualni.vlozVec(sbiranaVec);
                        vracenyText = "To se nedá nést sebou.";
                    }    
                }
            }            
        }
        return vracenyText;
    }
    //== Nesoukromé metody (instancí i třídy) ======================================
    public String getNazev() {
        return NAZEV;
    }
//     public boolean jeSunka(){
//     if(plan.getAktualniProstor().ziskejVec("šunka").equals("šunka")){
//     return false;
//     }
//     else{
//     return true;
//     }
//     }
    //== Soukromé metody (instancí i třídy) ========================================

}
