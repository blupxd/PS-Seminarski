package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import models.Klijent;
import models.Oprema;
import models.Radnik;
import models.StavkaUgovora;
import models.Ugovor;

public class UgovorDBB extends GenericRepository<Ugovor> {

    private final DBBroker db = DBBroker.getInstance();

    public int kreirajUgovor(Ugovor u) throws Exception {
        if (u.getStatus() == null) u.setStatus(Ugovor.STATUS_AKTIVAN);
        return super.create(u).getIdUgovor();
    }

    public void promeniUgovor(Ugovor u) throws Exception {
        super.update(u);
    }

    public Ugovor pretraziUgovor(int idUgovor) throws Exception {
        String sql = "SELECT u.*, r.ime AS rIme, r.prezime AS rPrezime "
                   + "FROM Ugovor u JOIN Radnik r ON u.idRadnik = r.idRadnik "
                   + "WHERE u.idUgovor = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idUgovor);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Ugovor u = mapUgovor(rs);
            Radnik radnik = new Radnik();
            radnik.setIdRadnik(rs.getInt("idRadnik"));
            radnik.setIme(rs.getString("rIme"));
            radnik.setPrezime(rs.getString("rPrezime"));
            u.setRadnik(radnik);
            u.setStavke(vratiStavkeZaUgovor(idUgovor));
            return u;
        }
        return null;
    }

    public List<Ugovor> vratiListuUgovoraPoCriterijumu(Ugovor kriterijum) throws Exception {
        StringBuilder sql = new StringBuilder(
            "SELECT u.*, r.ime AS rIme, r.prezime AS rPrezime "
          + "FROM Ugovor u JOIN Radnik r ON u.idRadnik = r.idRadnik "
          + "LEFT JOIN Klijent k ON u.idKlijent = k.idKlijent WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (kriterijum.getIdUgovor() > 0) {
            sql.append(" AND u.idUgovor = ?");
            params.add(kriterijum.getIdUgovor());
        }
        if (kriterijum.getStatus() != null && !kriterijum.getStatus().isEmpty()) {
            sql.append(" AND u.status = ?");
            params.add(kriterijum.getStatus());
        }
        if (kriterijum.getIdRadnik() > 0) {
            sql.append(" AND u.idRadnik = ?");
            params.add(kriterijum.getIdRadnik());
        }
        if (kriterijum.getIdKlijent() > 0) {
            sql.append(" AND u.idKlijent = ?");
            params.add(kriterijum.getIdKlijent());
        }
        if (kriterijum.getDatumIzdavanja() != null) {
            sql.append(" AND u.datumIzdavanja >= ?");
            params.add(new java.sql.Date(kriterijum.getDatumIzdavanja().getTime()));
        }
        if (kriterijum.getDatumVracanjaPlaniran() != null) {
            sql.append(" AND u.datumVracanjaPlaniran <= ?");
            params.add(new java.sql.Date(kriterijum.getDatumVracanjaPlaniran().getTime()));
        }
        sql.append(" ORDER BY u.datumIzdavanja DESC");

        PreparedStatement ps = db.prepareStatement(sql.toString());
        for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
        ResultSet rs = ps.executeQuery();
        List<Ugovor> lista = new ArrayList<>();
        while (rs.next()) {
            Ugovor u = mapUgovor(rs);
            Radnik radnik = new Radnik();
            radnik.setIdRadnik(rs.getInt("idRadnik"));
            radnik.setIme(rs.getString("rIme"));
            radnik.setPrezime(rs.getString("rPrezime"));
            u.setRadnik(radnik);
            lista.add(u);
        }
        return lista;
    }

    public List<Ugovor> vratiListuUgovoraPoOpremi(int idOprema) throws Exception {
        String sql = "SELECT DISTINCT u.*, r.ime AS rIme, r.prezime AS rPrezime "
                   + "FROM Ugovor u JOIN Radnik r ON u.idRadnik = r.idRadnik "
                   + "JOIN StavkaUgovora s ON u.idUgovor = s.idUgovor "
                   + "WHERE s.idOprema = ? ORDER BY u.datumIzdavanja DESC";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idOprema);
        ResultSet rs = ps.executeQuery();
        List<Ugovor> lista = new ArrayList<>();
        while (rs.next()) {
            Ugovor u = mapUgovor(rs);
            Radnik radnik = new Radnik();
            radnik.setIdRadnik(rs.getInt("idRadnik"));
            radnik.setIme(rs.getString("rIme"));
            radnik.setPrezime(rs.getString("rPrezime"));
            u.setRadnik(radnik);
            lista.add(u);
        }
        return lista;
    }

    public List<StavkaUgovora> vratiStavkeZaUgovor(int idUgovor) throws Exception {
        String sql = "SELECT s.*, o.naziv AS oNaziv, o.serijskiBroj, o.cenaPoDanu, o.kolicinaDostupna AS oKolicina "
                   + "FROM StavkaUgovora s JOIN Oprema o ON s.idOprema = o.idOprema "
                   + "WHERE s.idUgovor = ? ORDER BY s.rb";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idUgovor);
        ResultSet rs = ps.executeQuery();
        List<StavkaUgovora> lista = new ArrayList<>();
        while (rs.next()) {
            StavkaUgovora stavka = new StavkaUgovora(
                rs.getInt("idUgovor"),
                rs.getInt("rb"),
                rs.getInt("kolicina"),
                rs.getDouble("iznos"),
                rs.getInt("idOprema")
            );
            Oprema o = new Oprema();
            o.setIdOprema(rs.getInt("idOprema"));
            o.setNaziv(rs.getString("oNaziv"));
            o.setSerijskiBroj(rs.getString("serijskiBroj"));
            o.setCenaPoDanu(rs.getDouble("cenaPoDanu"));
            o.setKolicinaDostupna(rs.getInt("oKolicina"));
            stavka.setOprema(o);
            lista.add(stavka);
        }
        return lista;
    }

    public void kreirajStavku(StavkaUgovora stavka) throws Exception {
        String sql = "INSERT INTO StavkaUgovora (idUgovor, rb, kolicina, iznos, idOprema) "
                   + "VALUES (?,?,?,?,?)";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, stavka.getIdUgovor());
        ps.setInt(2, stavka.getRb());
        ps.setInt(3, stavka.getKolicina());
        ps.setDouble(4, stavka.getIznos());
        ps.setInt(5, stavka.getIdOprema());
        ps.executeUpdate();
    }

    public void obrisiStavkeZaUgovor(int idUgovor) throws Exception {
        String sql = "DELETE FROM StavkaUgovora WHERE idUgovor = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idUgovor);
        ps.executeUpdate();
    }

    public int vratiSlobodnuKolicinu(int idOprema, java.util.Date datumOd, java.util.Date datumDo, int izuzmiUgovor) throws Exception {
        String sql = "SELECT o.kolicinaDostupna - IFNULL("
                   + "  (SELECT SUM(s.kolicina) FROM StavkaUgovora s "
                   + "   JOIN Ugovor u ON s.idUgovor = u.idUgovor "
                   + "   WHERE s.idOprema = ? "
                   + "     AND u.status NOT IN ('Storniran') "
                   + "     AND u.idUgovor != ? "
                   + "     AND u.datumIzdavanja <= ? "
                   + "     AND COALESCE(u.datumVracanjaStvarni, u.datumVracanjaPlaniran) >= ?)"
                   + ", 0) AS kolicinaSlobodna "
                   + "FROM Oprema o WHERE o.idOprema = ?";
        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, idOprema);
        ps.setInt(2, izuzmiUgovor);
        ps.setDate(3, new java.sql.Date(datumDo.getTime()));
        ps.setDate(4, new java.sql.Date(datumOd.getTime()));
        ps.setInt(5, idOprema);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt("kolicinaSlobodna");
        return 0;
    }

    private Ugovor mapUgovor(ResultSet rs) throws Exception {
        Date datumVracanjaStvarni = rs.getDate("datumVracanjaStvarni");
        return new Ugovor(
            rs.getInt("idUgovor"),
            rs.getDate("datumIzdavanja"),
            rs.getInt("popust"),
            rs.getDate("datumVracanjaPlaniran"),
            datumVracanjaStvarni,
            rs.getDouble("ukupanIznos"),
            rs.getString("status"),
            rs.getInt("idRadnik"),
            rs.getInt("idKlijent")
        );
    }
}
