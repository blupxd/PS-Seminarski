package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.PrSs;
import models.Radnik;

public class RadnikDBB {

    private final DBBroker db = DBBroker.getInstance();

    public Radnik prijaviRadnik(String korisnickoIme, String sifra) throws Exception {
        String sql = "SELECT * FROM Radnik WHERE korisnickoIme = ? AND sifra = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, korisnickoIme);
        ps.setString(2, sifra);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return mapRadnik(rs);
        }
        return null;
    }

    public int kreirajRadnik(Radnik r) throws Exception {
        String sql = "INSERT INTO Radnik (ime, prezime, korisnickoIme, sifra, email) VALUES (?,?,?,?,?)";
        PreparedStatement ps = db.prepareStatementWithKeys(sql);
        ps.setString(1, r.getIme());
        ps.setString(2, r.getPrezime());
        ps.setString(3, r.getKorisnickoIme());
        ps.setString(4, r.getSifra());
        ps.setString(5, r.getEmail());
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) return keys.getInt(1);
        return -1;
    }

    public void promeniRadnik(Radnik r) throws Exception {
        String sql = "UPDATE Radnik SET ime=?, prezime=?, korisnickoIme=?, sifra=?, email=? WHERE idRadnik=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, r.getIme());
        ps.setString(2, r.getPrezime());
        ps.setString(3, r.getKorisnickoIme());
        ps.setString(4, r.getSifra());
        ps.setString(5, r.getEmail());
        ps.setInt(6, r.getIdRadnik());
        ps.executeUpdate();
    }

    public void obrisiRadnik(int idRadnik) throws Exception {
        String sql = "DELETE FROM Radnik WHERE idRadnik = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idRadnik);
        ps.executeUpdate();
    }

    public Radnik pretraziRadnik(int idRadnik) throws Exception {
        String sql = "SELECT * FROM Radnik WHERE idRadnik = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idRadnik);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return mapRadnik(rs);
        return null;
    }

    public List<Radnik> vratiSveRadnike() throws Exception {
        String sql = "SELECT * FROM Radnik ORDER BY prezime, ime";
        ResultSet rs = db.executeQuery(sql);
        List<Radnik> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapRadnik(rs));
        return lista;
    }

    public List<Radnik> pretraziRadnikePoKriterijumu(Radnik kriterijum) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM Radnik WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (kriterijum.getIme() != null && !kriterijum.getIme().isEmpty()) {
            sql.append(" AND ime LIKE ?");
            params.add("%" + kriterijum.getIme() + "%");
        }
        if (kriterijum.getPrezime() != null && !kriterijum.getPrezime().isEmpty()) {
            sql.append(" AND prezime LIKE ?");
            params.add("%" + kriterijum.getPrezime() + "%");
        }
        if (kriterijum.getKorisnickoIme() != null && !kriterijum.getKorisnickoIme().isEmpty()) {
            sql.append(" AND korisnickoIme LIKE ?");
            params.add("%" + kriterijum.getKorisnickoIme() + "%");
        }
        sql.append(" ORDER BY prezime, ime");
        PreparedStatement ps = db.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
        ResultSet rs = ps.executeQuery();
        List<Radnik> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapRadnik(rs));
        return lista;
    }

    public List<Radnik> pretraziRadnikePoStrucnojSpremi(int idStrucnaSprema) throws Exception {
        String sql = "SELECT r.* FROM Radnik r JOIN PrSs p ON r.idRadnik = p.idRadnik WHERE p.idStrucnaSprema = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idStrucnaSprema);
        ResultSet rs = ps.executeQuery();
        List<Radnik> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapRadnik(rs));
        return lista;
    }

    private Radnik mapRadnik(ResultSet rs) throws Exception {
        return new Radnik(
            rs.getInt("idRadnik"),
            rs.getString("ime"),
            rs.getString("prezime"),
            rs.getString("korisnickoIme"),
            rs.getString("sifra"),
            rs.getString("email")
        );
    }
}
