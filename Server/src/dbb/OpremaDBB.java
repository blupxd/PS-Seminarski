package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Oprema;

public class OpremaDBB {

    private final DBBroker db = DBBroker.getInstance();

    public int kreirajOprema(Oprema o) throws Exception {
        String sql = "INSERT INTO Oprema (naziv, serijskiBroj, opis, cenaPoDanu, kolicinaDostupna) VALUES (?,?,?,?,?)";
        PreparedStatement ps = db.prepareStatementWithKeys(sql);
        ps.setString(1, o.getNaziv());
        ps.setString(2, o.getSerijskiBroj());
        ps.setString(3, o.getOpis());
        ps.setDouble(4, o.getCenaPoDanu());
        ps.setInt(5, o.getKolicinaDostupna() > 0 ? o.getKolicinaDostupna() : 1);
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) return keys.getInt(1);
        return -1;
    }

    public void promeniOprema(Oprema o) throws Exception {
        String sql = "UPDATE Oprema SET naziv=?, serijskiBroj=?, opis=?, cenaPoDanu=?, kolicinaDostupna=? WHERE idOprema=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, o.getNaziv());
        ps.setString(2, o.getSerijskiBroj());
        ps.setString(3, o.getOpis());
        ps.setDouble(4, o.getCenaPoDanu());
        ps.setInt(5, o.getKolicinaDostupna() > 0 ? o.getKolicinaDostupna() : 1);
        ps.setInt(6, o.getIdOprema());
        ps.executeUpdate();
    }

    public void obrisiOprema(int idOprema) throws Exception {
        String sql = "DELETE FROM Oprema WHERE idOprema = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idOprema);
        ps.executeUpdate();
    }

    public Oprema pretraziOprema(int idOprema) throws Exception {
        String sql = "SELECT * FROM Oprema WHERE idOprema = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idOprema);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return mapOprema(rs);
        return null;
    }

    public List<Oprema> vratiSvuOpremu() throws Exception {
        ResultSet rs = db.executeQuery("SELECT * FROM Oprema ORDER BY naziv");
        List<Oprema> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapOprema(rs));
        return lista;
    }

    public List<Oprema> pretraziOpremaPoCriterijumu(Oprema kriterijum) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM Oprema WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (kriterijum.getNaziv() != null && !kriterijum.getNaziv().isEmpty()) {
            sql.append(" AND naziv LIKE ?");
            params.add("%" + kriterijum.getNaziv() + "%");
        }
        if (kriterijum.getSerijskiBroj() != null && !kriterijum.getSerijskiBroj().isEmpty()) {
            sql.append(" AND serijskiBroj LIKE ?");
            params.add("%" + kriterijum.getSerijskiBroj() + "%");
        }
        sql.append(" ORDER BY naziv");
        PreparedStatement ps = db.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
        ResultSet rs = ps.executeQuery();
        List<Oprema> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapOprema(rs));
        return lista;
    }

    private Oprema mapOprema(ResultSet rs) throws Exception {
        return new Oprema(
            rs.getInt("idOprema"),
            rs.getString("naziv"),
            rs.getString("serijskiBroj"),
            rs.getString("opis"),
            rs.getDouble("cenaPoDanu"),
            rs.getInt("kolicinaDostupna")
        );
    }
}
