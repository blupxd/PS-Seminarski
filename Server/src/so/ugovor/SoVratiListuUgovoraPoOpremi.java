package so.ugovor;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.UgovorDBB;

public class SoVratiListuUgovoraPoOpremi extends ApstraktnaGenerickaOperacija {

    private final UgovorDBB ugovorDBB = new UgovorDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Integer)) {
            throw new Exception("Parametar mora biti instanca klase Integer.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        int idOprema = (Integer) param;
        rezultat = ugovorDBB.vratiListuUgovoraPoOpremi(idOprema);
    }
}
