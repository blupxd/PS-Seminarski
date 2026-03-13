package so.auth;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.RadnikDBB;
import models.Radnik;

public class SoPrijaviRadnik extends ApstraktnaGenerickaOperacija {

    private final RadnikDBB radnikDBB = new RadnikDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof Radnik)) {
            throw new Exception("Parametar mora biti instanca klase Radnik.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        Radnik r = (Radnik) param;
        Radnik found = radnikDBB.prijaviRadnik(r.getKorisnickoIme(), r.getSifra());
        if (found == null) {
            throw new Exception("Korisničko ime i šifra nisu ispravni.");
        }
        rezultat = found;
    }
}
