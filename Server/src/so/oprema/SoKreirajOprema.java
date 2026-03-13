package so.oprema;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.OpremaDBB;
import models.Oprema;

public class SoKreirajOprema extends ApstraktnaGenerickaOperacija {

    private final OpremaDBB opremaDBB = new OpremaDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Oprema)) {
            throw new Exception("Parametar mora biti instanca klase Oprema.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Oprema o = (Oprema) param;
        int id = opremaDBB.kreirajOprema(o);
        o.setIdOprema(id);
        rezultat = o;
    }
}
