package tablemodels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.PravnoLice;

public class PravnoLiceTableModel extends AbstractTableModel {

    private final String[] kolone = {"ID klijenta", "Naziv firme", "PIB", "Maticni br.", "Adresa", "Telefon", "Email"};
    private List<PravnoLice> lista;

    public PravnoLiceTableModel() {
        this.lista = new ArrayList<>();
    }

    public void setLista(List<PravnoLice> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    public PravnoLice getPravnoLiceAt(int row) {
        return lista.get(row);
    }

    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return kolone.length; }
    @Override public String getColumnName(int col) { return kolone[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        PravnoLice pl = lista.get(row);
        switch (col) {
            case 0: return pl.getIdKlijent();
            case 1: return pl.getNazivFirme();
            case 2: return pl.getPIB();
            case 3: return pl.getMaticniBroj();
            case 4: return pl.getAdresa();
            case 5: return pl.getTelefon();
            case 6: return pl.getEmail();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) { return false; }
}
