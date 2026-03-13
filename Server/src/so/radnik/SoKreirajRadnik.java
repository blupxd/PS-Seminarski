package so.radnik;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.PrSsDBB;
import dbb.RadnikDBB;
import models.PrSs;
import models.Radnik;

public class SoKreirajRadnik extends ApstraktnaGenerickaOperacija {

    private final RadnikDBB radnikDBB = new RadnikDBB();
    private final PrSsDBB prSsDBB = new PrSsDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Radnik)) {
            throw new Exception("Parametar mora biti instanca klase Radnik.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Radnik r = (Radnik) param;
        int id = radnikDBB.kreirajRadnik(r);
        r.setIdRadnik(id);
        if (r.getPrSsList() != null) {
            for (PrSs prss : r.getPrSsList()) {
                prss.setIdRadnik(id);
                prSsDBB.ubaciPrSs(prss);
            }
        }
        rezultat = r;
    }
}
