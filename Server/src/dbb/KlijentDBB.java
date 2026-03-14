package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Klijent;

public class KlijentDBB extends GenericRepository<Klijent> {

    private final DBBroker db = DBBroker.getInstance();

    public int kreirajKlijent(Klijent k) throws Exception {
        return super.create(k).getIdKlijent();
    }

    public void promeniKlijent(Klijent k) throws Exception {
        super.update(k);
    }

    public void obrisiKlijent(int idKlijent) throws Exception {
        Klijent k = new Klijent();
        k.setIdKlijent(idKlijent);
        super.delete(k);
    }

    public Klijent pretraziKlijent(int idKlijent) throws Exception {
        Klijent k = new Klijent();
        k.setIdKlijent(idKlijent);
        return super.getByPK(k);
    }

    public List<Klijent> vratiSveKlijente() throws Exception {
        ResultSet rs = db.executeQuery("SELECT * FROM Klijent ORDER BY idKlijent");
        List<Klijent> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add((Klijent) new Klijent().fromResultSet(rs));
        }
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
        while (rs.next()) lista.add((Klijent) new Klijent().fromResultSet(rs));
        return lista;
    }
}
