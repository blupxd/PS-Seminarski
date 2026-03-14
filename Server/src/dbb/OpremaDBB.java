package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Oprema;

public class OpremaDBB extends GenericRepository<Oprema> {

    private final DBBroker db = DBBroker.getInstance();

    public int kreirajOprema(Oprema o) throws Exception {
        if (o.getKolicinaDostupna() <= 0) o.setKolicinaDostupna(1);
        return super.create(o).getIdOprema();
    }

    public void promeniOprema(Oprema o) throws Exception {
        if (o.getKolicinaDostupna() <= 0) o.setKolicinaDostupna(1);
        super.update(o);
    }

    public void obrisiOprema(int idOprema) throws Exception {
        Oprema o = new Oprema();
        o.setIdOprema(idOprema);
        super.delete(o);
    }

    public Oprema pretraziOprema(int idOprema) throws Exception {
        Oprema o = new Oprema();
        o.setIdOprema(idOprema);
        return super.getByPK(o);
    }

    public List<Oprema> vratiSvuOpremu() throws Exception {
        ResultSet rs = db.executeQuery("SELECT * FROM Oprema ORDER BY naziv");
        List<Oprema> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add((Oprema) new Oprema().fromResultSet(rs));
        }
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
        while (rs.next()) {
            lista.add((Oprema) new Oprema().fromResultSet(rs));
        }
        return lista;
    }
}
