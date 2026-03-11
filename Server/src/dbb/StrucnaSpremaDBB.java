package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.StrucnaSprema;

public class StrucnaSpremaDBB {

    private final DBBroker db = DBBroker.getInstance();

    public int kreirajStrucnaSprema(StrucnaSprema ss) throws Exception {
        String sql = "INSERT INTO StrucnaSprema (nazivSpreme, stepen) VALUES (?,?)";
        PreparedStatement ps = db.prepareStatementWithKeys(sql);
        ps.setString(1, ss.getNazivSpreme());
        ps.setString(2, ss.getStepen());
        ps.executeUpdate();
        ResultSet keys = ps.getGeneratedKeys();
        if (keys.next()) return keys.getInt(1);
        return -1;
    }

    public void promeniStrucnaSprema(StrucnaSprema ss) throws Exception {
        String sql = "UPDATE StrucnaSprema SET nazivSpreme=?, stepen=? WHERE idStrucnaSprema=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, ss.getNazivSpreme());
        ps.setString(2, ss.getStepen());
        ps.setInt(3, ss.getIdStrucnaSprema());
        ps.executeUpdate();
    }

    public StrucnaSprema pretraziStrucnaSprema(int id) throws Exception {
        String sql = "SELECT * FROM StrucnaSprema WHERE idStrucnaSprema = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return mapSS(rs);
        return null;
    }

    public List<StrucnaSprema> vratiSveStrucneSprema() throws Exception {
        ResultSet rs = db.executeQuery("SELECT * FROM StrucnaSprema ORDER BY nazivSpreme");
        List<StrucnaSprema> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapSS(rs));
        return lista;
    }

    public List<StrucnaSprema> pretraziStrucneSprema(StrucnaSprema kriterijum) throws Exception {
        StringBuilder sql = new StringBuilder("SELECT * FROM StrucnaSprema WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (kriterijum.getNazivSpreme() != null && !kriterijum.getNazivSpreme().isEmpty()) {
            sql.append(" AND nazivSpreme LIKE ?");
            params.add("%" + kriterijum.getNazivSpreme() + "%");
        }
        if (kriterijum.getStepen() != null && !kriterijum.getStepen().isEmpty()) {
            sql.append(" AND stepen = ?");
            params.add(kriterijum.getStepen());
        }
        PreparedStatement ps = db.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
        ResultSet rs = ps.executeQuery();
        List<StrucnaSprema> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapSS(rs));
        return lista;
    }

    public void obrisiStrucnaSprema(int idStrucnaSprema) throws Exception {
        String sql = "DELETE FROM StrucnaSprema WHERE idStrucnaSprema = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idStrucnaSprema);
        ps.executeUpdate();
    }

    private StrucnaSprema mapSS(ResultSet rs) throws Exception {
        return new StrucnaSprema(
            rs.getInt("idStrucnaSprema"),
            rs.getString("nazivSpreme"),
            rs.getString("stepen")
        );
    }
}
