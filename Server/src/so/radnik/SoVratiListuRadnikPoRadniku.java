package so.radnik;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.RadnikDBB;
import models.Radnik;

import java.util.List;

public class SoVratiListuRadnikPoRadniku extends ApstraktnaGenerickaOperacija {

    private final RadnikDBB radnikDBB = new RadnikDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Radnik)) {
            throw new Exception("Parametar mora biti instanca klase Radnik.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Radnik kriterijum = (Radnik) param;
        List lista = radnikDBB.pretraziRadnikePoKriterijumu(kriterijum);
        if (lista == null || lista.isEmpty()) {
            throw new Exception("Sistem ne može da nađe radnike po zadatim kriterijumima.");
        }
        rezultat = lista;
    }
}
