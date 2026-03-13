package so.pravnolice;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.PravnoLiceDBB;
import models.PravnoLice;

import java.util.List;

public class SoVratiListuPravnoLice extends ApstraktnaGenerickaOperacija {

    private final PravnoLiceDBB pravnoLiceDBB = new PravnoLiceDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof PravnoLice)) {
            throw new Exception("Parametar mora biti instanca klase PravnoLice.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        PravnoLice kriterijum = (PravnoLice) param;
        List lista = pravnoLiceDBB.pretraziPravnoLicePoCriterijumu(kriterijum);
        if (lista == null || lista.isEmpty()) {
            throw new Exception("Sistem ne može da nađe pravna lica po zadatim kriterijumima.");
        }
        rezultat = lista;
    }
}
