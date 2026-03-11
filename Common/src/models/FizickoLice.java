package models;

import annotations.Column;
import annotations.Table;
import java.io.Serializable;
import java.sql.ResultSet;

@Table(name = "FizickoLice")
public class FizickoLice extends Klijent implements Serializable {

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "JMBG")
    private String JMBG;

    @Column(name = "brojLicneKarte")
    private String brojLicneKarte;

    public FizickoLice() {
    }

    public FizickoLice(int idKlijent, String adresa, String telefon, String email,
            String ime, String prezime, String JMBG, String brojLicneKarte) {
        super(idKlijent, adresa, telefon, email);
        this.ime = ime;
        this.prezime = prezime;
        this.JMBG = JMBG;
        this.brojLicneKarte = brojLicneKarte;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJMBG() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public String getBrojLicneKarte() {
        return brojLicneKarte;
    }

    public void setBrojLicneKarte(String brojLicneKarte) {
        this.brojLicneKarte = brojLicneKarte;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws Exception {
        return new FizickoLice(
            rs.getInt("idKlijent"),
            rs.getString("adresa"),
            rs.getString("telefon"),
            rs.getString("email"),
            rs.getString("ime"),
            rs.getString("prezime"),
            rs.getString("JMBG"),
            rs.getString("brojLicneKarte")
        );
    }

    @Override
    public String toString() {
        return ime + " " + prezime + " (JMBG: " + JMBG + ")";
    }
}
