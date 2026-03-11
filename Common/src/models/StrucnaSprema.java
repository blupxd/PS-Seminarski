package models;

import annotations.Column;
import annotations.Table;
import java.io.Serializable;
import java.sql.ResultSet;

@Table(name = "StrucnaSprema")
public class StrucnaSprema extends GenericEntity implements Serializable {

    @Column(name = "idStrucnaSprema", isPrimaryKey = true, isAutoIncrement = true)
    private int idStrucnaSprema;

    @Column(name = "nazivSpreme")
    private String nazivSpreme;

    @Column(name = "stepen")
    private String stepen;

    public StrucnaSprema() {
    }

    public StrucnaSprema(int idStrucnaSprema, String nazivSpreme, String stepen) {
        this.idStrucnaSprema = idStrucnaSprema;
        this.nazivSpreme = nazivSpreme;
        this.stepen = stepen;
    }

    public int getIdStrucnaSprema() {
        return idStrucnaSprema;
    }

    public void setIdStrucnaSprema(int idStrucnaSprema) {
        this.idStrucnaSprema = idStrucnaSprema;
    }

    public String getNazivSpreme() {
        return nazivSpreme;
    }

    public void setNazivSpreme(String nazivSpreme) {
        this.nazivSpreme = nazivSpreme;
    }

    public String getStepen() {
        return stepen;
    }

    public void setStepen(String stepen) {
        this.stepen = stepen;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws Exception {
        return new StrucnaSprema(
            rs.getInt("idStrucnaSprema"),
            rs.getString("nazivSpreme"),
            rs.getString("stepen")
        );
    }

    @Override
    public String toString() {
        return nazivSpreme + " (" + stepen + ")";
    }
}
