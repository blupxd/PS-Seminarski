package models;

import annotations.Column;
import annotations.Table;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "Ugovor")
public class Ugovor extends GenericEntity implements Serializable {

    @Column(name = "idUgovor", isPrimaryKey = true, isAutoIncrement = true)
    private int idUgovor;

    @Column(name = "datumIzdavanja")
    private Date datumIzdavanja;

    @Column(name = "popust")
    private int popust;

    @Column(name = "datumVracanjaPlaniran")
    private Date datumVracanjaPlaniran;

    @Column(name = "datumVracanjaStvarni")
    private Date datumVracanjaStvarni;

    @Column(name = "ukupanIznos")
    private double ukupanIznos;

    @Column(name = "status")
    private String status;

    @Column(name = "idRadnik")
    private int idRadnik;

    @Column(name = "idKlijent")
    private int idKlijent;

    private Radnik radnik;
    private Klijent klijent;
    private List<StavkaUgovora> stavke = new ArrayList<>();

    public static final String STATUS_AKTIVAN = "Aktivan";
    public static final String STATUS_ZAVRSEN = "Zavrsen";
    public static final String STATUS_STORNIRAN = "Storniran";

    public Ugovor() {
    }

    public Ugovor(int idUgovor, Date datumIzdavanja, int popust,
            Date datumVracanjaPlaniran, Date datumVracanjaStvarni,
            double ukupanIznos, String status, int idRadnik, int idKlijent) {
        this.idUgovor = idUgovor;
        this.datumIzdavanja = datumIzdavanja;
        this.popust = popust;
        this.datumVracanjaPlaniran = datumVracanjaPlaniran;
        this.datumVracanjaStvarni = datumVracanjaStvarni;
        this.ukupanIznos = ukupanIznos;
        this.status = status;
        this.idRadnik = idRadnik;
        this.idKlijent = idKlijent;
    }

    public int getIdUgovor() {
        return idUgovor;
    }

    public void setIdUgovor(int idUgovor) {
        this.idUgovor = idUgovor;
    }

    public Date getDatumIzdavanja() {
        return datumIzdavanja;
    }

    public void setDatumIzdavanja(Date datumIzdavanja) {
        this.datumIzdavanja = datumIzdavanja;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    public Date getDatumVracanjaPlaniran() {
        return datumVracanjaPlaniran;
    }

    public void setDatumVracanjaPlaniran(Date datumVracanjaPlaniran) {
        this.datumVracanjaPlaniran = datumVracanjaPlaniran;
    }

    public Date getDatumVracanjaStvarni() {
        return datumVracanjaStvarni;
    }

    public void setDatumVracanjaStvarni(Date datumVracanjaStvarni) {
        this.datumVracanjaStvarni = datumVracanjaStvarni;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdRadnik() {
        return idRadnik;
    }

    public void setIdRadnik(int idRadnik) {
        this.idRadnik = idRadnik;
    }

    public int getIdKlijent() {
        return idKlijent;
    }

    public void setIdKlijent(int idKlijent) {
        this.idKlijent = idKlijent;
    }

    public Radnik getRadnik() {
        return radnik;
    }

    public void setRadnik(Radnik radnik) {
        this.radnik = radnik;
    }

    public Klijent getKlijent() {
        return klijent;
    }

    public void setKlijent(Klijent klijent) {
        this.klijent = klijent;
    }

    public List<StavkaUgovora> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaUgovora> stavke) {
        this.stavke = stavke;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws Exception {
        return new Ugovor(
            rs.getInt("idUgovor"),
            rs.getTimestamp("datumIzdavanja"),
            rs.getInt("popust"),
            rs.getTimestamp("datumVracanjaPlaniran"),
            rs.getTimestamp("datumVracanjaStvarni"),
            rs.getDouble("ukupanIznos"),
            rs.getString("status"),
            rs.getInt("idRadnik"),
            rs.getInt("idKlijent")
        );
    }

    @Override
    public String toString() {
        return "Ugovor #" + idUgovor + " [" + status + "]";
    }
}
