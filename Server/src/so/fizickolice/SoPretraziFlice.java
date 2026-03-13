package so.fizickolice;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.FizickoLiceDBB;
import models.FizickoLice;

public class SoPretraziFlice extends ApstraktnaGenerickaOperacija {

    private final FizickoLiceDBB fizickoLiceDBB = new FizickoLiceDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Integer)) {
            throw new Exception("Parametar mora biti instanca klase Integer.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        int id = (Integer) param;
        FizickoLice fl = fizickoLiceDBB.pretraziFizickoLice(id);
        if (fl == null) {
            throw new Exception("Sistem ne može da nađe fizičko lice.");
        }
        rezultat = fl;
    }
}
