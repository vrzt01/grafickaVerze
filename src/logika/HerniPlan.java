package logika;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.PanelVychodu;
import util.ObserverZmenyProstoru;
import util.SubjektZmenyProstoru;

/**
 *  Class HerniPlan - třída představující mapu a stav adventury.
 * 
 *  Tato třída inicializuje prvky ze kterých se hra skládá:
 *  vytváří všechny prostory,
 *  propojuje je vzájemně pomocí východů 
 *  a pamatuje si aktuální prostor, ve kterém se hráč právě nachází.
 *
 *@author     Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova, Tomáš Vrzák
 *@version    pro školní rok 2016/2017
 */
public class HerniPlan implements SubjektZmenyProstoru {

    private Prostor aktualniProstor;
    private Prostor vychod;
    private Postava ghul;
    private List<ObserverZmenyProstoru> seznamPozorovatelu;
    /**
     *  Konstruktor který vytváří jednotlivé prostory a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví halu.
     */
    public HerniPlan(int kod1, int kod2, int kod3, int kod4) {
        zalozProstoryHry(kod1,kod2,kod3,kod4);
        seznamPozorovatelu = new ArrayList<>();
    }

    /**
     *  Vytváří jednotlivé prostory, věci a postavu a propojuje je pomocí východů.
     *  Jako výchozí aktuální prostor nastaví chodbu.
     *  Schovává věci do věcí, které se nechají prohledat.
     *  
     *  @param jako parametr musime zadat casti kodu, pro názvy papírků ve hře
     */
    private void zalozProstoryHry(int kod1, int kod2, int kod3, int kod4) {
        //String nazev, String popis, boolean jeZamceny
        // vytvářejí se jednotlivé prostory
        Prostor chodba = new Prostor("chodba","Nacházíte se na chodbě domu.", false, 350, 125);     
        Prostor koupelna = new Prostor("koupelna", "Nacházíte se v koupelně", false,250, 50);
        Prostor loznice = new Prostor("loznice","Nacházíte se v ložnici", false,410, 50);
        Prostor obyvak = new Prostor("obyvak","Nacházíte se v obývaku", false,310, 220);
        Prostor sklep = new Prostor("sklep","Nachazíte se ve sklepě.", false,330, 315);
        Prostor puda = new Prostor("puda","Nacházíte se na půde.", false,580, 315);
        Prostor schodisteSklep = new Prostor("schodisteSklep","Tímto schodištem se dostanete do sklepa.", false,440, 210);
        Prostor schodistePuda = new Prostor("schodistePuda","Tímto schodištem se dostanete na půdu.", false,490, 210);
        vychod = new Prostor("vychod","x", true,10, 50);
        // přiřazují se průchody mezi prostory (sousedící prostory)
        chodba.setVychod(koupelna);
        chodba.setVychod(loznice);
        chodba.setVychod(obyvak);
        chodba.setVychod(schodisteSklep);
        chodba.setVychod(schodistePuda);
        chodba.setVychod(vychod);

        schodisteSklep.setVychod(sklep);
        schodisteSklep.setVychod(chodba);

        schodistePuda.setVychod(puda);
        schodistePuda.setVychod(chodba);

        sklep.setVychod(schodisteSklep);
        puda.setVychod(schodistePuda);

        koupelna.setVychod(chodba);
        loznice.setVychod(chodba);
        obyvak.setVychod(chodba);

        aktualniProstor = chodba; // hra začíná v domečku   
        //Vec(String nazev, boolean prenositelnost, boolean jeProhledatelna)
        Vec stul_ch = new Vec("stul", false, false);
        Vec obraz_ch = new Vec("obraz", false, false);
        Vec vesak = new Vec("vesak", false, false);
        Vec botnik = new Vec("botnik", false, false);
        chodba.vlozVec(stul_ch);
        chodba.vlozVec(obraz_ch);
        chodba.vlozVec(vesak);
        chodba.vlozVec(botnik);

        Vec umyvadlo = new Vec("umyvadlo", false, true);
        Vec papirek2 = new Vec("kod"+kod2, true, false);//59
        umyvadlo.setSchovaneVeci("kod"+kod2, papirek2);        

        Vec skrinka_k = new Vec("skrinka", false, true);
        Vec kartacek = new Vec("kartacek", true, false);
        Vec mydlo = new Vec("mydlo", true, false);
        skrinka_k.setSchovaneVeci("mydlo", mydlo);
        skrinka_k.setSchovaneVeci("kartacek", kartacek);

        Vec zachod = new Vec("zachod", false, false);
        koupelna.vlozVec(umyvadlo);
        koupelna.vlozVec(skrinka_k);
        koupelna.vlozVec(zachod);

        Vec postel = new Vec("postel", false, false);
        Vec skrin_l = new Vec("skrin", false, true);
        Vec sako = new Vec("sako", true, false);
        Vec ponozky = new Vec("ponozky", true, false);
        skrin_l.setSchovaneVeci("sako", sako);
        skrin_l.setSchovaneVeci("ponozky", ponozky);

        Vec zrcadlo = new Vec("zrcadlo", false, true);
        Vec papirek3 = new Vec("kod"+kod3, true, false);
        zrcadlo.setSchovaneVeci("kod"+kod3, papirek3);
        loznice.vlozVec(postel);
        loznice.vlozVec(skrin_l);
        loznice.vlozVec(zrcadlo);

        Vec sejf = new Vec("sejf", false, true);
        Vec papirek4 = new Vec("kod"+kod4, true, false);
        Vec hotovost = new Vec("hotovost", true, false);
        sejf.setSchovaneVeci("kod"+kod4, papirek4);
        sejf.setSchovaneVeci("hotovost", hotovost);

        Vec stolek = new Vec("stolek", false, true);
        Vec blok = new Vec("blok", true, false);
        Vec propiska = new Vec("propiska", true, false);
        stolek.setSchovaneVeci("blok", blok);
        stolek.setSchovaneVeci("propiska", propiska);

        Vec gauc = new Vec("gauc", false, false);
        obyvak.vlozVec(sejf);
        obyvak.vlozVec(stolek);
        obyvak.vlozVec(gauc);

        Vec lednice = new Vec("lednice", false, true);
        Vec sunka = new Vec("sunka", true, false);
        Vec mleko = new Vec("mleko", true, false);
        obyvak.vlozVec(lednice);
        lednice.setSchovaneVeci("sunka", sunka);
        lednice.setSchovaneVeci("mleko", mleko);

        Vec bojler = new Vec("bojler", false, false);
        Vec pradlo = new Vec("pradlo", false, true);
        //Vec napoveda = new Vec("papírek na půdě je poslední částí kódu", false, false);
        Vec papirekNapoveda = new Vec("help", true, true);
        sklep.vlozVec(bojler);
        sklep.vlozVec(pradlo);
        sklep.vlozVec(papirekNapoveda);

        Vec baterka = new Vec("baterka", true, false);
        Vec papirek1 = new Vec("kod"+kod1, true, false);
        Vec kosti = new Vec("kosti", true, false);
        puda.vlozVec(baterka);
        puda.vlozVec(papirek1);
        puda.vlozVec(kosti);

        ghul = new Postava("ghul", "Vidíte hladového a agresivního ghula, pozor", true, false);
        puda.vlozPostavu(ghul);

    }

