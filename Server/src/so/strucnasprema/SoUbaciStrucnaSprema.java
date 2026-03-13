package so.strucnasprema;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.PrSsDBB;
import models.PrSs;

public class SoUbaciStrucnaSprema extends ApstraktnaGenerickaOperacija {

    private final PrSsDBB prSsDBB = new PrSsDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof PrSs)) {
            throw new Exception("Parametar mora biti instanca klase PrSs.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        PrSs prss = (PrSs) param;
        prSsDBB.ubaciPrSs(prss);
    }
}
