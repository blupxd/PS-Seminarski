package so.oprema;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.OpremaDBB;
import models.Oprema;

import java.util.List;

public class SoVratiListuOprema extends ApstraktnaGenerickaOperacija {

    private final OpremaDBB opremaDBB = new OpremaDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Oprema)) {
            throw new Exception("Parametar mora biti instanca klase Oprema.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Oprema kriterijum = (Oprema) param;
        List lista = opremaDBB.pretraziOpremaPoCriterijumu(kriterijum);
        if (lista == null || lista.isEmpty()) {
            throw new Exception("Sistem ne može da nađe opremu po zadatim kriterijumima.");
        }
        rezultat = lista;
    }
}
