package so.ugovor;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.UgovorDBB;
import models.StavkaUgovora;
import models.Ugovor;

public class SoKreirajUgovor extends ApstraktnaGenerickaOperacija {

    private final UgovorDBB ugovorDBB = new UgovorDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Ugovor)) {
            throw new Exception("Parametar mora biti instanca klase Ugovor.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Ugovor u = (Ugovor) param;
        int id = ugovorDBB.kreirajUgovor(u);
        u.setIdUgovor(id);
        if (u.getStavke() != null) {
            int rb = 1;
            for (StavkaUgovora stavka : u.getStavke()) {
                stavka.setIdUgovor(id);
                stavka.setRb(rb++);
                ugovorDBB.kreirajStavku(stavka);
            }
        }
        rezultat = u;
    }
}
