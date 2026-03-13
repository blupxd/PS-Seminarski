package so.pravnolice;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.PravnoLiceDBB;

public class SoObrisiPravnoLice extends ApstraktnaGenerickaOperacija {

    private final PravnoLiceDBB pravnoLiceDBB = new PravnoLiceDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Integer)) {
            throw new Exception("Parametar mora biti instanca klase Integer.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        int id = (Integer) param;
        pravnoLiceDBB.obrisiPravnoLice(id);
    }
}
