package tablemodels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.Radnik;

public class RadnikTableModel extends AbstractTableModel {

    private final String[] kolone = {"ID", "Ime", "Prezime", "Kor. ime", "Email"};
    private List<Radnik> lista;

    public RadnikTableModel() {
        this.lista = new ArrayList<>();
    }

    public void setLista(List<Radnik> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    public Radnik getRadnikAt(int row) {
        return lista.get(row);
    }

    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return kolone.length; }
    @Override public String getColumnName(int col) { return kolone[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        Radnik r = lista.get(row);
        switch (col) {
            case 0: return r.getIdRadnik();
            case 1: return r.getIme();
            case 2: return r.getPrezime();
            case 3: return r.getKorisnickoIme();
            case 4: return r.getEmail();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) { return false; }
}
