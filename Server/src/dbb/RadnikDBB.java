package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.PrSs;
import models.Radnik;

public class RadnikDBB extends GenericRepository<Radnik> {

    private final DBBroker db = DBBroker.getInstance();

    public Radnik prijaviRadnik(String korisnickoIme, String sifra) throws Exception {
        String sql = "SELECT * FROM Radnik WHERE korisnickoIme = ? AND sifra = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, korisnickoIme);
        ps.setString(2, sifra);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return (Radnik) new Radnik().fromResultSet(rs);
        }
        return null;
    }

    public int kreirajRadnik(Radnik r) throws Exception {
        return super.create(r).getIdRadnik();
    }

    public void promeniRadnik(Radnik r) throws Exception {
        super.update(r);
    }

    public void obrisiRadnik(int idRadnik) throws Exception {
        Radnik r = new Radnik();
        r.setIdRadnik(idRadnik);
        super.delete(r);
    }

    public Radnik pretraziRadnik(int idRadnik) throws Exception {
        Radnik r = new Radnik();
        r.setIdRadnik(idRadnik);
        return super.getByPK(r);
    }

    public List<Radnik> vratiSveRadnike() throws Exception {
        String sql = "SELECT * FROM Radnik ORDER BY prezime, ime";
        ResultSet rs = db.executeQuery(sql);
        List<Radnik> lista = new ArrayList<>();
        while (rs.next()) lista.add((Radnik) new Radnik().fromResultSet(rs));
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
        while (rs.next()) lista.add((Radnik) new Radnik().fromResultSet(rs));
        return lista;
    }

    public List<Radnik> pretraziRadnikePoStrucnojSpremi(int idStrucnaSprema) throws Exception {
        String sql = "SELECT r.* FROM Radnik r JOIN PrSs p ON r.idRadnik = p.idRadnik WHERE p.idStrucnaSprema = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idStrucnaSprema);
        ResultSet rs = ps.executeQuery();
        List<Radnik> lista = new ArrayList<>();
        while (rs.next()) lista.add((Radnik) new Radnik().fromResultSet(rs));
        return lista;
    }
}
