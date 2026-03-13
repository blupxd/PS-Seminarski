package so.radnik;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.RadnikDBB;
import models.Radnik;

public class SoPromeniRadnik extends ApstraktnaGenerickaOperacija {

    private final RadnikDBB radnikDBB = new RadnikDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Radnik)) {
            throw new Exception("Parametar mora biti instanca klase Radnik.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Radnik r = (Radnik) param;
        radnikDBB.promeniRadnik(r);
        rezultat = r;
    }
}
