package so.klijent;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.KlijentDBB;
import models.Klijent;

public class SoPromeniKlijent extends ApstraktnaGenerickaOperacija {

    private final KlijentDBB klijentDBB = new KlijentDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Klijent)) {
            throw new Exception("Parametar mora biti instanca klase Klijent.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Klijent k = (Klijent) param;
        klijentDBB.promeniKlijent(k);
        rezultat = k;
    }
}
