package logika;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

/**
 * Trida Prostor - popisuje jednotlivé prostory (místnosti) hry
 *
 * Tato třída je součástí jednoduché textové hry.
 *
 * "Prostor" reprezentuje jedno místo (místnost, prostor, ..) ve scénáři hry.
 * Prostor může mít sousední prostory připojené přes východy. Pro každý východ
 * si prostor ukládá odkaz na sousedící prostor.
 *
 * @author Michael Kolling, Lubos Pavlicek, Jarmila Pavlickova
 * @version pro školní rok 2016/2017
 */
public class Prostor {

    private String nazev;
    private String popis;
    private boolean jeZamceny;
    private Set<Prostor> vychody;   // obsahuje sousední místnosti
    private Map<String, Vec> veci;
    private Map<String, Postava> postavy;
    private double left;
    private double top;
    /**
     * Vytvoření prostoru se zadaným popisem, např. "kuchyň", "chodba"...
     *
     * @param nazev nazev prostoru, jednoznačný identifikátor, jedno slovo nebo
     * víceslovný název bez mezer.
     * @param popis Popis prostoru.
     */
    public Prostor(String nazev, String popis, boolean jeZamceny, double left, double top) {
        this.nazev = nazev;
        this.popis = popis;
        this.jeZamceny = jeZamceny;
        this.left = left;
        this.top = top;
        vychody = new HashSet<>();
        veci = new HashMap<>();
        postavy = new HashMap<>();
    }

    /**
     * Definuje východ z prostoru (sousední/vedlejsi prostor). Vzhledem k tomu,
     * že je použit Set pro uložení východů, může být sousední prostor uveden
     * pouze jednou (tj. nelze mít dvoje dveře do stejné sousední místnosti).
     * Druhé zadání stejného prostoru tiše přepíše předchozí zadání (neobjeví se
     * žádné chybové hlášení). Lze zadat též cestu ze do sebe sama.
     *
     * @param vedlejsi prostor, který sousedi s aktualnim prostorem.
     *
     */
    public void setVychod(Prostor vedlejsi) {
        vychody.add(vedlejsi);
    }

    /**
     * Metoda equals pro porovnání dvou prostorů. Překrývá se metoda equals ze
     * třídy Object. Dva prostory jsou shodné, pokud mají stejný název. Tato
     * metoda je důležitá z hlediska správného fungování seznamu východů (Set).
     *
     * Bližší popis metody equals je u třídy Object.
     *
     * @param o object, který se má porovnávat s aktuálním
     * @return hodnotu true, pokud má zadaný prostor stejný název, jinak false
     */  
    @Override
    public boolean equals(Object o) {
        // porovnáváme zda se nejedná o dva odkazy na stejnou instanci
        if (this == o) {
            return true;
        }
        // porovnáváme jakého typu je parametr 
        if (!(o instanceof Prostor)) {
            return false;    // pokud parametr není typu Prostor, vrátíme false
        }
        // přetypujeme parametr na typ Prostor 
        Prostor druhy = (Prostor) o;

        //metoda equals třídy java.util.Objects porovná hodnoty obou názvů. 
        //Vrátí true pro stejné názvy a i v případě, že jsou oba názvy null,
        //jinak vrátí false.

        return (java.util.Objects.equals(this.nazev, druhy.nazev));       
    }

    /**
     * metoda hashCode vraci ciselny identifikator instance, ktery se pouziva
     * pro optimalizaci ukladani v dynamickych datovych strukturach. Pri
     * prekryti metody equals je potreba prekryt i metodu hashCode. Podrobny
     * popis pravidel pro vytvareni metody hashCode je u metody hashCode ve
     * tride Object
     */
    @Override
    public int hashCode() {
        int vysledek = 3;
        int hashNazvu = java.util.Objects.hashCode(this.nazev);
        vysledek = 37 * vysledek + hashNazvu;
        return vysledek;
    }

    /**
     * Vrací název prostoru (byl zadán při vytváření prostoru jako parametr
     * konstruktoru)
     *
     * @return název prostoru
     */
    public String getNazev() {
        return nazev;       
    }

    public boolean getJeZamceny() {
        return jeZamceny;
    }

    /**
     * Vrací klíč, kterým se odemyká prostor
     *
     * @return věc - klíč k odemčení prostoru
     */

    /**
     * Nastavuje zamčenost prostoru
     *
     * @param zamčenost prostoru
     */
    public void setJeZamceny(boolean jeZamceny) {
        this.jeZamceny = jeZamceny;
    }

    /**
     * Vrací "dlouhý" popis prostoru, který může vypadat následovně: Jsi v
     * mistnosti/prostoru vstupni hala budovy VSE na Jiznim meste. vychody:
     * chodba bufet ucebna
     *
     * @return Dlouhý popis prostoru
     */
    public String dlouhyPopis() {
        return "Jsi v místnosti/prostoru " + nazev + "\n"
        + popisVychodu()+ "\n"
        + popisVeci()+ "\n"
        + popisPostav();
    }

