package controllers;

import operations.Operation;
import operations.OperationType;
import so.auth.SoPrijaviRadnik;
import so.fizickolice.*;
import so.klijent.*;
import so.oprema.*;
import so.pravnolice.*;
import so.radnik.*;
import so.strucnasprema.*;
import so.ugovor.*;

public class OperationHandler {

    public Operation execute(Operation op) {
        ApstraktnaGenerickaOperacija so;
        switch (op.getType()) {

            // auth
            case PRIJAVI_RADNIK: so = new SoPrijaviRadnik(); break;

            // ugovor
            case PROVERI_DOSTUPNOST_OPREME: so = new SoProveriDostupnostOpreme(); break;
            case KREIRAJ_UGOVOR: so = new SoKreirajUgovor(); break;
            case PROMENI_UGOVOR: so = new SoPromeniUgovor(); break;
            case PRETRAZI_UGOVOR: so = new SoPretraziUgovor(); break;
            case VRATI_LISTU_UGOVOR_PO_UGOVORU:
            case VRATI_LISTU_UGOVOR_PO_RADNIKU:
            case VRATI_LISTU_UGOVOR_PO_KLIJENTU: so = new SoVratiListuUgovoraPoCriterijumu(); break;
            case VRATI_LISTU_UGOVOR_PO_OPREMI: so = new SoVratiListuUgovoraPoOpremi(); break;

            // klijent
            case KREIRAJ_KLIJENT: so = new SoKreirajKlijent(); break;
            case PROMENI_KLIJENT: so = new SoPromeniKlijent(); break;
            case PRETRAZI_KLIJENT: so = new SoPretraziKlijent(); break;
            case OBRISI_KLIJENT: so = new SoObrisiKlijent(); break;
            case VRATI_LISTU_KLIJENT: so = new SoVratiListuKlijent(); break;
            case VRATI_LISTU_SVI_KLIJENT: so = new SoVratiListuSviKlijent(); break;

            // pravno lice
            case KREIRAJ_PRAVNO_LICE: so = new SoKreirajPravnoLice(); break;
            case UBACI_PRAVNO_LICE: so = new SoUbaciPravnoLice(); break;
            case PROMENI_PRAVNO_LICE: so = new SoPromeniPravnoLice(); break;
            case OBRISI_PRAVNO_LICE: so = new SoObrisiPravnoLice(); break;
            case PRETRAZI_PRAVNO_LICE: so = new SoPretraziPravnoLice(); break;
            case VRATI_LISTU_PRAVNO_LICE_PO_PRAVNOM_LICU:
            case VRATI_LISTU_PRAVNO_LICE_PO_KLIJENTU: so = new SoVratiListuPravnoLice(); break;

            // fizicko lice
            case KREIRAJ_FIZICKO_LICE: so = new SoKreirajFizickoLice(); break;
            case UBACI_FIZICKO_LICE: so = new SoUbaciFizickoLice(); break;
            case PROMENI_FIZICKO_LICE: so = new SoPromeniFizickoLice(); break;
            case OBRISI_FIZICKO_LICE: so = new SoObrisiFizickoLice(); break;
            case PRETRAZI_FIZICKO_LICE: so = new SoPretraziFlice(); break;
            case VRATI_LISTU_FIZICKO_LICE_PO_FIZICKOM_LICU:
            case VRATI_LISTU_FIZICKO_LICE_PO_KLIJENTU: so = new SoVratiListuFizickoLice(); break;

            // radnik
            case KREIRAJ_RADNIK: so = new SoKreirajRadnik(); break;
            case PROMENI_RADNIK: so = new SoPromeniRadnik(); break;
            case PRETRAZI_RADNIK: so = new SoPretraziRadnik(); break;
            case OBRISI_RADNIK: so = new SoObrisiRadnik(); break;
            case VRATI_LISTU_SVI_RADNIK: so = new SoVratiListuSviRadnik(); break;
            case VRATI_LISTU_RADNIK_PO_RADNIKU: so = new SoVratiListuRadnikPoRadniku(); break;
            case VRATI_LISTU_RADNIK_PO_STRUCNOJ_SPREMI: so = new SoVratiListuRadnikPoStrucnojSpremi(); break;

            // oprema
            case KREIRAJ_OPREMA: so = new SoKreirajOprema(); break;
            case PROMENI_OPREMA: so = new SoPromeniOprema(); break;
            case PRETRAZI_OPREMA: so = new SoPretraziOprema(); break;
            case OBRISI_OPREMA: so = new SoObrisiOprema(); break;
            case VRATI_LISTU_OPREMA: so = new SoVratiListuOprema(); break;
            case VRATI_LISTU_SVI_OPREMA: so = new SoVratiListuSviOprema(); break;

            // strucna sprema
            case KREIRAJ_STRUCNA_SPREMA: so = new SoKreirajStrucnaSprema(); break;
            case UBACI_STRUCNA_SPREMA: so = new SoUbaciStrucnaSprema(); break;
            case PROMENI_STRUCNA_SPREMA: so = new SoPromeniStrucnaSprema(); break;
            case PRETRAZI_STRUCNA_SPREMA: so = new SoPretraziStrucnaSprema(); break;
            case VRATI_LISTU_SVI_STRUCNA_SPREMA: so = new SoVratiListuSviStrucnaSprema(); break;
            case VRATI_LISTU_STRUCNA_SPREMA: so = new SoVratiListuStrucnaSprema(); break;
            case OBRISI_STRUCNA_SPREMA: so = new SoObrisiStrucnaSprema(); break;

            default:
                op.setSuccess(false);
                op.setErrorMessage("Nepoznata operacija: " + op.getType());
                return op;
        }

        try {
            so.izvrsi(op.getRequest());
            op.setResponse(so.getRezultat());
            op.setSuccess(true);
        } catch (Exception e) {
            op.setSuccess(false);
            op.setErrorMessage(e.getMessage());
        }
        return op;
    }
}
