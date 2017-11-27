/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package logika;

import java.util.*;
/*******************************************************************************
 * Instance třídy PrikazOdemkni představují ...
 *
 * @author    Tomáš Vrzák
 * @version   LS 2016/17
 */
public class PrikazOdemkni implements IPrikaz
{ //== Datové atributy (statické i instancí)======================================
    private static final String NAZEV = "odemkni";
    private HerniPlan plan;
    private String celyKod;
    private int pocetPokusu;
    //== Konstruktory a tovární metody =============================================

    /***************************************************************************
     *  Konstruktor ....
     */
    public PrikazOdemkni(HerniPlan plan, String celyKod, int pocetPokusu)
    {
        this.plan = plan;  
        this.celyKod = celyKod;
        this.pocetPokusu = pocetPokusu;
    }

    //== Nesoukromé metody (instancí i třídy) ======================================

    /**
     *  Provádí příkaz "odemkni". 
     *  
     *  Muzeme odemknout pouze sousední prostor, ostatni 
     *  V teto metode je vyreseno ukonceni hry. K odemceni prostoruje potreba zadat 8 mistny kod, ktery zle ziskat v sousednich prostorech
     *  Hrac muze zkusit zadat vystupni kod pouze 6x. Po kazdem pokusu ve vypise pocet zbyvajicich pokusu. Hráč může kdykoliv odejít zpět do
     *  domu a hledat další nápovědy pomocí příkazu "zpět".
     *  
     *  @param parametry - jako  parametr obsahuje jméno prostoru, který se má odemknout.
     *                         
     *  @return zpráva, kterou vypíše hra hráči
     */ 
    public String proved(String... parametry){
        String vracenyText = null;        
        Prostor aktualni = plan.getAktualniProstor();                   
        String nazevOdemykatelnehoProstoru = null;
        Prostor odemykatelny = null;

        if (parametry.length == 0){        
            vracenyText =  "Co chceš odemknout?";
        }
        if (parametry.length == 1) {
            nazevOdemykatelnehoProstoru = parametry[0];           
        }

        //Prostor odemykatelnyProstor = aktualni.vratSousedniProstor(nazevOdemykatelnehoProstoru);

        if (parametry.length != 0){ 
            if (aktualni.getNazev().equals(nazevOdemykatelnehoProstoru)) {
                vracenyText =  "Můžeš odemykat pouze sousední prostory!";
            } else {
                odemykatelny = aktualni.vratSousedniProstor(nazevOdemykatelnehoProstoru);   
                if (odemykatelny instanceof Prostor) {
                    if(odemykatelny.getJeZamceny()){
                        System.out.println("Pokud znáte výstupní osmimístný kód, tak ho zadejte, nebo se vraťte do hry zadáním 'zpět'");
                        System.out.print("Zadejte kód nebo 'zpět':");
                        Scanner scanner = new Scanner(System.in);     

                        String zadanyKod = scanner.nextLine();
                        while(!(zadanyKod.equals("zpět"))){
                            if(zadanyKod.length() == 8){
                                if(zadanyKod.equals(celyKod)){
                                    System.out.print("Jsi v bezpečí! \n");
                                    odemykatelny.setJeZamceny(false);                                    
                                    break;

                                }
                                else{  
                                    pocetPokusu--;
                                    System.out.print("Zadaný kód je špatně. Zbývá " +pocetPokusu + " pokusů. \n");
                                    if(pocetPokusu < 1){
                                        System.out.print("Prohráls, Tvé mentální schopnosti jsou na úrovni žáka MŠ. \n");
                                        odemykatelny.setPopis("konec");
                                        break;
                                    }         
                                }
                            }
                            else{   
                                pocetPokusu--;
                                System.out.print("Zadaný kód není osmimístný. Zbývá " +pocetPokusu+ " pokusů. \n");
                                if(pocetPokusu < 1){
                                    System.out.print("Prohráls, Tvé mentální schopnosti jsou na úrovni žáka MŠ. \n");
                                    odemykatelny.setPopis("konec");
                                    break; 
                                }                                

                            }
                            System.out.print("Zadejte kód nebo 'zpět':");
                            zadanyKod = scanner.nextLine();
                        }
                        if(pocetPokusu < 1 || zadanyKod.equals("zpět")){
                            vracenyText = "";
                        } else {
                            vracenyText = "Vrátil jsi se do hry";
                        }
                    }
                    else{
                        vracenyText = "Odemknout můžeš pouze východ. Ostatní prostory jsou odemčené."; 
                    }
                }
                else {
                    vracenyText = "Můžeš odemknout pouze východ!";
                }
            }
        }

        return vracenyText;
    }

    /**
     *  Metoda vrací název příkazu (slovo které používá hráč pro jeho vyvolání)
     *  
     *  @return nazev prikazu
     */
    public String getNazev(){
        return NAZEV;
    }
}
