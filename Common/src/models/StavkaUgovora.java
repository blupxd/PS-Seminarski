package models;

import annotations.Column;
import annotations.Table;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;

@Table(name = "StavkaUgovora")
public class StavkaUgovora extends GenericEntity implements Serializable {

    @Column(name = "idUgovor", isPrimaryKey = true)
    private int idUgovor;

    @Column(name = "rb", isPrimaryKey = true)
    private int rb;

    @Column(name = "kolicina")
    private int kolicina;

    @Column(name = "iznos")
    private double iznos;

    @Column(name = "datumOd")
    private Date datumOd;

    @Column(name = "datumDo")
    private Date datumDo;

    @Column(name = "idOprema")
    private int idOprema;

    private Oprema oprema;

    public StavkaUgovora() {
    }

    public StavkaUgovora(int idUgovor, int rb, int kolicina, double iznos, int idOprema) {
        this.idUgovor = idUgovor;
        this.rb = rb;
        this.kolicina = kolicina;
        this.iznos = iznos;
        this.idOprema = idOprema;
    }

    public int getIdUgovor() {
        return idUgovor;
    }

    public void setIdUgovor(int idUgovor) {
        this.idUgovor = idUgovor;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public Date getDatumOd() {
        return datumOd;
    }

    public void setDatumOd(Date datumOd) {
        this.datumOd = datumOd;
    }

    public Date getDatumDo() {
        return datumDo;
    }

    public void setDatumDo(Date datumDo) {
        this.datumDo = datumDo;
    }

    public int getIdOprema() {
        return idOprema;
    }

    public void setIdOprema(int idOprema) {
        this.idOprema = idOprema;
    }

    public Oprema getOprema() {
        return oprema;
    }

    public void setOprema(Oprema oprema) {
        this.oprema = oprema;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws Exception {
        StavkaUgovora s = new StavkaUgovora(
            rs.getInt("idUgovor"),
            rs.getInt("rb"),
            rs.getInt("kolicina"),
            rs.getDouble("iznos"),
            rs.getInt("idOprema")
        );
        s.setDatumOd(rs.getTimestamp("datumOd"));
        s.setDatumDo(rs.getTimestamp("datumDo"));
        return s;
    }

    @Override
    public String toString() {
        return "Stavka " + rb + " - " + (oprema != null ? oprema.getNaziv() : "id:" + idOprema)
                + " x" + kolicina + " = " + iznos;
    }
}
