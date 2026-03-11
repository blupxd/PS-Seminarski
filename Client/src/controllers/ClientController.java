package controllers;

import models.FizickoLice;
import models.Klijent;
import models.Oprema;
import models.PravnoLice;
import models.PrSs;
import models.Radnik;
import models.StavkaUgovora;
import models.StrucnaSprema;
import models.Ugovor;
import operations.Operation;
import operations.OperationType;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientController {

    private static ClientController instance;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private static final String HOST = "localhost";
    private static final int PORT = 9000;

    private ClientController() {
    }

    public static ClientController getInstance() {
        if (instance == null) {
            instance = new ClientController();
        }
        return instance;
    }

    public void connect() throws Exception {
        socket = new Socket(HOST, PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        System.out.println("[Klijent] Konekcija uspostavljena: " + HOST + ":" + PORT);
    }

    public void disconnect() throws Exception {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("[Klijent] Konekcija zatvorena.");
        }
    }

    public Operation sendOperation(Operation op) throws Exception {
        out.writeObject(op);
        out.flush();
        out.reset();
        return (Operation) in.readObject();
    }

    //Autentikacija

    public Radnik prijaviRadnika(Radnik r) throws Exception {
        Operation op = new Operation(OperationType.PRIJAVI_RADNIK, r);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (Radnik) op.getResponse();
    }

    //Radnik

    public Radnik kreirajRadnika(Radnik r) throws Exception {
        Operation op = new Operation(OperationType.KREIRAJ_RADNIK, r);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (Radnik) op.getResponse();
    }

    public Radnik promeniRadnika(Radnik r) throws Exception {
        Operation op = new Operation(OperationType.PROMENI_RADNIK, r);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (Radnik) op.getResponse();
    }

    public void obrisiRadnika(int idRadnik) throws Exception {
        Operation op = new Operation(OperationType.OBRISI_RADNIK, idRadnik);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
    }

    @SuppressWarnings("unchecked")
    public List<Radnik> vratiSveRadnike() throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_SVI_RADNIK, null);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Radnik>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<Radnik> pretrazujRadnike(Radnik kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_RADNIK_PO_RADNIKU, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Radnik>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<Radnik> vratiRadnikePoStrucnojSpremi(int idStrucnaSprema) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_RADNIK_PO_STRUCNOJ_SPREMI, idStrucnaSprema);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Radnik>) op.getResponse();
    }

    // Klijent

    public Klijent kreirajKlijenta(Klijent k) throws Exception {
        Operation op = new Operation(OperationType.KREIRAJ_KLIJENT, k);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (Klijent) op.getResponse();
    }

    public Klijent promeniKlijenta(Klijent k) throws Exception {
        Operation op = new Operation(OperationType.PROMENI_KLIJENT, k);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (Klijent) op.getResponse();
    }

    public void obrisiKlijenta(int idKlijent) throws Exception {
        Operation op = new Operation(OperationType.OBRISI_KLIJENT, idKlijent);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
    }

    @SuppressWarnings("unchecked")
    public List<Klijent> pretrazujKlijente(Klijent kriterijum) throws Exception {
        Operation op = new Operation(OperationType.PRETRAZI_KLIJENT, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Klijent>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<Klijent> vratiKlijentePo(Klijent kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_KLIJENT, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Klijent>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<Klijent> vratiSveKlijente() throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_SVI_KLIJENT, null);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Klijent>) op.getResponse();
    }

    //FizickoLice

    public FizickoLice kreirajFizickoLice(FizickoLice fl) throws Exception {
        Operation op = new Operation(OperationType.KREIRAJ_FIZICKO_LICE, fl);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (FizickoLice) op.getResponse();
    }

    public FizickoLice ubaciFizickoLice(FizickoLice fl) throws Exception {
        Operation op = new Operation(OperationType.UBACI_FIZICKO_LICE, fl);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (FizickoLice) op.getResponse();
    }

    public FizickoLice promeniFizickoLice(FizickoLice fl) throws Exception {
        Operation op = new Operation(OperationType.PROMENI_FIZICKO_LICE, fl);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (FizickoLice) op.getResponse();
    }

    public void obrisiFizickoLice(int idKlijent) throws Exception {
        Operation op = new Operation(OperationType.OBRISI_FIZICKO_LICE, idKlijent);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
    }

    @SuppressWarnings("unchecked")
    public List<FizickoLice> pretrazujFizickaLica(FizickoLice kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_FIZICKO_LICE_PO_FIZICKOM_LICU, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<FizickoLice>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<FizickoLice> vratiFizickaLicaPoKlijentu(FizickoLice kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_FIZICKO_LICE_PO_KLIJENTU, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<FizickoLice>) op.getResponse();
    }

    //PravnoLice

    public PravnoLice kreirajPravnoLice(PravnoLice pl) throws Exception {
        Operation op = new Operation(OperationType.KREIRAJ_PRAVNO_LICE, pl);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (PravnoLice) op.getResponse();
    }

    public PravnoLice ubacijPravnoLice(PravnoLice pl) throws Exception {
        Operation op = new Operation(OperationType.UBACI_PRAVNO_LICE, pl);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (PravnoLice) op.getResponse();
    }

    public PravnoLice promeniPravnoLice(PravnoLice pl) throws Exception {
        Operation op = new Operation(OperationType.PROMENI_PRAVNO_LICE, pl);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (PravnoLice) op.getResponse();
    }

    public void obrisiPravnoLice(int idKlijent) throws Exception {
        Operation op = new Operation(OperationType.OBRISI_PRAVNO_LICE, idKlijent);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
    }

    @SuppressWarnings("unchecked")
    public List<PravnoLice> pretrazujPravnaLica(PravnoLice kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_PRAVNO_LICE_PO_PRAVNOM_LICU, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<PravnoLice>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<PravnoLice> vratiPravnaLicaPoKlijentu(PravnoLice kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_PRAVNO_LICE_PO_KLIJENTU, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<PravnoLice>) op.getResponse();
    }

    // Oprema 

    public Oprema kreirajOpremu(Oprema o) throws Exception {
        Operation op = new Operation(OperationType.KREIRAJ_OPREMA, o);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (Oprema) op.getResponse();
    }

    public Oprema promeniOpremu(Oprema o) throws Exception {
        Operation op = new Operation(OperationType.PROMENI_OPREMA, o);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (Oprema) op.getResponse();
    }

    public void obrisiOpremu(int idOprema) throws Exception {
        Operation op = new Operation(OperationType.OBRISI_OPREMA, idOprema);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
    }

    @SuppressWarnings("unchecked")
    public List<Oprema> vratiSvuOpremu() throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_SVI_OPREMA, null);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Oprema>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<Oprema> pretrazujOpremu(Oprema kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_OPREMA, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Oprema>) op.getResponse();
    }

    //StrucnaSprema

    public StrucnaSprema kreirajStrucnuSpremu(StrucnaSprema ss) throws Exception {
        Operation op = new Operation(OperationType.KREIRAJ_STRUCNA_SPREMA, ss);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (StrucnaSprema) op.getResponse();
    }

    public StrucnaSprema promeniStrucnuSpremu(StrucnaSprema ss) throws Exception {
        Operation op = new Operation(OperationType.PROMENI_STRUCNA_SPREMA, ss);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (StrucnaSprema) op.getResponse();
    }

    public void obrisiStrucnuSpremu(StrucnaSprema ss) throws Exception {
        Operation op = new Operation(OperationType.OBRISI_STRUCNA_SPREMA, ss);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
    }

    public void ubaciStrucnuSpremu(PrSs prSs) throws Exception {
        Operation op = new Operation(OperationType.UBACI_STRUCNA_SPREMA, prSs);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
    }

    @SuppressWarnings("unchecked")
    public List<StrucnaSprema> vratiSveStrucneSpreme() throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_SVI_STRUCNA_SPREMA, null);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<StrucnaSprema>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<StrucnaSprema> pretrazujStrucneSpreme(StrucnaSprema kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_STRUCNA_SPREMA, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<StrucnaSprema>) op.getResponse();
    }

    // Ugovor

    public Ugovor kreirajUgovor(Ugovor u) throws Exception {
        Operation op = new Operation(OperationType.KREIRAJ_UGOVOR, u);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (Ugovor) op.getResponse();
    }

    public Ugovor promeniUgovor(Ugovor u) throws Exception {
        Operation op = new Operation(OperationType.PROMENI_UGOVOR, u);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (Ugovor) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<Ugovor> pretrazujUgovore(Ugovor kriterijum) throws Exception {
        Operation op = new Operation(OperationType.PRETRAZI_UGOVOR, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Ugovor>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<Ugovor> vratiUgovorePoKlijentu(Ugovor kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_UGOVOR_PO_KLIJENTU, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Ugovor>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<Ugovor> vratiUgovorePoRadniku(Ugovor kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_UGOVOR_PO_RADNIKU, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Ugovor>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<Ugovor> vratiUgovorePoUgovoru(Ugovor kriterijum) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_UGOVOR_PO_UGOVORU, kriterijum);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Ugovor>) op.getResponse();
    }

    @SuppressWarnings("unchecked")
    public List<Ugovor> vratiUgovorePoOpremi(int idOprema) throws Exception {
        Operation op = new Operation(OperationType.VRATI_LISTU_UGOVOR_PO_OPREMI, idOprema);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (List<Ugovor>) op.getResponse();
    }

    public int proveriDostupnostOpreme(StavkaUgovora stavka) throws Exception {
        Operation op = new Operation(OperationType.PROVERI_DOSTUPNOST_OPREME, stavka);
        op = sendOperation(op);
        if (!op.isSuccess()) throw new Exception(op.getErrorMessage());
        return (int) op.getResponse();
    }
}
