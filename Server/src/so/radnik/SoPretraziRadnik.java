package so.radnik;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.PrSsDBB;
import dbb.RadnikDBB;
import models.Radnik;

public class SoPretraziRadnik extends ApstraktnaGenerickaOperacija {

    private final RadnikDBB radnikDBB = new RadnikDBB();
    private final PrSsDBB prSsDBB = new PrSsDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Integer)) {
            throw new Exception("Parametar mora biti instanca klase Integer.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        int id = (Integer) param;
        Radnik r = radnikDBB.pretraziRadnik(id);
        if (r == null) {
            throw new Exception("Sistem ne može da nađe radnika.");
        }
        r.setPrSsList(prSsDBB.vratiPrSsZaRadnika(id));
        rezultat = r;
    }
}
