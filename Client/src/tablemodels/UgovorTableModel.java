package tablemodels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.Ugovor;

public class UgovorTableModel extends AbstractTableModel {

    private final String[] kolone = {"ID", "Datum izdavanja", "Klijent", "Status", "Popust", "Ukupno"};
    private List<Ugovor> lista;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public UgovorTableModel() {
        this.lista = new ArrayList<>();
    }

    public void setLista(List<Ugovor> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    public Ugovor getUgovorAt(int row) {
        return lista.get(row);
    }

    public List<Ugovor> getLista() {
        return lista;
    }

    public void addUgovor(Ugovor u) {
        lista.add(u);
        fireTableRowsInserted(lista.size() - 1, lista.size() - 1);
    }

    public void updateUgovor(int row, Ugovor u) {
        lista.set(row, u);
        fireTableRowsUpdated(row, row);
    }

    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return kolone.length; }
    @Override public String getColumnName(int col) { return kolone[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Ugovor u = lista.get(row);
        switch (col) {
            case 0: return u.getIdUgovor();
            case 1: return u.getDatumIzdavanja() != null ? sdf.format(u.getDatumIzdavanja()) : "";
            case 2: return u.getIdKlijent();
            case 3: return u.getStatus();
            case 4: return u.getPopust();
            case 5: return u.getUkupanIznos();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) { return false; }
}
