package tablemodels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.Oprema;

public class OpremaTableModel extends AbstractTableModel {

    private final String[] kolone = {"ID", "Naziv", "Serijski br.", "Cena/dan", "Kolicina"};
    private List<Oprema> lista;

    public OpremaTableModel() {
        this.lista = new ArrayList<>();
    }

    public void setLista(List<Oprema> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    public Oprema getOpremaAt(int row) {
        return lista.get(row);
    }

    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return kolone.length; }
    @Override public String getColumnName(int col) { return kolone[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Oprema o = lista.get(row);
        switch (col) {
            case 0: return o.getIdOprema();
            case 1: return o.getNaziv();
            case 2: return o.getSerijskiBroj();
            case 3: return o.getCenaPoDanu();
            case 4: return o.getKolicinaDostupna();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) { return false; }
}
