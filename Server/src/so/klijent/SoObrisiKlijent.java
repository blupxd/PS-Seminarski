package so.klijent;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.KlijentDBB;

public class SoObrisiKlijent extends ApstraktnaGenerickaOperacija {

    private final KlijentDBB klijentDBB = new KlijentDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Integer)) {
            throw new Exception("Parametar mora biti instanca klase Integer.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        int id = (Integer) param;
        klijentDBB.obrisiKlijent(id);
    }
}
