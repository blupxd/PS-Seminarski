package so.pravnolice;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.PravnoLiceDBB;
import models.PravnoLice;

public class SoKreirajPravnoLice extends ApstraktnaGenerickaOperacija {

    private final PravnoLiceDBB pravnoLiceDBB = new PravnoLiceDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof PravnoLice)) {
            throw new Exception("Parametar mora biti instanca klase PravnoLice.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        PravnoLice pl = (PravnoLice) param;
        pravnoLiceDBB.kreirajPravnoLice(pl);
        rezultat = pl;
    }
}
