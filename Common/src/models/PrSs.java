package models;

import annotations.Column;
import annotations.Table;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;

@Table(name = "PrSs")
public class PrSs extends GenericEntity implements Serializable {

    @Column(name = "idRadnik", isPrimaryKey = true)
    private int idRadnik;

    @Column(name = "idStrucnaSprema", isPrimaryKey = true)
    private int idStrucnaSprema;

    @Column(name = "datumSticanja")
    private Date datumSticanja;

    private Radnik radnik;
    private StrucnaSprema strucnaSprema;

    public PrSs() {
    }

    public PrSs(int idRadnik, int idStrucnaSprema, Date datumSticanja) {
        this.idRadnik = idRadnik;
        this.idStrucnaSprema = idStrucnaSprema;
        this.datumSticanja = datumSticanja;
    }

    public int getIdRadnik() {
        return idRadnik;
    }

    public void setIdRadnik(int idRadnik) {
        this.idRadnik = idRadnik;
    }

    public int getIdStrucnaSprema() {
        return idStrucnaSprema;
    }

    public void setIdStrucnaSprema(int idStrucnaSprema) {
        this.idStrucnaSprema = idStrucnaSprema;
    }

    public Date getDatumSticanja() {
        return datumSticanja;
    }

    public void setDatumSticanja(Date datumSticanja) {
        this.datumSticanja = datumSticanja;
    }

    public Radnik getRadnik() {
        return radnik;
    }

    public void setRadnik(Radnik radnik) {
        this.radnik = radnik;
    }

    public StrucnaSprema getStrucnaSprema() {
        return strucnaSprema;
    }

    public void setStrucnaSprema(StrucnaSprema strucnaSprema) {
        this.strucnaSprema = strucnaSprema;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws Exception {
        PrSs p = new PrSs(
            rs.getInt("idRadnik"),
            rs.getInt("idStrucnaSprema"),
            rs.getTimestamp("datumSticanja")
        );
        return p;
    }

    @Override
    public String toString() {
        return (strucnaSprema != null ? strucnaSprema.getNazivSpreme() : "id:" + idStrucnaSprema)
                + " (" + datumSticanja + ")";
    }
}
