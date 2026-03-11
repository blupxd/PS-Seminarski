package tablemodels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.FizickoLice;

public class FizickoLiceTableModel extends AbstractTableModel {

    private final String[] kolone = {"ID klijenta", "Ime", "Prezime", "JMBG", "Br. lične karte", "Adresa", "Telefon", "Email"};
    private List<FizickoLice> lista;

    public FizickoLiceTableModel() {
        this.lista = new ArrayList<>();
    }

    public void setLista(List<FizickoLice> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    public FizickoLice getFizickoLiceAt(int row) {
        return lista.get(row);
    }

    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return kolone.length; }
    @Override public String getColumnName(int col) { return kolone[col]; }

    @Override
    public Object getValueAt(int row, int col) {
        FizickoLice fl = lista.get(row);
        switch (col) {
            case 0: return fl.getIdKlijent();
            case 1: return fl.getIme();
            case 2: return fl.getPrezime();
            case 3: return fl.getJMBG();
            case 4: return fl.getBrojLicneKarte();
            case 5: return fl.getAdresa();
            case 6: return fl.getTelefon();
            case 7: return fl.getEmail();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col) { return false; }
}
