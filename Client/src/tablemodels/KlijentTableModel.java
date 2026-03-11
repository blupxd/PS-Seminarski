package tablemodels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.Klijent;

public class KlijentTableModel extends AbstractTableModel {

    private final String[] kolone = {"ID", "Adresa", "Telefon", "Email"};
    private List<Klijent> lista;

    public KlijentTableModel() {
        this.lista = new ArrayList<>();
    }

    public void setLista(List<Klijent> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    public Klijent getKlijentAt(int row) {
        return lista.get(row);
    }

    public List<Klijent> getLista() {
        return lista;
    }

    public void addKlijent(Klijent k) {
        lista.add(k);
        fireTableRowsInserted(lista.size() - 1, lista.size() - 1);
    }

    public void updateKlijent(int row, Klijent k) {
        lista.set(row, k);
        fireTableRowsUpdated(row, row);
    }

    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return kolone.length; }
    @Override public String getColumnName(int col) { return kolone[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Klijent k = lista.get(row);
        switch (col) {
            case 0: return k.getIdKlijent();
            case 1: return k.getAdresa();
            case 2: return k.getTelefon();
            case 3: return k.getEmail();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) { return false; }
}
