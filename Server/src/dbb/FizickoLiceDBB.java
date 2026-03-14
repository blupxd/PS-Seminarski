package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.FizickoLice;

public class FizickoLiceDBB extends GenericRepository<models.FizickoLice> {

    private final DBBroker db = DBBroker.getInstance();

    public void kreirajFizickoLice(FizickoLice fl) throws Exception {
        KlijentDBB kdb = new KlijentDBB();
        int idKlijent = kdb.kreirajKlijent(fl);
        fl.setIdKlijent(idKlijent);
        ubaciFizickoLice(fl);
    }

    public void ubaciFizickoLice(FizickoLice fl) throws Exception {
        String sql = "INSERT INTO FizickoLice (idKlijent, ime, prezime, JMBG, brojLicneKarte) VALUES (?,?,?,?,?)";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, fl.getIdKlijent());
        ps.setString(2, fl.getIme());
        ps.setString(3, fl.getPrezime());
        ps.setString(4, fl.getJMBG());
        ps.setString(5, fl.getBrojLicneKarte());
        ps.executeUpdate();
    }

    public void promeniFizickoLice(FizickoLice fl) throws Exception {
        KlijentDBB kdb = new KlijentDBB();
        kdb.promeniKlijent(fl);
        String sql = "UPDATE FizickoLice SET ime=?, prezime=?, JMBG=?, brojLicneKarte=? WHERE idKlijent=?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, fl.getIme());
        ps.setString(2, fl.getPrezime());
        ps.setString(3, fl.getJMBG());
        ps.setString(4, fl.getBrojLicneKarte());
        ps.setInt(5, fl.getIdKlijent());
        ps.executeUpdate();
    }

    public void obrisiFizickoLice(int idKlijent) throws Exception {
        String sql = "DELETE FROM FizickoLice WHERE idKlijent = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idKlijent);
        ps.executeUpdate();
    }

    public FizickoLice pretraziFizickoLice(int idKlijent) throws Exception {
        String sql = "SELECT k.*, f.ime, f.prezime, f.JMBG, f.brojLicneKarte "
                   + "FROM Klijent k JOIN FizickoLice f ON k.idKlijent = f.idKlijent "
                   + "WHERE k.idKlijent = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idKlijent);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return mapFizickoLice(rs);
        return null;
    }

    public List<FizickoLice> pretraziFizickoLicePoCriterijumu(FizickoLice kriterijum) throws Exception {
        StringBuilder sql = new StringBuilder(
            "SELECT k.*, f.ime, f.prezime, f.JMBG, f.brojLicneKarte "
          + "FROM Klijent k JOIN FizickoLice f ON k.idKlijent = f.idKlijent WHERE 1=1");
        List<Object> params = new ArrayList<>();
        if (kriterijum.getIme() != null && !kriterijum.getIme().isEmpty()) {
            sql.append(" AND f.ime LIKE ?");
            params.add("%" + kriterijum.getIme() + "%");
        }
        if (kriterijum.getPrezime() != null && !kriterijum.getPrezime().isEmpty()) {
            sql.append(" AND f.prezime LIKE ?");
            params.add("%" + kriterijum.getPrezime() + "%");
        }
        if (kriterijum.getJMBG() != null && !kriterijum.getJMBG().isEmpty()) {
            sql.append(" AND f.JMBG LIKE ?");
            params.add("%" + kriterijum.getJMBG() + "%");
        }
        if (kriterijum.getIdKlijent() > 0) {
            sql.append(" AND k.idKlijent = ?");
            params.add(kriterijum.getIdKlijent());
        }
        PreparedStatement ps = db.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
        ResultSet rs = ps.executeQuery();
        List<FizickoLice> lista = new ArrayList<>();
        while (rs.next()) lista.add(mapFizickoLice(rs));
        return lista;
    }

    private FizickoLice mapFizickoLice(ResultSet rs) throws Exception {
        return new FizickoLice(
            rs.getInt("idKlijent"),
            rs.getString("adresa"),
            rs.getString("telefon"),
            rs.getString("email"),
            rs.getString("ime"),
            rs.getString("prezime"),
            rs.getString("JMBG"),
            rs.getString("brojLicneKarte")
        );
    }
}
