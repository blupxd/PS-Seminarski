package so.ugovor;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.UgovorDBB;
import models.Ugovor;

public class SoVratiListuUgovoraPoCriterijumu extends ApstraktnaGenerickaOperacija {

    private final UgovorDBB ugovorDBB = new UgovorDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Ugovor)) {
            throw new Exception("Parametar mora biti instanca klase Ugovor.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Ugovor kriterijum = (Ugovor) param;
        rezultat = ugovorDBB.vratiListuUgovoraPoCriterijumu(kriterijum);
    }
}
