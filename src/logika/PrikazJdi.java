package logika;

/**
 *  TĹ™Ă­da PrikazJdi implementuje pro hru pĹ™Ă­kaz jdi.
 *  Tato tĹ™Ă­da je souÄŤĂˇstĂ­ jednoduchĂ© textovĂ© hry.
 *  
 *@author     Jarmila Pavlickova, LuboĹˇ PavlĂ­ÄŤek
 *@version    pro ĹˇkolnĂ­ rok 2016/2017
 */
class PrikazJdi implements IPrikaz {
    private static final String NAZEV = "jdi";
    private HerniPlan plan;

    /**
     *  Konstruktor tĹ™Ă­dy
     *  
     *  @param plan hernĂ­ plĂˇn, ve kterĂ©m se bude ve hĹ™e "chodit" 
     */    
    public PrikazJdi(HerniPlan plan) {
        this.plan = plan;
    }

    /**
     *  ProvĂˇdĂ­ pĹ™Ă­kaz "jdi". HrĂˇÄŤ vejde do sousednĂ­ho prostoru, kterĂ˝ mĂˇ v nabĂ­dce.
     *  Pokud vejde do jinĂ©ho prostoru, vypĂ­Ĺˇe se chyba. Pokud je zamÄŤenĂ˝, tak takĂ©.
     *
     *@param parametry - jako  parametr obsahuje jmĂ©no prostoru (vĂ˝chodu),
     *                         do kterĂ©ho se mĂˇ jĂ­t.
     *@return zprĂˇva, kterou vypĂ­Ĺˇe hra hrĂˇÄŤi
     */ 
    @Override
    public String proved(String... parametry) {
        if (parametry.length == 0) {
            // pokud chybĂ­ druhĂ© slovo (sousednĂ­ prostor), tak ....
            return "Kam mam jit? Musis zadat jmeno vychodu.";
        }

        String smer = null;
        if (parametry.length == 1) {
            smer = parametry[0];           
        }
        if (parametry.length == 2) {
            smer = parametry[0] + " " + parametry[1];           
        }
        // zkouĹˇĂ­me pĹ™ejĂ­t do sousednĂ­ho prostoru
        Prostor sousedniProstor = plan.getAktualniProstor().vratSousedniProstor(smer);

        if (sousedniProstor == null) {
            return "Tam se odsud jit neda!";
        }
        else {
            if (sousedniProstor.getNazev().equals("vychod")) {
                return "Pro vychod musis vychod odemknout.";

            } else {
                plan.setAktualniProstor(sousedniProstor);
                return sousedniProstor.dlouhyPopis();
            }
        }
    }

    /**
     *  Metoda vracĂ­ nĂˇzev pĹ™Ă­kazu (slovo kterĂ© pouĹľĂ­vĂˇ hrĂˇÄŤ pro jeho vyvolĂˇnĂ­)
     *  
     *  @ return nazev prikazu
     */
    @Override
    public String getNazev() {
        return NAZEV;
    }

}
