package dbb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.StrucnaSprema;

public class StrucnaSpremaDBB extends GenericRepository<StrucnaSprema> {

    private final DBBroker db = DBBroker.getInstance();

    public int kreirajStrucnaSprema(StrucnaSprema ss) throws Exception {
        return super.create(ss).getIdStrucnaSprema();
    }

    public void promeniStrucnaSprema(StrucnaSprema ss) throws Exception {
        super.update(ss);
    }

    public StrucnaSprema pretraziStrucnaSprema(int id) throws Exception {
        StrucnaSprema ss = new StrucnaSprema();
        ss.setIdStrucnaSprema(id);
        return super.getByPK(ss);
    }

    public List<StrucnaSprema> vratiSveStrucneSprema() throws Exception {
        ResultSet rs = db.executeQuery("SELECT * FROM StrucnaSprema ORDER BY nazivSpreme");
        List<StrucnaSprema> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add((StrucnaSprema) new StrucnaSprema().fromResultSet(rs));
        }
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
        while (rs.next()) {
            lista.add((StrucnaSprema) new StrucnaSprema().fromResultSet(rs));
        }
        return lista;
    }

    public void obrisiStrucnaSprema(int idStrucnaSprema) throws Exception {
        StrucnaSprema ss = new StrucnaSprema();
        ss.setIdStrucnaSprema(idStrucnaSprema);
        super.delete(ss);
    }
}
