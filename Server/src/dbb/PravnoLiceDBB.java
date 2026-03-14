package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.PravnoLice;

public class PravnoLiceDBB extends GenericRepository<PravnoLice> {

    private final DBBroker db = DBBroker.getInstance();

    public void kreirajPravnoLice(PravnoLice pl) throws Exception {
        //  kreira zapsi u klijent  pa u pravno lice
        KlijentDBB kdb = new KlijentDBB();
        int idKlijent = kdb.kreirajKlijent(pl);
        pl.setIdKlijent(idKlijent);
        ubaciPravnoLice(pl);
    }

    public void ubaciPravnoLice(PravnoLice pl) throws Exception {
        String sql = "INSERT INTO PravnoLice (idKlijent, nazivFirme, PIB, maticniBroj) VALUES (?,?,?,?)";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, pl.getIdKlijent());
        ps.setString(2, pl.getNazivFirme());
        ps.setString(3, pl.getPIB());
        ps.setString(4, pl.getMaticniBroj());
        ps.executeUpdate();
    }

    public void promeniPravnoLice(PravnoLice pl) throws Exception {
        KlijentDBB kdb = new KlijentDBB();
        kdb.promeniKlijent(pl);
        String sql = "UPDATE PravnoLice SET nazivFirme=?, PIB=?, maticniBroj=? WHERE idKlijent=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, pl.getNazivFirme());
        ps.setString(2, pl.getPIB());
        ps.setString(3, pl.getMaticniBroj());
        ps.setInt(4, pl.getIdKlijent());
        ps.executeUpdate();
    }

    public void obrisiPravnoLice(int idKlijent) throws Exception {
        String sql = "DELETE FROM PravnoLice WHERE idKlijent = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idKlijent);
        ps.executeUpdate();
    }

    public PravnoLice pretraziPravnoLice(int idKlijent) throws Exception {
        String sql = "SELECT k.*, p.nazivFirme, p.PIB, p.maticniBroj "
                   + "FROM Klijent k JOIN PravnoLice p ON k.idKlijent = p.idKlijent "
                   + "WHERE k.idKlijent = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idKlijent);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return mapPravnoLice(rs);
        return null;
    }

    public List<PravnoLice> pretraziPravnoLicePoCriterijumu(PravnoLice kriterijum) throws Exception {
        StringBuilder sql = new StringBuilder(
            "SELECT k.*, p.nazivFirme, p.PIB, p.maticniBroj "
          + "FROM Klijent k JOIN PravnoLice p ON k.idKlijent = p.idKlijent WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (kriterijum.getNazivFirme() != null && !kriterijum.getNazivFirme().isEmpty()) {
            sql.append(" AND p.nazivFirme LIKE ?");
            params.add("%" + kriterijum.getNazivFirme() + "%");
        }
        if (kriterijum.getPIB() != null && !kriterijum.getPIB().isEmpty()) {
            sql.append(" AND p.PIB LIKE ?");
            params.add("%" + kriterijum.getPIB() + "%");
        }
        if (kriterijum.getIdKlijent() > 0) {
            sql.append(" AND k.idKlijent = ?");
            params.add(kriterijum.getIdKlijent());
        }
        PreparedStatement ps = db.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
        ResultSet rs = ps.executeQuery();
        List<PravnoLice> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapPravnoLice(rs));
        return lista;
    }

    private PravnoLice mapPravnoLice(ResultSet rs) throws Exception {
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
}
