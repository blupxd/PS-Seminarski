package so.strucnasprema;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.StrucnaSpremaDBB;
import models.StrucnaSprema;

public class SoPromeniStrucnaSprema extends ApstraktnaGenerickaOperacija {

    private final StrucnaSpremaDBB ssDBB = new StrucnaSpremaDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof StrucnaSprema)) {
            throw new Exception("Parametar mora biti instanca klase StrucnaSprema.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        StrucnaSprema ss = (StrucnaSprema) param;
        ssDBB.promeniStrucnaSprema(ss);
        rezultat = ss;
    }
}
