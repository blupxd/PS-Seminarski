package so.strucnasprema;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.StrucnaSpremaDBB;
import models.StrucnaSprema;

public class SoPretraziStrucnaSprema extends ApstraktnaGenerickaOperacija {

    private final StrucnaSpremaDBB ssDBB = new StrucnaSpremaDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Integer)) {
            throw new Exception("Parametar mora biti instanca klase Integer.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        int id = (Integer) param;
        StrucnaSprema ss = ssDBB.pretraziStrucnaSprema(id);
        if (ss == null) {
            throw new Exception("Sistem ne može da nađe stručnu spremu.");
        }
        rezultat = ss;
    }
}
