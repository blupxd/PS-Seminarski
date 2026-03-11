package tablemodels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.StavkaUgovora;

public class StavkaUgovoraTableModel extends AbstractTableModel {

    private final String[] kolone = {"RB", "Oprema", "Količina", "Iznos"};
    private List<StavkaUgovora> lista;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public StavkaUgovoraTableModel() {
        this.lista = new ArrayList<>();
    }

    public void setLista(List<StavkaUgovora> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    public List<StavkaUgovora> getLista() {
        return lista;
    }

    public StavkaUgovora getStavkaAt(int row) {
        return lista.get(row);
    }

    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return kolone.length; }
    @Override public String getColumnName(int col) { return kolone[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        StavkaUgovora s = lista.get(row);
        switch (col) {
            case 0: return s.getRb();
            case 1: return s.getOprema() != null ? s.getOprema().getNaziv() : "ID:" + s.getIdOprema();
            case 2: return s.getKolicina();
            case 3: return s.getIznos();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) { return false; }
}
