package so.radnik;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.RadnikDBB;

public class SoVratiListuRadnikPoStrucnojSpremi extends ApstraktnaGenerickaOperacija {

    private final RadnikDBB radnikDBB = new RadnikDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Integer)) {
            throw new Exception("Parametar mora biti instanca klase Integer.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        int idSS = (Integer) param;
        rezultat = radnikDBB.pretraziRadnikePoStrucnojSpremi(idSS);
    }
}
