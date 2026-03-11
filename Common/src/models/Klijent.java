package models;

import annotations.Column;
import annotations.Table;
import java.io.Serializable;
import java.sql.ResultSet;

@Table(name = "Klijent")
public class Klijent extends GenericEntity implements Serializable {

    @Column(name = "idKlijent", isPrimaryKey = true, isAutoIncrement = true)
    private int idKlijent;

    @Column(name = "adresa")
    private String adresa;

    @Column(name = "telefon")
    private String telefon;

    @Column(name = "email")
    private String email;

    public Klijent() {
    }

    public Klijent(int idKlijent, String adresa, String telefon, String email) {
        this.idKlijent = idKlijent;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
    }

    public int getIdKlijent() {
        return idKlijent;
    }

    public void setIdKlijent(int idKlijent) {
        this.idKlijent = idKlijent;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws Exception {
        return new Klijent(
            rs.getInt("idKlijent"),
            rs.getString("adresa"),
            rs.getString("telefon"),
            rs.getString("email")
        );
    }

    @Override
    public String toString() {
        return "Klijent [" + idKlijent + "] " + adresa;
    }
}
