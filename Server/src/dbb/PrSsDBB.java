package dbb;

import java.util.List;
import models.PrSs;
import models.StrucnaSprema;

public class PrSsDBB extends GenericRepository<PrSs> {

    public void ubaciPrSs(PrSs prss) throws Exception {
        super.create(prss);
    }

    public void obrisiPrSs(int idRadnik, int idStrucnaSprema) throws Exception {
        PrSs p = new PrSs();
        p.setIdRadnik(idRadnik);
        p.setIdStrucnaSprema(idStrucnaSprema);
        super.delete(p);
    }

    public List<PrSs> vratiPrSsZaRadnika(int idRadnik) throws Exception {
        List<PrSs> lista = super.getBy(new PrSs(), "idRadnik", idRadnik);
        StrucnaSpremaDBB ssDB = new StrucnaSpremaDBB();
        for (PrSs p : lista) {
            StrucnaSprema ss = ssDB.pretraziStrucnaSprema(p.getIdStrucnaSprema());
            p.setStrucnaSprema(ss);
        }
        return lista;
    }
}
