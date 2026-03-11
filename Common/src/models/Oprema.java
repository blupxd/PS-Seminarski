package models;

import annotations.Column;
import annotations.Table;
import java.io.Serializable;
import java.sql.ResultSet;

@Table(name = "Oprema")
public class Oprema extends GenericEntity implements Serializable {

    @Column(name = "idOprema", isPrimaryKey = true, isAutoIncrement = true)
    private int idOprema;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "serijskiBroj")
    private String serijskiBroj;

    @Column(name = "opis")
    private String opis;

    @Column(name = "cenaPoDanu")
    private double cenaPoDanu;

    @Column(name = "kolicinaDostupna")
    private int kolicinaDostupna;

    public Oprema() {
    }

    public Oprema(int idOprema, String naziv, String serijskiBroj, String opis, double cenaPoDanu, int kolicinaDostupna) {
        this.idOprema = idOprema;
        this.naziv = naziv;
        this.serijskiBroj = serijskiBroj;
        this.opis = opis;
        this.cenaPoDanu = cenaPoDanu;
        this.kolicinaDostupna = kolicinaDostupna;
    }

    public int getIdOprema() {
        return idOprema;
    }

    public void setIdOprema(int idOprema) {
        this.idOprema = idOprema;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSerijskiBroj() {
        return serijskiBroj;
    }

    public void setSerijskiBroj(String serijskiBroj) {
        this.serijskiBroj = serijskiBroj;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCenaPoDanu() {
        return cenaPoDanu;
    }

    public void setCenaPoDanu(double cenaPoDanu) {
        this.cenaPoDanu = cenaPoDanu;
    }

    public int getKolicinaDostupna() {
        return kolicinaDostupna;
    }

    public void setKolicinaDostupna(int kolicinaDostupna) {
        this.kolicinaDostupna = kolicinaDostupna;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws Exception {
        return new Oprema(
            rs.getInt("idOprema"),
            rs.getString("naziv"),
            rs.getString("serijskiBroj"),
            rs.getString("opis"),
            rs.getDouble("cenaPoDanu"),
            rs.getInt("kolicinaDostupna")
        );
    }

    @Override
    public String toString() {
        return naziv + " (Serial:" + serijskiBroj + ")";
    }
}