    /**
     *  Metoda vrací odkaz na aktuální prostor, ve ktetém se hráč právě nachází.
     *
     *@return     aktuální prostor
     */

    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    /**
     *  Metoda nastaví aktuální prostor, používá se nejčastěji při přechodu mezi prostory
     *
     *@param  prostor nový aktuální prostor
     */
    public void setAktualniProstor(Prostor prostor) {
        aktualniProstor = prostor;
        upozorniPozorovatele();
    }

    /**
     *  Metoda testuje zda nastavala výhra.
     *  Vyhra nastane, kdyz se odemkne vychod.
     *
     *@return     nastal/nenastal konec hry
     */
    public boolean vyhra(){        
        if(vychod.getJeZamceny() == false){
            return true;
        } else {
            return false;       
        }
    }

    /**
     *  Metoda testuje zda nastavala prohra.
     *  Prohra nastane, kdyz parametr konec u postavy Ghul se nastavi na true.
     *
     *@return     nastal/nenastal konec hry
     */
    public boolean prohra(){
        if(ghul.getKonec() || vychod.getPopis().equals("konec")){        
            return true;
        }
        else{
            return false;
        }
    }

   public void zaregistrujPozorovatele(ObserverZmenyProstoru pozorovatel)
      {
        seznamPozorovatelu.add(pozorovatel);
      }

    public void odregistrujPozorovatele(ObserverZmenyProstoru pozorovatel)
      {
        seznamPozorovatelu.remove(pozorovatel);
      }

    public void upozorniPozorovatele()
      {
        for (ObserverZmenyProstoru pozorovatel : seznamPozorovatelu) {
            pozorovatel.aktualizuj();
        }
      }

    
   
    
}
