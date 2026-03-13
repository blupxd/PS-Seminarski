package so.klijent;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.KlijentDBB;

public class SoVratiListuSviKlijent extends ApstraktnaGenerickaOperacija {

    private final KlijentDBB klijentDBB = new KlijentDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        rezultat = klijentDBB.vratiSveKlijente();
    }
}
