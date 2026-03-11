/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package forms;

import controllers.ClientController;
import java.util.ArrayList;
import java.util.List;
import models.Klijent;
import models.Oprema;
import models.Radnik;
import models.StavkaUgovora;
import models.Ugovor;
import operations.Operation;
import operations.OperationType;
import tablemodels.StavkaUgovoraTableModel;
import tablemodels.UgovorTableModel;

/**
 *
 * @author matij
 */
public class UgovorForm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UgovorForm.class.getName());

    /**
     * Creates new form UgovorForm
     */
    private Radnik radnik;
    private UgovorTableModel tableModelUgovori;
    private StavkaUgovoraTableModel tableModelStavke;
    private String originalniStatus = null;
    private java.util.Set<Integer> izmenjeniIds = new java.util.HashSet<>();

    public UgovorForm(Radnik radnik) {
        this.radnik = radnik;
        initComponents();
        setTitle("Ugovori");
        // Spinner format
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy");
        for (javax.swing.JSpinner sp : new javax.swing.JSpinner[]{datDatumIzdavanja, datDatumPlaniran, datDatumStvaran}) {
            sp.setModel(new javax.swing.SpinnerDateModel());
            sp.setEditor(new javax.swing.JSpinner.DateEditor(sp, "dd.MM.yyyy"));
        }
        // Status
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            Ugovor.STATUS_AKTIVAN, Ugovor.STATUS_ZAVRSEN, Ugovor.STATUS_STORNIRAN
        }));
        // Tabela ugovora
        tableModelUgovori = new UgovorTableModel();
        tblUgovori.setModel(tableModelUgovori);
        tblUgovori.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUgovori.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                ucitajIzabraniUgovor();
            }
        });
        // Tabela stavki
        tableModelStavke = new StavkaUgovoraTableModel();
        tblStavke.setModel(tableModelStavke);
        tblStavke.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        // ComboBox klijent i oprema
        ucitajKlijente();
        ucitajOpremu();
        txtIdUgovor.setEditable(false);
        datDatumStvaran.setEnabled(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cmbOprema.addActionListener(e -> azurirajSlobodnuKolicinu());
        datDatumIzdavanja.addChangeListener(e -> azurirajSlobodnuKolicinu());
        datDatumPlaniran.addChangeListener(e -> azurirajSlobodnuKolicinu());
        ucitajSveUgovore();
        azurirajSlobodnuKolicinu();
    }

    private void ucitajSveUgovore() {
        try {
            Ugovor kriterijum = new Ugovor();
            List<Ugovor> lista = ClientController.getInstance().vratiUgovorePoKlijentu(kriterijum);
            tableModelUgovori.setLista(lista);
        } catch (Exception e) {
            tableModelUgovori.setLista(new ArrayList<>());
            javax.swing.JOptionPane.showMessageDialog(this, "Greška pri učitavanju ugovora: " + e.getMessage());
        }
    }

    private void ucitajKlijente() {
        try {
            List<Klijent> lista = ClientController.getInstance().vratiSveKlijente();
            cmbKlijent.removeAllItems();
            for (Klijent k : lista) {
                cmbKlijent.addItem(k);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Greška pri učitavanju klijenata: " + e.getMessage());
        }
    }

    private void ucitajOpremu() {
        try {
            List<Oprema> lista = ClientController.getInstance().vratiSvuOpremu();
            cmbOprema.removeAllItems();
            for (Oprema o : lista) {
                cmbOprema.addItem(o);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Greška pri učitavanju opreme: " + e.getMessage());
        }
    }

    private void ucitajIzabraniUgovor() {
        int row = tblUgovori.getSelectedRow();
        if (row < 0) {
            return;
        }
        Ugovor uLocal = tableModelUgovori.getUgovorAt(row);
        if (uLocal.getIdUgovor() == 0) {
            // Lokalno kreiran ugovor, još nije sačuvan
            txtIdUgovor.setText("");
            datDatumIzdavanja.setValue(uLocal.getDatumIzdavanja() != null ? uLocal.getDatumIzdavanja() : new java.util.Date());
            datDatumPlaniran.setValue(uLocal.getDatumVracanjaPlaniran() != null ? uLocal.getDatumVracanjaPlaniran() : new java.util.Date());
            datDatumStvaran.setValue(new java.util.Date());
            txtPopust.setText(String.valueOf(uLocal.getPopust()));
            originalniStatus = uLocal.getStatus();
            cmbStatus.setSelectedItem(uLocal.getStatus());
            for (int i = 0; i < cmbKlijent.getItemCount(); i++) {
                if (cmbKlijent.getItemAt(i).getIdKlijent() == uLocal.getIdKlijent()) {
                    cmbKlijent.setSelectedIndex(i); break;
                }
            }
            tableModelStavke.setLista(uLocal.getStavke() != null ? uLocal.getStavke() : new ArrayList<>());
            azurirajUkupanIznos();
            javax.swing.JOptionPane.showMessageDialog(this, "Систем је нашао уговор");
            return;
        }
        int id = uLocal.getIdUgovor();
        if (izmenjeniIds.contains(id)) {
            // Lokalno izmenjen ugovor, ucitaj iz tabele
            txtIdUgovor.setText(String.valueOf(uLocal.getIdUgovor()));
            datDatumIzdavanja.setValue(uLocal.getDatumIzdavanja() != null ? uLocal.getDatumIzdavanja() : new java.util.Date());
            datDatumPlaniran.setValue(uLocal.getDatumVracanjaPlaniran() != null ? uLocal.getDatumVracanjaPlaniran() : new java.util.Date());
            datDatumStvaran.setValue(uLocal.getDatumVracanjaStvarni() != null ? uLocal.getDatumVracanjaStvarni() : new java.util.Date());
            txtPopust.setText(String.valueOf(uLocal.getPopust()));
            originalniStatus = uLocal.getStatus();
            cmbStatus.setSelectedItem(uLocal.getStatus());
            for (int i = 0; i < cmbKlijent.getItemCount(); i++) {
                if (cmbKlijent.getItemAt(i).getIdKlijent() == uLocal.getIdKlijent()) {
                    cmbKlijent.setSelectedIndex(i); break;
                }
            }
            tableModelStavke.setLista(uLocal.getStavke() != null ? uLocal.getStavke() : new ArrayList<>());
            azurirajUkupanIznos();
            javax.swing.JOptionPane.showMessageDialog(this, "Систем је нашао уговор");
            return;
        }
        try {
            Operation op = new Operation();
            op.setType(OperationType.PRETRAZI_UGOVOR);
            op.setRequest(id);
            op = ClientController.getInstance().sendOperation(op);
            if (!op.isSuccess()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Систем не може да нађе уговор");
                return;
            }
            Ugovor u = (Ugovor) op.getResponse();
            txtIdUgovor.setText(String.valueOf(u.getIdUgovor()));
            datDatumIzdavanja.setValue(u.getDatumIzdavanja() != null ? u.getDatumIzdavanja() : new java.util.Date());
            datDatumPlaniran.setValue(u.getDatumVracanjaPlaniran() != null ? u.getDatumVracanjaPlaniran() : new java.util.Date());
            datDatumStvaran.setValue(u.getDatumVracanjaStvarni() != null ? u.getDatumVracanjaStvarni() : new java.util.Date());
            txtPopust.setText(String.valueOf(u.getPopust()));
            originalniStatus = u.getStatus();
            cmbStatus.setSelectedItem(u.getStatus());
            // Postavi klijenta
            for (int i = 0; i < cmbKlijent.getItemCount(); i++) {
                if (cmbKlijent.getItemAt(i).getIdKlijent() == u.getIdKlijent()) {
                    cmbKlijent.setSelectedIndex(i);
                    break;
                }
            }
            // Ucitaj stavke
            List<StavkaUgovora> stavke = u.getStavke() != null ? u.getStavke() : new ArrayList<>();
            tableModelStavke.setLista(stavke);
            azurirajUkupanIznos();
            javax.swing.JOptionPane.showMessageDialog(this, "Систем је нашао уговор");
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Систем не може да нађе уговор");
        }
    }

    private void osveziTabeluStavki() {
        tableModelStavke.setLista(tableModelStavke.getLista());
    }

    private void azurirajUkupanIznos() {
        double suma = 0;
        for (StavkaUgovora s : tableModelStavke.getLista()) {
            suma += s.getIznos();
        }
        int popust = 0;
        try {
            popust = Integer.parseInt(txtPopust.getText().trim());
        } catch (NumberFormatException ex) {
        }
        if (popust > 0 && popust <= 100) {
            suma = suma * (100 - popust) / 100.0;
        }
        lblUkupanIznos.setText(String.format("Ukupan iznos: %.2f", suma));
    }

    private void azurirajSlobodnuKolicinu() {
        if (cmbOprema.getSelectedItem() == null) {
            jLabel10.setText("Oprema");
            jLabel11.setText("Kolicina");
            return;
        }
        Oprema sel = (Oprema) cmbOprema.getSelectedItem();
        jLabel10.setText("Oprema (cena/dan: " + sel.getCenaPoDanu() + ")");
        java.util.Date datOd = (java.util.Date) datDatumIzdavanja.getValue();
        java.util.Date datDo = (java.util.Date) datDatumPlaniran.getValue();
        java.util.Calendar cOdS = java.util.Calendar.getInstance();
        cOdS.setTime(datOd);
        cOdS.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cOdS.set(java.util.Calendar.MINUTE, 0);
        cOdS.set(java.util.Calendar.SECOND, 0);
        cOdS.set(java.util.Calendar.MILLISECOND, 0);
        java.util.Calendar cDoS = java.util.Calendar.getInstance();
        cDoS.setTime(datDo);
        cDoS.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cDoS.set(java.util.Calendar.MINUTE, 0);
        cDoS.set(java.util.Calendar.SECOND, 0);
        cDoS.set(java.util.Calendar.MILLISECOND, 0);
        if (cDoS.before(cOdS)) {
            jLabel11.setText("Kolicina");
            return;
        }
        try {
            StavkaUgovora provera = new StavkaUgovora();
            provera.setIdOprema(sel.getIdOprema());
            provera.setDatumOd(datOd);
            provera.setDatumDo(datDo);
            provera.setKolicina(0);
            provera.setIdUgovor(0);
            int slobodno = ClientController.getInstance().proveriDostupnostOpreme(provera);
            jLabel11.setText("Kolicina (slobodno: " + slobodno + ")");
        } catch (Exception e) {
            jLabel11.setText("Kolicina");
        }
    }

    private void ocisti() {
        txtIdUgovor.setText("");
        txtPopust.setText("0");
        cmbStatus.setSelectedIndex(0);
        if (cmbKlijent.getItemCount() > 0) {
            cmbKlijent.setSelectedIndex(0);
        }
        datDatumIzdavanja.setValue(new java.util.Date());
        datDatumPlaniran.setValue(new java.util.Date());
        datDatumStvaran.setValue(new java.util.Date());
        tableModelStavke.setLista(new java.util.ArrayList<>());
        tblUgovori.clearSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtIdUgovor = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        datDatumIzdavanja = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        datDatumPlaniran = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        datDatumStvaran = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        txtPopust = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cmbKlijent = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUgovori = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStavke = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmbOprema = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtKolicina = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnPretrazi = new javax.swing.JButton();
        btnKreirajUgovor = new javax.swing.JButton();
        btnPromeniUgovor = new javax.swing.JButton();
        btnOcisti = new javax.swing.JButton();
        btnDodajStavku = new javax.swing.JButton();
        btnUkloniStavku = new javax.swing.JButton();
        lblUkupanIznos = new javax.swing.JLabel();
        btnIzmeniStavku = new javax.swing.JButton();
        btnSacuvaj = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtIdUgovor.setEnabled(false);

        jLabel1.setText("ID");

        datDatumIzdavanja.setModel(new javax.swing.SpinnerDateModel());

        jLabel2.setText("Datum Izdavanja");

        jLabel3.setText("Planiran Datum");

        datDatumPlaniran.setModel(new javax.swing.SpinnerDateModel());

        jLabel4.setText("Stvaran Datum");

        datDatumStvaran.setModel(new javax.swing.SpinnerDateModel());

        jLabel5.setText("Popust");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "aktivan", "završen", "storniran" }));

        jLabel6.setText("Status");

        jLabel7.setText("Klijent");

        tblUgovori.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblUgovori);

        tblStavke.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblStavke);

        jLabel8.setText("Aktivni ugovori");

        jLabel9.setText("Stavke ugovora");

        jLabel10.setText("Oprema");

        jLabel11.setText("Kolicina");

        btnPretrazi.setText("Pretraži");
        btnPretrazi.addActionListener(this::btnPretraziActionPerformed);

        btnKreirajUgovor.setText("Kreiraj ugovor");
        btnKreirajUgovor.addActionListener(this::btnKreirajUgovorActionPerformed);

        btnPromeniUgovor.setText("Izmeni ugovor");
        btnPromeniUgovor.addActionListener(this::btnPromeniUgovorActionPerformed);

        btnOcisti.setText("Očisti");
        btnOcisti.addActionListener(this::btnOcistiActionPerformed);

        btnDodajStavku.setText("Dodaj stavku");
        btnDodajStavku.addActionListener(this::btnDodajStavkuActionPerformed);

        btnUkloniStavku.setText("Obriši");
        btnUkloniStavku.addActionListener(this::btnUkloniStavkuActionPerformed);

        lblUkupanIznos.setText("Ukupan iznos: 0.00");

        btnIzmeniStavku.setText("Izmeni");
        btnIzmeniStavku.addActionListener(this::btnIzmeniStavkuActionPerformed);

        btnSacuvaj.setText("Sačuvaj");
        btnSacuvaj.addActionListener(this::btnSacuvajActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbOprema, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(262, 262, 262))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                                        .addComponent(btnIzmeniStavku)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addComponent(btnDodajStavku))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(141, 141, 141))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdUgovor, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(lblUkupanIznos)
                                .addGap(221, 221, 221)
                                .addComponent(btnSacuvaj, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnOcisti, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnUkloniStavku))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(datDatumIzdavanja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel2))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(datDatumPlaniran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel3))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel4)
                                                .addComponent(datDatumStvaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel5)
                                                .addComponent(txtPopust, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel6))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel7)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(cmbKlijent, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(btnPretrazi, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addComponent(jLabel8))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnKreirajUgovor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnPromeniUgovor, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdUgovor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(lblUkupanIznos)
                    .addComponent(btnSacuvaj, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datDatumIzdavanja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datDatumPlaniran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(datDatumStvaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnKreirajUgovor))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(btnPromeniUgovor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPopust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbKlijent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPretrazi))
                .addGap(4, 4, 4)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel9)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbOprema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtKolicina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDodajStavku)
                        .addComponent(btnIzmeniStavku)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUkloniStavku)
                    .addComponent(btnOcisti))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKreirajUgovorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKreirajUgovorActionPerformed
        if (cmbKlijent.getSelectedItem() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Odaberite klijenta."); return;
        }
        if (tableModelStavke.getLista().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Систем не може да креира уговор"); return;
        }
        int popust = 0;
        try { popust = Integer.parseInt(txtPopust.getText().trim()); } catch (NumberFormatException ex) {}
        Ugovor u = new Ugovor();
        u.setIdUgovor(0);
        u.setDatumIzdavanja((java.util.Date) datDatumIzdavanja.getValue());
        u.setDatumVracanjaPlaniran((java.util.Date) datDatumPlaniran.getValue());
        u.setDatumVracanjaStvarni(null);
        u.setPopust(popust);
        u.setStatus(Ugovor.STATUS_AKTIVAN);
        u.setIdKlijent(((Klijent) cmbKlijent.getSelectedItem()).getIdKlijent());
        u.setIdRadnik(radnik.getIdRadnik());
        u.setStavke(new ArrayList<>(tableModelStavke.getLista()));
        double sumaStavki = 0;
        for (StavkaUgovora s : u.getStavke()) sumaStavki += s.getIznos();
        if (popust > 0 && popust <= 100) sumaStavki = sumaStavki * (100 - popust) / 100.0;
        u.setUkupanIznos(sumaStavki);
        tableModelUgovori.addUgovor(u);
        ocisti();
        javax.swing.JOptionPane.showMessageDialog(this, "Систем је креирао уговор");
    }//GEN-LAST:event_btnKreirajUgovorActionPerformed

    private void btnPromeniUgovorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromeniUgovorActionPerformed
        int row = tblUgovori.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Odaberite ugovor iz tabele.");
            return;
        }
        if (cmbKlijent.getSelectedItem() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Odaberite klijenta."); return;
        }
        Ugovor original = tableModelUgovori.getUgovorAt(row);
        if (Ugovor.STATUS_STORNIRAN.equals(original.getStatus())) {
            javax.swing.JOptionPane.showMessageDialog(this, "Stornirani ugovor se ne može menjati."); return;
        }
        String noviStatus = (String) cmbStatus.getSelectedItem();
        if (Ugovor.STATUS_ZAVRSEN.equals(original.getStatus()) && !Ugovor.STATUS_STORNIRAN.equals(noviStatus)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Završeni ugovor može jedino biti preveden u status 'Storniran'."); return;
        }
        int popust = 0;
        try { popust = Integer.parseInt(txtPopust.getText().trim()); } catch (NumberFormatException ex) {}
        Ugovor u = new Ugovor();
        u.setIdUgovor(original.getIdUgovor());
        u.setDatumIzdavanja((java.util.Date) datDatumIzdavanja.getValue());
        u.setDatumVracanjaPlaniran((java.util.Date) datDatumPlaniran.getValue());
        u.setPopust(popust);
        u.setStatus(noviStatus);
        u.setIdKlijent(((Klijent) cmbKlijent.getSelectedItem()).getIdKlijent());
        u.setIdRadnik(radnik.getIdRadnik());
        u.setStavke(new ArrayList<>(tableModelStavke.getLista()));
        if (Ugovor.STATUS_ZAVRSEN.equals(noviStatus)) {
            u.setDatumVracanjaStvarni(new java.util.Date());
        } else {
            u.setDatumVracanjaStvarni(null);
        }
        double sumaStavki = 0;
        for (StavkaUgovora s : u.getStavke()) sumaStavki += s.getIznos();
        if (popust > 0 && popust <= 100) sumaStavki = sumaStavki * (100 - popust) / 100.0;
        u.setUkupanIznos(sumaStavki);
        tableModelUgovori.updateUgovor(row, u);
        if (u.getIdUgovor() > 0) {
            izmenjeniIds.add(u.getIdUgovor());
        }
        javax.swing.JOptionPane.showMessageDialog(this, "Систем је изменио уговор.");
    }//GEN-LAST:event_btnPromeniUgovorActionPerformed

    private void btnPretraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPretraziActionPerformed
        try {
            Ugovor kriterijum = new Ugovor();
            kriterijum.setStatus((String) cmbStatus.getSelectedItem());
            if (cmbKlijent.getSelectedItem() != null) {
                kriterijum.setIdKlijent(((Klijent) cmbKlijent.getSelectedItem()).getIdKlijent());
            }
            kriterijum.setDatumIzdavanja((java.util.Date) datDatumIzdavanja.getValue());
            kriterijum.setDatumVracanjaPlaniran((java.util.Date) datDatumPlaniran.getValue());
            List<Ugovor> lista = ClientController.getInstance().vratiUgovorePoKlijentu(kriterijum);
            tableModelUgovori.setLista(lista);
            if (lista != null && !lista.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Систем је нашао уговоре по задатим критеријумима");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Систем не може да нађе уговоре по задатим критеријумима");
            }
        } catch (Exception e) {
            tableModelUgovori.setLista(new ArrayList<>());
            javax.swing.JOptionPane.showMessageDialog(this, "Систем не може да нађе уговоре по задатим критеријумима");
        }
    }//GEN-LAST:event_btnPretraziActionPerformed

    private void btnUkloniStavkuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUkloniStavkuActionPerformed
        int row = tblStavke.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Odaberite stavku za uklanjanje.");
            return;
        }
        List<StavkaUgovora> stavke = new ArrayList<>(tableModelStavke.getLista());
        stavke.remove(row);
        for (int i = 0; i < stavke.size(); i++) {
            stavke.get(i).setRb(i + 1);
        }
        tableModelStavke.setLista(stavke);
        azurirajUkupanIznos();
    }//GEN-LAST:event_btnUkloniStavkuActionPerformed

    private void btnDodajStavkuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajStavkuActionPerformed
        if (cmbOprema.getSelectedItem() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Odaberite opremu.");
            return;
        }
        String kolStr = txtKolicina.getText().trim();
        if (kolStr.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Unesite količinu.");
            return;
        }
        int kolicina;
        try {
            kolicina = Integer.parseInt(kolStr);
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Količina mora biti ceo broj.");
            return;
        }
        Oprema selOprema = (Oprema) cmbOprema.getSelectedItem();
        java.util.Date datOd = (java.util.Date) datDatumIzdavanja.getValue();
        java.util.Date datDo = (java.util.Date) datDatumPlaniran.getValue();
        java.util.Calendar cOd = java.util.Calendar.getInstance();
        cOd.setTime(datOd);
        cOd.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cOd.set(java.util.Calendar.MINUTE, 0);
        cOd.set(java.util.Calendar.SECOND, 0);
        cOd.set(java.util.Calendar.MILLISECOND, 0);
        java.util.Calendar cDo = java.util.Calendar.getInstance();
        cDo.setTime(datDo);
        cDo.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cDo.set(java.util.Calendar.MINUTE, 0);
        cDo.set(java.util.Calendar.SECOND, 0);
        cDo.set(java.util.Calendar.MILLISECOND, 0);
        if (cDo.before(cOd)) {
            javax.swing.JOptionPane.showMessageDialog(this, "Planirani datum povratka ne sme biti pre datuma izdavanja.");
            return;
        }
        // Proveriti dostupnost opreme u zeljenom periodu
        try {
            StavkaUgovora provera = new StavkaUgovora();
            provera.setIdOprema(selOprema.getIdOprema());
            provera.setDatumOd(datOd);
            provera.setDatumDo(datDo);
            // idUgovor = 0 za novi, ili stvarni ID pri izmeni
            int tekuciUgovorId = 0;
            String idStr = txtIdUgovor.getText().trim();
            if (!idStr.isEmpty()) {
                try {
                    tekuciUgovorId = Integer.parseInt(idStr);
                } catch (NumberFormatException ex) {
                }
            }
            provera.setIdUgovor(tekuciUgovorId);
            provera.setKolicina(kolicina); // proveri da li ima dovoljno slobodnih komada
            ClientController.getInstance().proveriDostupnostOpreme(provera);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Greska pri proveri dostupnosti: " + e.getMessage());
            return;
        }
        long diffDani = (cDo.getTimeInMillis() - cOd.getTimeInMillis()) / (1000L * 60 * 60 * 24);
        long dani = Math.max(1, diffDani);
        double iznos = kolicina * selOprema.getCenaPoDanu() * dani;
        StavkaUgovora stavka = new StavkaUgovora();
        stavka.setRb(tableModelStavke.getRowCount() + 1);
        stavka.setIdOprema(selOprema.getIdOprema());
        stavka.setOprema(selOprema);
        stavka.setKolicina(kolicina);
        stavka.setIznos(iznos);
        List<StavkaUgovora> stavke = new ArrayList<>(tableModelStavke.getLista());
        stavke.add(stavka);
        tableModelStavke.setLista(stavke);
        azurirajUkupanIznos();
        txtKolicina.setText("");
    }//GEN-LAST:event_btnDodajStavkuActionPerformed

    private void btnOcistiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOcistiActionPerformed
        ocisti();
        ucitajSveUgovore();
    }//GEN-LAST:event_btnOcistiActionPerformed

    private void btnIzmeniStavkuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIzmeniStavkuActionPerformed
        int row = tblStavke.getSelectedRow();
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Odaberite stavku za izmenu.");
            return;
        }
        StavkaUgovora stavka = tableModelStavke.getStavkaAt(row);
        // Popuni polja vrednostima iz stavke
        for (int i = 0; i < cmbOprema.getItemCount(); i++) {
            if (cmbOprema.getItemAt(i).getIdOprema() == stavka.getIdOprema()) {
                cmbOprema.setSelectedIndex(i);
                break;
            }
        }
        txtKolicina.setText(String.valueOf(stavka.getKolicina()));
        // Pitaj korisnika za novu kolicinu
        String novaKolStr = javax.swing.JOptionPane.showInputDialog(this, "Nova količina:", stavka.getKolicina());
        if (novaKolStr == null) {
            return; // cancel
        }
        int novaKolicina;
        try {
            novaKolicina = Integer.parseInt(novaKolStr.trim());
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Količina mora biti ceo broj.");
            return;
        }
        if (novaKolicina <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Količina mora biti veća od 0.");
            return;
        }
        // Proveri dostupnost samo ako se kolicina povecava
        java.util.Date datOd = (java.util.Date) datDatumIzdavanja.getValue();
        java.util.Date datDo = (java.util.Date) datDatumPlaniran.getValue();
        if (novaKolicina > stavka.getKolicina()) {
            try {
                StavkaUgovora provera = new StavkaUgovora();
                provera.setIdOprema(stavka.getIdOprema());
                provera.setDatumOd(datOd);
                provera.setDatumDo(datDo);
                int tekuciUgovorId = 0;
                String idStr = txtIdUgovor.getText().trim();
                if (!idStr.isEmpty()) {
                    try {
                        tekuciUgovorId = Integer.parseInt(idStr);
                    } catch (NumberFormatException ex) {
                    }
                }
                provera.setIdUgovor(tekuciUgovorId);
                provera.setKolicina(novaKolicina);
                ClientController.getInstance().proveriDostupnostOpreme(provera);
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Greška pri proveri dostupnosti: " + e.getMessage());
                return;
            }
        }
        // Ažuriraj stavku
        java.util.Calendar cOdI = java.util.Calendar.getInstance();
        cOdI.setTime(datOd);
        cOdI.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cOdI.set(java.util.Calendar.MINUTE, 0);
        cOdI.set(java.util.Calendar.SECOND, 0);
        cOdI.set(java.util.Calendar.MILLISECOND, 0);
        java.util.Calendar cDoI = java.util.Calendar.getInstance();
        cDoI.setTime(datDo);
        cDoI.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cDoI.set(java.util.Calendar.MINUTE, 0);
        cDoI.set(java.util.Calendar.SECOND, 0);
        cDoI.set(java.util.Calendar.MILLISECOND, 0);
        long dani = Math.max(1, (cDoI.getTimeInMillis() - cOdI.getTimeInMillis()) / (1000L * 60 * 60 * 24));
        double iznos = novaKolicina * stavka.getOprema().getCenaPoDanu() * dani;
        stavka.setKolicina(novaKolicina);
        stavka.setIznos(iznos);
        tableModelStavke.setLista(tableModelStavke.getLista());
        azurirajUkupanIznos();
    }//GEN-LAST:event_btnIzmeniStavkuActionPerformed

    private void btnSacuvajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSacuvajActionPerformed
        List<Ugovor> lista = tableModelUgovori.getLista();
        boolean imaGreska = false;
        boolean imaNestoZaCuvati = false;
        for (Ugovor u : lista) {
            try {
                if (u.getIdUgovor() == 0) {
                    imaNestoZaCuvati = true;
                    ClientController.getInstance().kreirajUgovor(u);
                } else if (izmenjeniIds.contains(u.getIdUgovor())) {
                    imaNestoZaCuvati = true;
                    ClientController.getInstance().promeniUgovor(u);
                }
            } catch (Exception e) { imaGreska = true; imaNestoZaCuvati = true; }
        }
        izmenjeniIds.clear();
        if (!imaNestoZaCuvati) {
            javax.swing.JOptionPane.showMessageDialog(this, "Систем не може да запамти уговор");
            return;
        }
        if (!imaGreska) {
            javax.swing.JOptionPane.showMessageDialog(this, "Систем је запамтио уговор.");
            ucitajSveUgovore();
            ocisti();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Систем не може да запамти уговор");
        }
    }//GEN-LAST:event_btnSacuvajActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new UgovorForm(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodajStavku;
    private javax.swing.JButton btnIzmeniStavku;
    private javax.swing.JButton btnKreirajUgovor;
    private javax.swing.JButton btnOcisti;
    private javax.swing.JButton btnPretrazi;
    private javax.swing.JButton btnPromeniUgovor;
    private javax.swing.JButton btnSacuvaj;
    private javax.swing.JButton btnUkloniStavku;
    private javax.swing.JComboBox<Klijent> cmbKlijent;
    private javax.swing.JComboBox<Oprema> cmbOprema;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JSpinner datDatumIzdavanja;
    private javax.swing.JSpinner datDatumPlaniran;
    private javax.swing.JSpinner datDatumStvaran;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblUkupanIznos;
    private javax.swing.JTable tblStavke;
    private javax.swing.JTable tblUgovori;
    private javax.swing.JTextField txtIdUgovor;
    private javax.swing.JTextField txtKolicina;
    private javax.swing.JTextField txtPopust;
    // End of variables declaration//GEN-END:variables
}