    /**
     * Vrací textový řetězec, který popisuje sousední východy, například:
     * "vychody: hala ".
     *
     * @return Popis východů - názvů sousedních prostorů
     */
    private String popisVychodu() {
        String vracenyText = "Východy: | ";
        for (Prostor sousedni : vychody) {
            vracenyText += sousedni.getNazev() + " | ";
        }
        return vracenyText;
    }

    private String popisVeci(){
        String vracenyText;
        if (veci.isEmpty()) {
            vracenyText = "V místnosti |" + nazev + "| se nenachází žádné věci.";
        } else {        
            vracenyText = "Jsou tu tyto věci: | ";
            for (String nazev : veci.keySet()) {
                vracenyText += nazev + " | ";            
            }           
        }
        return vracenyText;
    }

    /**
     * Vrací textový řetězec, který popisuje postavy v prostoru.
     *
     * @return Popis postav v aktuálním prostoru
     */
    private String popisPostav(){
        String vracenyText;
        if (postavy.isEmpty()) {
            vracenyText = "V místnosti |" + nazev + "| se nenachází žádné postavy.";
        } else {        
            vracenyText = "Jsou tu tyto postavy: | ";
            for (String nazev : postavy.keySet()) {
                vracenyText += nazev + " | ";            
            }           
        }
        return vracenyText;
    }

    /**
     * Vrací prostor, který sousedí s aktuálním prostorem a jehož název je zadán
     * jako parametr. Pokud prostor s udaným jménem nesousedí s aktuálním
     * prostorem, vrací se hodnota null.
     *
     * @param nazevSouseda Jméno sousedního prostoru (východu)
     * @return Prostor, který se nachází za příslušným východem, nebo hodnota
     * null, pokud prostor zadaného jména není sousedem.
     */
    public Prostor vratSousedniProstor(String nazevSouseda) {
        List<Prostor>hledaneProstory = 
            vychody.stream()
            .filter(sousedni -> sousedni.getNazev().equals(nazevSouseda))
        .collect(Collectors.toList());
        if(hledaneProstory.isEmpty()){
            return null;
        }
        else {
            return hledaneProstory.get(0);
        }
    }

    /**
     * Vrací kolekci obsahující prostory, se kterými tento prostor sousedí.
     * Takto získaný seznam sousedních prostor nelze upravovat (přidávat,
     * odebírat východy) protože z hlediska správného návrhu je to plně
     * záležitostí třídy Prostor.
     *
     * @return Nemodifikovatelná kolekce prostorů (východů), se kterými tento
     * prostor sousedí.
     */
    public Collection<Prostor> getVychody() {
        return Collections.unmodifiableCollection(vychody);
    }

    
    /**
     * odebere věc z prostoru
     *
     * @param odebíraná věc
     */
    public Vec odeberVec(String nazevOdebiraneVeci){
        return veci.remove(nazevOdebiraneVeci);
    }

    public void vlozVec(Vec vkladanaVec){
        veci.put(vkladanaVec.getNazev(),vkladanaVec);
    }

   

    public void vlozPostavu(Postava vkladanaPostava){
        postavy.put(vkladanaPostava.getNazev(),vkladanaPostava);
    }

    /**
     * vrátí postavu prostoru
     *
     * @param název vrácené postavy
     * @return hledaná postava
     */
    public Postava ziskejPostavu(String nazevPostavy){   
        Postava hledanaPostava = null;
        for (String nazev : postavy.keySet()) {
            hledanaPostava = postavy.get(nazevPostavy);         
        }          
        return hledanaPostava;
    }

    public Vec ziskejVec(String nazevVeci){   
        Vec hledanaVec = null;
        for (String nazev : veci.keySet()) {
            hledanaVec = veci.get(nazevVeci);         
        }          
        return hledanaVec;
    }

    /**
     * odebere postavu z prostoru
     *
     * @param odebíraná postava
     */
    public Postava odeberPostavu(String nazevOdebiranePostavy){
        return postavy.remove(nazevOdebiranePostavy);
    }
    public void setPopis(String popis) {
        this.popis = popis;
    }
    public String getPopis() {
        return this.popis;
    }

     public double getLeft() {
        return left;
    }

    public double getTop() {
        return top;
    }

    public String seznamVychodu() {
        String vracenyText = "vychody:";
        for (Prostor sousedni : vychody) {
             vracenyText += " " + sousedni.getNazev();
        }
        return vracenyText;
    }
    
    public Set<Vec> seznamVeciVProstoru () {        
        Set<Vec> veciVPRostoru = new HashSet<>();
        for (String nazev : veci.keySet()) {                
            Vec v = veci.get(nazev);
            veciVPRostoru.add(v);
        }
        return veciVPRostoru;
    }
}
