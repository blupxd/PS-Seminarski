package so.klijent;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.KlijentDBB;
import models.Klijent;

public class SoPretraziKlijent extends ApstraktnaGenerickaOperacija {

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
        Klijent k = klijentDBB.pretraziKlijent(id);
        if (k == null) {
            throw new Exception("Sistem ne može da nađe klijenta.");
        }
        rezultat = k;
    }
}
