package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Klijent;

public class KlijentDBB {

    private final DBBroker db = DBBroker.getInstance();

    public int kreirajKlijent(Klijent k) throws Exception {
        String sql = "INSERT INTO Klijent (adresa, telefon, email) VALUES (?,?,?)";
        PreparedStatement ps = db.prepareStatementWithKeys(sql);
        ps.setString(1, k.getAdresa());
        ps.setString(2, k.getTelefon());
        ps.setString(3, k.getEmail());
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) return keys.getInt(1);
        return -1;
    }

    public void promeniKlijent(Klijent k) throws Exception {
        String sql = "UPDATE Klijent SET adresa=?, telefon=?, email=? WHERE idKlijent=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, k.getAdresa());
        ps.setString(2, k.getTelefon());
        ps.setString(3, k.getEmail());
        ps.setInt(4, k.getIdKlijent());
        ps.executeUpdate();
    }

    public void obrisiKlijent(int idKlijent) throws Exception {
        String sql = "DELETE FROM Klijent WHERE idKlijent = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idKlijent);
        ps.executeUpdate();
    }

    public Klijent pretraziKlijent(int idKlijent) throws Exception {
        String sql = "SELECT * FROM Klijent WHERE idKlijent = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idKlijent);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return mapKlijent(rs);
        return null;
    }

    public List<Klijent> vratiSveKlijente() throws Exception {
        ResultSet rs = db.executeQuery("SELECT * FROM Klijent ORDER BY idKlijent");
        List<Klijent> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapKlijent(rs));
        return lista;
    }

    public List<Klijent> pretraziKlijente(Klijent kriterijum) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM Klijent WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (kriterijum.getAdresa() != null && !kriterijum.getAdresa().isEmpty()) {
            sql.append(" AND adresa LIKE ?");
            params.add("%" + kriterijum.getAdresa() + "%");
        }
        if (kriterijum.getEmail() != null && !kriterijum.getEmail().isEmpty()) {
            sql.append(" AND email LIKE ?");
            params.add("%" + kriterijum.getEmail() + "%");
        }
        if (kriterijum.getTelefon() != null && !kriterijum.getTelefon().isEmpty()) {
            sql.append(" AND telefon LIKE ?");
            params.add("%" + kriterijum.getTelefon() + "%");
        }
        PreparedStatement ps = db.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
        ResultSet rs = ps.executeQuery();
        List<Klijent> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapKlijent(rs));
        return lista;
    }

    public Klijent mapKlijent(ResultSet rs) throws Exception {
        return new Klijent(
            rs.getInt("idKlijent"),
            rs.getString("adresa"),
            rs.getString("telefon"),
            rs.getString("email")
        );
    }
}
