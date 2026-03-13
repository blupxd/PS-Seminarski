package so.strucnasprema;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.StrucnaSpremaDBB;
import models.StrucnaSprema;

import java.util.List;

public class SoVratiListuStrucnaSprema extends ApstraktnaGenerickaOperacija {

    private final StrucnaSpremaDBB ssDBB = new StrucnaSpremaDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof StrucnaSprema)) {
            throw new Exception("Parametar mora biti instanca klase StrucnaSprema.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        StrucnaSprema kriterijum = (StrucnaSprema) param;
        List lista = ssDBB.pretraziStrucneSprema(kriterijum);
        if (lista == null || lista.isEmpty()) {
            throw new Exception("Sistem ne može da nađe stručne spreme po zadatim kriterijumima.");
        }
        rezultat = lista;
    }
}
