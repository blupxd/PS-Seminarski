package so.ugovor;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.UgovorDBB;
import models.Ugovor;

public class SoPretraziUgovor extends ApstraktnaGenerickaOperacija {

    private final UgovorDBB ugovorDBB = new UgovorDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Integer)) {
            throw new Exception("Parametar mora biti instanca klase Integer.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        int id = (Integer) param;
        Ugovor u = ugovorDBB.pretraziUgovor(id);
        if (u == null) {
            throw new Exception("Sistem ne može da nađe ugovor.");
        }
        rezultat = u;
    }
}
