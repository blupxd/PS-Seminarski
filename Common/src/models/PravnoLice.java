package models;

import annotations.Column;
import annotations.Table;
import java.io.Serializable;
import java.sql.ResultSet;

@Table(name = "PravnoLice")
public class PravnoLice extends Klijent implements Serializable {

    @Column(name = "nazivFirme")
    private String nazivFirme;

    @Column(name = "PIB")
    private String PIB;

    @Column(name = "maticniBroj")
    private String maticniBroj;

    public PravnoLice() {
    }

    public PravnoLice(int idKlijent, String adresa, String telefon, String email,
            String nazivFirme, String PIB, String maticniBroj) {
        super(idKlijent, adresa, telefon, email);
        this.nazivFirme = nazivFirme;
        this.PIB = PIB;
        this.maticniBroj = maticniBroj;
    }

    public String getNazivFirme() {
        return nazivFirme;
    }

    public void setNazivFirme(String nazivFirme) {
        this.nazivFirme = nazivFirme;
    }

    public String getPIB() {
        return PIB;
    }

    public void setPIB(String PIB) {
        this.PIB = PIB;
    }

    public String getMaticniBroj() {
        return maticniBroj;
    }

    public void setMaticniBroj(String maticniBroj) {
        this.maticniBroj = maticniBroj;
    }

    @Override
    public GenericEntity fromResultSet(ResultSet rs) throws Exception {
        return new PravnoLice(
            rs.getInt("idKlijent"),
            rs.getString("adresa"),
            rs.getString("telefon"),
            rs.getString("email"),
            rs.getString("nazivFirme"),
            rs.getString("PIB"),
            rs.getString("maticniBroj")
        );
    }

    @Override
    public String toString() {
        return nazivFirme + " (PIB: " + PIB + ")";
    }
}
