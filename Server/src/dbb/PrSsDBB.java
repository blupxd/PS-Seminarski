package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.PrSs;
import models.StrucnaSprema;

public class PrSsDBB {

    private final DBBroker db = DBBroker.getInstance();

    public void ubaciPrSs(PrSs prss) throws Exception {
        String sql = "INSERT INTO PrSs (idRadnik, idStrucnaSprema, datumSticanja) VALUES (?,?,?)";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, prss.getIdRadnik());
        ps.setInt(2, prss.getIdStrucnaSprema());
        ps.setDate(3, new java.sql.Date(prss.getDatumSticanja().getTime()));
        ps.executeUpdate();
    }

    public void obrisiPrSs(int idRadnik, int idStrucnaSprema) throws Exception {
        String sql = "DELETE FROM PrSs WHERE idRadnik = ? AND idStrucnaSprema = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idRadnik);
        ps.setInt(2, idStrucnaSprema);
        ps.executeUpdate();
    }

    public List<PrSs> vratiPrSsZaRadnika(int idRadnik) throws Exception {
        String sql = "SELECT p.*, s.nazivSpreme, s.stepen FROM PrSs p "
                   + "JOIN StrucnaSprema s ON p.idStrucnaSprema = s.idStrucnaSprema "
                   + "WHERE p.idRadnik = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idRadnik);
        ResultSet rs = ps.executeQuery();
        List<PrSs> lista = new ArrayList<>();
        while (rs.next()) {
            PrSs prss = new PrSs(
                rs.getInt("idRadnik"),
                rs.getInt("idStrucnaSprema"),
                rs.getDate("datumSticanja")
            );
            StrucnaSprema ss = new StrucnaSprema(
                rs.getInt("idStrucnaSprema"),
                rs.getString("nazivSpreme"),
                rs.getString("stepen")
            );
            prss.setStrucnaSprema(ss);
            lista.add(prss);
        }
        return lista;
    }
}
