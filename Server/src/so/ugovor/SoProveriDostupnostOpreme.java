package so.ugovor;

import controllers.ApstraktnaGenerickaOperacija;
import dbb.UgovorDBB;
import models.StavkaUgovora;

public class SoProveriDostupnostOpreme extends ApstraktnaGenerickaOperacija {

    private final UgovorDBB ugovorDBB = new UgovorDBB();

    @Override
    protected void proveriPreduslove(Object param) throws Exception {
        if (!(param instanceof StavkaUgovora)) {
            throw new Exception("Parametar mora biti instanca klase StavkaUgovora.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param) throws Exception {
        StavkaUgovora s = (StavkaUgovora) param;
        int slobodna = ugovorDBB.vratiSlobodnuKolicinu(
            s.getIdOprema(),
            s.getDatumOd(),
            s.getDatumDo(),
            s.getIdUgovor()
        );
        rezultat = slobodna;
        if (slobodna < s.getKolicina()) {
            throw new Exception("Nema dovoljno slobodnih komada. Slobodno: " + slobodna);
        }
    }
}
