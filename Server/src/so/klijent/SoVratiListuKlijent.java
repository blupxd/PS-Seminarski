package so.klijent;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.KlijentDBB;
import models.Klijent;

import java.util.List;

public class SoVratiListuKlijent extends ApstraktnaGenerickaOperacija {

    private final KlijentDBB klijentDBB = new KlijentDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Klijent)) {
            throw new Exception("Parametar mora biti instanca klase Klijent.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Klijent kriterijum = (Klijent) param;
        List lista = klijentDBB.pretraziKlijente(kriterijum);
        if (lista == null || lista.isEmpty()) {
            throw new Exception("Sistem ne može da nađe klijente po zadatim kriterijumima.");
        }
        rezultat = lista;
    }
}
