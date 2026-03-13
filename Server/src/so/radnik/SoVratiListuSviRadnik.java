package so.radnik;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.RadnikDBB;

public class SoVratiListuSviRadnik extends ApstraktnaGenerickaOperacija {

    private final RadnikDBB radnikDBB = new RadnikDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        rezultat = radnikDBB.vratiSveRadnike();
    }
}
