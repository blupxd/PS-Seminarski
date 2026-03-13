package so.oprema;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.OpremaDBB;
import models.Oprema;

public class SoPretraziOprema extends ApstraktnaGenerickaOperacija {

    private final OpremaDBB opremaDBB = new OpremaDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Integer)) {
            throw new Exception("Parametar mora biti instanca klase Integer.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        int id = (Integer) param;
        Oprema o = opremaDBB.pretraziOprema(id);
        if (o == null) {
            throw new Exception("Sistem ne može da nađe opremu.");
        }
        rezultat = o;
    }
}
