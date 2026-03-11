package tablemodels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.StrucnaSprema;

public class StrucnaSpremaTableModel extends AbstractTableModel {

    private final String[] kolone = {"ID", "Naziv", "Stepen"};
    private List<StrucnaSprema> lista;

    public StrucnaSpremaTableModel() {
        this.lista = new ArrayList<>();
    }

    public void setLista(List<StrucnaSprema> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    public StrucnaSprema getStrucnaSpremaAt(int row) {
        return lista.get(row);
    }

    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return kolone.length; }
    @Override public String getColumnName(int col) { return kolone[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        StrucnaSprema s = lista.get(row);
        switch (col) {
            case 0: return s.getIdStrucnaSprema();
            case 1: return s.getNazivSpreme();
            case 2: return s.getStepen();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) { return false; }
}
