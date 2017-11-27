   package logika;
import java.util.*;
/**
 *  Třída Hra - třída představující logiku adventury.
 * 
 *  Toto je hlavní třída  logiky aplikace.  Tato třída vytváří instanci třídy HerniPlan, která inicializuje mistnosti hry
 *  a vytváří seznam platných příkazů a instance tříd provádějící jednotlivé příkazy.
 *  Vypisuje uvítací a ukončovací text hry.
 *  Také vyhodnocuje jednotlivé příkazy zadané uživatelem.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Tomáš Vrzák
 *@version    pro školní rok 2016/2017
 */

public class Hra implements IHra {
    private SeznamPrikazu platnePrikazy;    // obsahuje seznam přípustných příkazů
    private HerniPlan herniPlan;
    private boolean konecHry = false;
    private Batoh batoh;
    private int kod1;
    private int kod2;
    private int kod3;
    private int kod4;
    private String celyKod;
    private int pocetPokusu;
    /**
     *  Vytváří hru a inicializuje místnosti (prostřednictvím třídy HerniPlan) a seznam platných příkazů.
     */
    public Hra() {
        pocetPokusu = 6;
        kod1 = 32;
        kod2 = 59;
        kod3 = 81;
        kod4 = 47;
        celyKod = Integer.toString(kod1)+Integer.toString(kod2)+Integer.toString(kod3)+Integer.toString(kod4);
        herniPlan = new HerniPlan(kod1,kod2,kod3,kod4);
        
        platnePrikazy = new SeznamPrikazu();
        batoh = new Batoh();
        platnePrikazy.vlozPrikaz(new PrikazNapoveda(platnePrikazy));
        platnePrikazy.vlozPrikaz(new PrikazJdi(herniPlan));
        platnePrikazy.vlozPrikaz(new PrikazKonec(this));
        platnePrikazy.vlozPrikaz(new PrikazOdhod(herniPlan, batoh));
        platnePrikazy.vlozPrikaz(new PrikazProhledat(herniPlan, batoh));
        platnePrikazy.vlozPrikaz(new PrikazSeber(herniPlan, batoh));
        platnePrikazy.vlozPrikaz(new PrikazOdemkni(herniPlan, celyKod, pocetPokusu));
        platnePrikazy.vlozPrikaz(new PrikazBatoh(batoh));

    }

    /**
     *  Vrátí úvodní zprávu pro hráče.
     */
    public static final int random(int start, int end){

        Random random = new Random();
        //int výsledek = r.nextInt(end-start) + start;
        return random.nextInt(end-start) + start;
    }

    public String vratUvitani() {

        return "Vítejte!\n" +
        "Hrdina se probouzí v předsíni domu, kam ho jako žert poslali jeho přátelé. \n"+
        "Před sebou vidí dveře ven do bezpečí. Na zdi u dveří je ovšem elektronický zámek s nápisem LOCKED \n"+
        "Pro jeho otevření musí najít 4 papírky s číselným kódem. \n"+
        "V domě se nachází 3 různá podlaží – sklep, obytné přízemí a půda.\n"+
        "Napište 'nápověda', pokud si nevíte rady, jak hrát dál.\n" +
        "\n" +
        herniPlan.getAktualniProstor().dlouhyPopis();
        
    }

    /**
     *  Vrátí závěrečnou zprávu pro hráče.
     */
    public String vratEpilog() {
        return "Dík, že jste si zahráli.  Ahoj.";
    }

    /** 
     * Vrací true, pokud hra skončila.
     */
    public boolean konecHry() {
        return konecHry;
    }

    /**
     *  Metoda zpracuje řetězec uvedený jako parametr, rozdělí ho na slovo příkazu a další parametry.
     *  Pak otestuje zda příkaz je klíčovým slovem  např. jdi.
     *  Pokud ano spustí samotné provádění příkazu.
     *
     *@param           radek  text, který zadal uživatel jako příkaz do hry.
     *@return          vrací se řetězec, který se má vypsat na obrazovku
     */
    public String zpracujPrikaz(String radek) {
        String [] slova = radek.split("[ \t]+");
        String slovoPrikazu = slova[0];
        String []parametry = new String[slova.length-1];
        for(int i=0 ;i<parametry.length;i++){
            parametry[i]= slova[i+1];   
        }
        String textKVypsani=" .... ";
        if (platnePrikazy.jePlatnyPrikaz(slovoPrikazu)) {
            IPrikaz prikaz = platnePrikazy.vratPrikaz(slovoPrikazu);
            textKVypsani = prikaz.proved(parametry);
            if(herniPlan.vyhra()){
                konecHry = true;
            }
            if (herniPlan.prohra()) {
                konecHry = true;
            }
        }
        else {
            textKVypsani="Nevím co tím myslíš? Tento příkaz neznám. ";
        }
        return textKVypsani;
    }

    /**
     *  Nastaví, že je konec hry, metodu využívá třída PrikazKonec,
     *  mohou ji použít i další implementace rozhraní Prikaz.
     *  
     *  @param  konecHry  hodnota false= konec hry, true = hra pokračuje
     */
    void setKonecHry(boolean konecHry) {
        this.konecHry = konecHry;
    }

    /**
     *  Metoda vrátí odkaz na herní plán, je využita hlavně v testech,
     *  kde se jejím prostřednictvím získává aktualní místnost hry.
     *  
     *  @return     odkaz na herní plán
     */
    public HerniPlan getHerniPlan(){
        return herniPlan;
    }
    public Batoh getBatoh(){
        return batoh;
     }
     
     public String getCelyKod(){
        return this.celyKod;
     }
     public int getKod1(){
        return this.kod1;
     }
     public int getKod2(){
        return this.kod2;
     }
     public int getKod3(){
        return this.kod3;
     }
     public int getKod4(){
        return this.kod4;
     }
     
}

