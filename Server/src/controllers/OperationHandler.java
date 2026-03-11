package controllers;

import dbb.*;
import models.*;
import operations.Operation;
import operations.OperationType;
import java.util.List;

public class OperationHandler extends ApstraktnaGenerickaOperacija {

    private final RadnikDBB radnikDBB = new RadnikDBB();
    private final KlijentDBB klijentDBB = new KlijentDBB();
    private final PravnoLiceDBB pravnoLiceDBB = new PravnoLiceDBB();
    private final FizickoLiceDBB fizickoLiceDBB = new FizickoLiceDBB();
    private final OpremaDBB opremaDBB = new OpremaDBB();
    private final StrucnaSpremaDBB ssDBB = new StrucnaSpremaDBB();
    private final UgovorDBB ugovorDBB = new UgovorDBB();
    private final PrSsDBB prSsDBB = new PrSsDBB();

    @Override
    public Operation execute(Operation op) {
        try {
            switch (op.getType()) {

                // ovde je auth
                case PRIJAVI_RADNIK: {
                    Radnik r = (Radnik) op.getRequest();
                    Radnik found = radnikDBB.prijaviRadnik(r.getKorisnickoIme(), r.getSifra());
                    if (found != null) {
                        op.setResponse(found);
                        op.setSuccess(true);
                    } else {
                        op.setSuccess(false);
                        op.setErrorMessage("Korisničko ime i šifra nisu ispravni.");
                    }
                    break;
                }

                // ugovor funkcije
                case PROVERI_DOSTUPNOST_OPREME: {
                    models.StavkaUgovora s = (models.StavkaUgovora) op.getRequest();
                    int slobodna = ugovorDBB.vratiSlobodnuKolicinu(
                        s.getIdOprema(),
                        s.getDatumOd(),
                        s.getDatumDo(),
                        s.getIdUgovor()
                    );
                    op.setResponse(slobodna);
                    boolean dovoljno = slobodna >= s.getKolicina();
                    op.setSuccess(dovoljno);
                    if (!dovoljno) op.setErrorMessage("Nema dovoljno slobodnih komada. Slobodno: " + slobodna);
                    break;
                }
                case KREIRAJ_UGOVOR: {
                    Ugovor u = (Ugovor) op.getRequest();
                    int id = ugovorDBB.kreirajUgovor(u);
                    u.setIdUgovor(id);
                    if (u.getStavke() != null) {
                        int rb = 1;
                        for (StavkaUgovora s : u.getStavke()) {
                            s.setIdUgovor(id);
                            s.setRb(rb++);
                            ugovorDBB.kreirajStavku(s);
                        }
                    }
                    op.setResponse(u);
                    op.setSuccess(true);
                    break;
                }
                case PROMENI_UGOVOR: {
                    Ugovor u = (Ugovor) op.getRequest();
                    ugovorDBB.promeniUgovor(u);
                    ugovorDBB.obrisiStavkeZaUgovor(u.getIdUgovor());
                    if (u.getStavke() != null) {
                        int rb = 1;
                        for (StavkaUgovora s : u.getStavke()) {
                            s.setIdUgovor(u.getIdUgovor());
                            s.setRb(rb++);
                            ugovorDBB.kreirajStavku(s);
                        }
                    }
                    op.setResponse(u);
                    op.setSuccess(true);
                    break;
                }
                case PRETRAZI_UGOVOR: {
                    int id = (Integer) op.getRequest();
                    Ugovor u = ugovorDBB.pretraziUgovor(id);
                    op.setResponse(u);
                    op.setSuccess(u != null);
                    if (u == null) {
                        op.setErrorMessage("Sistem ne može da nađe ugovor.");
                    }
                    break;
                }
                case VRATI_LISTU_UGOVOR_PO_UGOVORU:
                case VRATI_LISTU_UGOVOR_PO_RADNIKU:
                case VRATI_LISTU_UGOVOR_PO_KLIJENTU: {
                    Ugovor kriterijum = (Ugovor) op.getRequest();
                    List<Ugovor> lista = ugovorDBB.vratiListuUgovoraPoCriterijumu(kriterijum);
                    op.setResponse(lista);
                    op.setSuccess(true);
                    break;
                }
                case VRATI_LISTU_UGOVOR_PO_OPREMI: {
                    int idOprema = (Integer) op.getRequest();
                    List<Ugovor> lista = ugovorDBB.vratiListuUgovoraPoOpremi(idOprema);
                    op.setResponse(lista);
                    op.setSuccess(true);
                    break;
                }

                // klijent funkcije
                case KREIRAJ_KLIJENT: {
                    Klijent k = (Klijent) op.getRequest();
                    int id = klijentDBB.kreirajKlijent(k);
                    k.setIdKlijent(id);
                    op.setResponse(k);
                    op.setSuccess(true);
                    break;
                }
                case PROMENI_KLIJENT: {
                    Klijent k = (Klijent) op.getRequest();
                    klijentDBB.promeniKlijent(k);
                    op.setResponse(k);
                    op.setSuccess(true);
                    break;
                }
                case PRETRAZI_KLIJENT: {
                    int id = (Integer) op.getRequest();
                    Klijent k = klijentDBB.pretraziKlijent(id);
                    op.setResponse(k);
                    op.setSuccess(k != null);
                    if (k == null) {
                        op.setErrorMessage("Sistem ne može da nađe klijenta.");
                    }
                    break;
                }
                case OBRISI_KLIJENT: {
                    int id = (Integer) op.getRequest();
                    klijentDBB.obrisiKlijent(id);
                    op.setSuccess(true);
                    break;
                }
                case VRATI_LISTU_KLIJENT: {
                    Klijent kriterijum = (Klijent) op.getRequest();
                    List<Klijent> lista = klijentDBB.pretraziKlijente(kriterijum);
                    op.setResponse(lista);
                    op.setSuccess(!lista.isEmpty());
                    if (lista.isEmpty()) {
                        op.setErrorMessage("Sistem ne može da nađe klijente po zadatim kriterijumima.");
                    }
                    break;
                }
                case VRATI_LISTU_SVI_KLIJENT: {
                    List<Klijent> lista = klijentDBB.vratiSveKlijente();
                    op.setResponse(lista);
                    op.setSuccess(true);
                    break;
                }

                // funkcije za pravno lcie
                case KREIRAJ_PRAVNO_LICE: {
                    PravnoLice pl = (PravnoLice) op.getRequest();
                    pravnoLiceDBB.kreirajPravnoLice(pl);
                    op.setResponse(pl);
                    op.setSuccess(true);
                    break;
                }
                case UBACI_PRAVNO_LICE: {
                    PravnoLice pl = (PravnoLice) op.getRequest();
                    pravnoLiceDBB.ubaciPravnoLice(pl);
                    op.setResponse(pl);
                    op.setSuccess(true);
                    break;
                }
                case PROMENI_PRAVNO_LICE: {
                    PravnoLice pl = (PravnoLice) op.getRequest();
                    pravnoLiceDBB.promeniPravnoLice(pl);
                    op.setResponse(pl);
                    op.setSuccess(true);
                    break;
                }
                case OBRISI_PRAVNO_LICE: {
                    int id = (Integer) op.getRequest();
                    pravnoLiceDBB.obrisiPravnoLice(id);
                    op.setSuccess(true);
                    break;
                }
                case PRETRAZI_PRAVNO_LICE: {
                    int id = (Integer) op.getRequest();
                    PravnoLice pl = pravnoLiceDBB.pretraziPravnoLice(id);
                    op.setResponse(pl);
                    op.setSuccess(pl != null);
                    if (pl == null) {
                        op.setErrorMessage("Sistem ne može da nađe pravno lice.");
                    }
                    break;
                }
                case VRATI_LISTU_PRAVNO_LICE_PO_PRAVNOM_LICU:
                case VRATI_LISTU_PRAVNO_LICE_PO_KLIJENTU: {
                    PravnoLice kriterijum = (PravnoLice) op.getRequest();
                    List<PravnoLice> lista = pravnoLiceDBB.pretraziPravnoLicePoCriterijumu(kriterijum);
                    op.setResponse(lista);
                    op.setSuccess(!lista.isEmpty());
                    if (lista.isEmpty()) {
                        op.setErrorMessage("Sistem ne može da nađe pravna lica po zadatim kriterijumima.");
                    }
                    break;
                }

                // fiz. lice funkcije
                case KREIRAJ_FIZICKO_LICE: {
                    FizickoLice fl = (FizickoLice) op.getRequest();
                    fizickoLiceDBB.kreirajFizickoLice(fl);
                    op.setResponse(fl);
                    op.setSuccess(true);
                    break;
                }
                case UBACI_FIZICKO_LICE: {
                    FizickoLice fl = (FizickoLice) op.getRequest();
                    fizickoLiceDBB.ubaciFizickoLice(fl);
                    op.setResponse(fl);
                    op.setSuccess(true);
                    break;
                }
                case PROMENI_FIZICKO_LICE: {
                    FizickoLice fl = (FizickoLice) op.getRequest();
                    fizickoLiceDBB.promeniFizickoLice(fl);
                    op.setResponse(fl);
                    op.setSuccess(true);
                    break;
                }
                case OBRISI_FIZICKO_LICE: {
                    int id = (Integer) op.getRequest();
                    fizickoLiceDBB.obrisiFizickoLice(id);
                    op.setSuccess(true);
                    break;
                }
                case PRETRAZI_FIZICKO_LICE: {
                    int id = (Integer) op.getRequest();
                    FizickoLice fl = fizickoLiceDBB.pretraziFizickoLice(id);
                    op.setResponse(fl);
                    op.setSuccess(fl != null);
                    if (fl == null) {
                        op.setErrorMessage("Sistem ne može da nađe fizičko lice.");
                    }
                    break;
                }
                case VRATI_LISTU_FIZICKO_LICE_PO_FIZICKOM_LICU:
                case VRATI_LISTU_FIZICKO_LICE_PO_KLIJENTU: {
                    FizickoLice kriterijum = (FizickoLice) op.getRequest();
                    List<FizickoLice> lista = fizickoLiceDBB.pretraziFizickoLicePoCriterijumu(kriterijum);
                    op.setResponse(lista);
                    op.setSuccess(true);
                    break;
                }

                // radnik f-je
                case KREIRAJ_RADNIK: {
                    Radnik r = (Radnik) op.getRequest();
                    int id = radnikDBB.kreirajRadnik(r);
                    r.setIdRadnik(id);
                    if (r.getPrSsList() != null) {
                        for (PrSs prss : r.getPrSsList()) {
                            prss.setIdRadnik(id);
                            prSsDBB.ubaciPrSs(prss);
                        }
                    }
                    op.setResponse(r);
                    op.setSuccess(true);
                    break;
                }
                case PROMENI_RADNIK: {
                    Radnik r = (Radnik) op.getRequest();
                    radnikDBB.promeniRadnik(r);
                    op.setResponse(r);
                    op.setSuccess(true);
                    break;
                }
                case PRETRAZI_RADNIK: {
                    int id = (Integer) op.getRequest();
                    Radnik r = radnikDBB.pretraziRadnik(id);
                    if (r != null) {
                        r.setPrSsList(prSsDBB.vratiPrSsZaRadnika(id));
                    }
                    op.setResponse(r);
                    op.setSuccess(r != null);
                    if (r == null) {
                        op.setErrorMessage("Sistem ne može da nađe radnika.");
                    }
                    break;
                }
                case OBRISI_RADNIK: {
                    int id = (Integer) op.getRequest();
                    radnikDBB.obrisiRadnik(id);
                    op.setSuccess(true);
                    break;
                }
                case VRATI_LISTU_SVI_RADNIK: {
                    List<Radnik> lista = radnikDBB.vratiSveRadnike();
                    op.setResponse(lista);
                    op.setSuccess(true);
                    break;
                }
                case VRATI_LISTU_RADNIK_PO_RADNIKU: {
                    Radnik kriterijum = (Radnik) op.getRequest();
                    List<Radnik> lista = radnikDBB.pretraziRadnikePoKriterijumu(kriterijum);
                    op.setResponse(lista);
                    op.setSuccess(!lista.isEmpty());
                    if (lista.isEmpty()) {
                        op.setErrorMessage("Sistem ne može da nađe radnike po zadatim kriterijumima.");
                    }
                    break;
                }
                case VRATI_LISTU_RADNIK_PO_STRUCNOJ_SPREMI: {
                    int idSS = (Integer) op.getRequest();
                    List<Radnik> lista = radnikDBB.pretraziRadnikePoStrucnojSpremi(idSS);
                    op.setResponse(lista);
                    op.setSuccess(true);
                    break;
                }

                // oprema f-je
                case KREIRAJ_OPREMA: {
                    Oprema o = (Oprema) op.getRequest();
                    int id = opremaDBB.kreirajOprema(o);
                    o.setIdOprema(id);
                    op.setResponse(o);
                    op.setSuccess(true);
                    break;
                }
                case PROMENI_OPREMA: {
                    Oprema o = (Oprema) op.getRequest();
                    opremaDBB.promeniOprema(o);
                    op.setResponse(o);
                    op.setSuccess(true);
                    break;
                }
                case PRETRAZI_OPREMA: {
                    int id = (Integer) op.getRequest();
                    Oprema o = opremaDBB.pretraziOprema(id);
                    op.setResponse(o);
                    op.setSuccess(o != null);
                    if (o == null) {
                        op.setErrorMessage("Sistem ne može da nađe opremu.");
                    }
                    break;
                }
                case OBRISI_OPREMA: {
                    int id = (Integer) op.getRequest();
                    opremaDBB.obrisiOprema(id);
                    op.setSuccess(true);
                    break;
                }
                case VRATI_LISTU_SVI_OPREMA: {
                    List<Oprema> lista = opremaDBB.vratiSvuOpremu();
                    op.setResponse(lista);
                    op.setSuccess(true);
                    break;
                }
                case VRATI_LISTU_OPREMA: {
                    Oprema kriterijum = (Oprema) op.getRequest();
                    List<Oprema> lista = opremaDBB.pretraziOpremaPoCriterijumu(kriterijum);
                    op.setResponse(lista);
                    op.setSuccess(!lista.isEmpty());
                    if (lista.isEmpty()) {
                        op.setErrorMessage("Sistem ne može da nađe opremu po zadatim kriterijumima.");
                    }
                    break;
                }

                // strucna sprema 
                case KREIRAJ_STRUCNA_SPREMA: {
                    StrucnaSprema ss = (StrucnaSprema) op.getRequest();
                    int id = ssDBB.kreirajStrucnaSprema(ss);
                    ss.setIdStrucnaSprema(id);
                    op.setResponse(ss);
                    op.setSuccess(true);
                    break;
                }
                case UBACI_STRUCNA_SPREMA: {
                    PrSs prss = (PrSs) op.getRequest();
                    prSsDBB.ubaciPrSs(prss);
                    op.setSuccess(true);
                    break;
                }
                case PROMENI_STRUCNA_SPREMA: {
                    StrucnaSprema ss = (StrucnaSprema) op.getRequest();
                    ssDBB.promeniStrucnaSprema(ss);
                    op.setResponse(ss);
                    op.setSuccess(true);
                    break;
                }
                case PRETRAZI_STRUCNA_SPREMA: {
                    int id = (Integer) op.getRequest();
                    StrucnaSprema ss = ssDBB.pretraziStrucnaSprema(id);
                    op.setResponse(ss);
                    op.setSuccess(ss != null);
                    if (ss == null) {
                        op.setErrorMessage("Sistem ne može da nađe stručnu spremu.");
                    }
                    break;
                }
                case VRATI_LISTU_SVI_STRUCNA_SPREMA: {
                    List<StrucnaSprema> lista = ssDBB.vratiSveStrucneSprema();
                    op.setResponse(lista);
                    op.setSuccess(true);
                    break;
                }
                case VRATI_LISTU_STRUCNA_SPREMA: {
                    StrucnaSprema kriterijum = (StrucnaSprema) op.getRequest();
                    List<StrucnaSprema> lista = ssDBB.pretraziStrucneSprema(kriterijum);
                    op.setResponse(lista);
                    op.setSuccess(!lista.isEmpty());
                    if (lista.isEmpty()) {
                        op.setErrorMessage("Sistem ne može da nađe stručne spreme po zadatim kriterijumima.");
                    }
                    break;
                }
                case OBRISI_STRUCNA_SPREMA: {
                    StrucnaSprema ss = (StrucnaSprema) op.getRequest();
                    ssDBB.obrisiStrucnaSprema(ss.getIdStrucnaSprema());
                    op.setSuccess(true);
                    break;
                }

                default:
                    op.setSuccess(false);
                    op.setErrorMessage("Nepoznata operacija: " + op.getType());
            }
        } catch (Exception e) {
            op.setSuccess(false);
            op.setErrorMessage("Greška na serveru: " + e.getMessage());
            e.printStackTrace();
        }
        return op;
    }
}
