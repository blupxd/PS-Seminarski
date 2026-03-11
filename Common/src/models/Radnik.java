package models;

import annotations.Column;
import annotations.Table;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Radnik")
public class Radnik extends GenericEntity implements Serializable {

    @Column(name = "idRadnik", isPrimaryKey = true, isAutoIncrement = true)
    private int idRadnik;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "korisnickoIme")
    private String korisnickoIme;

    @Column(name = "sifra")
    private String sifra;

    @Column(name = "email")
    private String email;

    private List<PrSs> prSsList = new ArrayList<>();

    public Radnik() {
    }

    public Radnik(int idRadnik, String ime, String prezime, String korisnickoIme, String sifra, String email) {
        this.idRadnik = idRadnik;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
        this.email = email;
    }

    public int getIdRadnik() {
        return idRadnik;
    }

    public void setIdRadnik(int idRadnik) {
        this.idRadnik = idRadnik;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PrSs> getPrSsList() {
        return prSsList;
    }

    public void setPrSsList(List<PrSs> prSsList) {
        this.prSsList = prSsList;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws Exception {
        return new Radnik(
            rs.getInt("idRadnik"),
            rs.getString("ime"),
            rs.getString("prezime"),
            rs.getString("korisnickoIme"),
            rs.getString("sifra"),
            rs.getString("email")
        );
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }
}
