/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package forms;

import controllers.ClientController;
import models.Klijent;
import models.PravnoLice;
import models.Radnik;
import tablemodels.PravnoLiceTableModel;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author matij
 */
public class PravnoLiceForm extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PravnoLiceForm.class.getName());

    /**
     * Creates new form RadnikForm
     */
    private Radnik radnik;
    private PravnoLiceTableModel tableModel;
    private java.util.List<Klijent> listaKlijenata = new java.util.ArrayList<>();

    public PravnoLiceForm(Radnik radnik) {
        this.radnik = radnik;
        initComponents();
        setTitle("Pravna lica");
        tableModel = new PravnoLiceTableModel();
        tblPravnaLica.setModel(tableModel);
        tblPravnaLica.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPravnaLica.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                popuniPoljaIzTabele();
            }
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ucitajKlijente();
        cmbKlijent.addActionListener(evt -> {
            // Ako je red u tabeli izabran (edit mode), ne mijenjamo vidljivost polja
            if (tblPravnaLica.getSelectedRow() >= 0) return;
            boolean noviKlijent = (cmbKlijent.getSelectedIndex() == 0);
            txtAdresa.setVisible(noviKlijent);
            txtTelefon.setVisible(noviKlijent);
            txtEmail.setVisible(noviKlijent);
            if (!noviKlijent) {
                int idx = cmbKlijent.getSelectedIndex() - 1;
                if (idx >= 0 && idx < listaKlijenata.size()) {
                    Klijent k = listaKlijenata.get(idx);
                    txtAdresa.setText(k.getAdresa() != null ? k.getAdresa() : "");
                    txtTelefon.setText(k.getTelefon() != null ? k.getTelefon() : "");
                    txtEmail.setText(k.getEmail() != null ? k.getEmail() : "");
                }
            }
        });
        ucitajSve();
    }

    private void ucitajKlijente() {
        try {
            List<Klijent> lista = ClientController.getInstance().vratiSveKlijente();
            listaKlijenata.clear();
            cmbKlijent.removeAllItems();
            cmbKlijent.addItem("-- Novi klijent --");
            listaKlijenata = lista;
            for (Klijent k : listaKlijenata) cmbKlijent.addItem(k.toString());
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Greška pri učitavanju klijenata: " + e.getMessage());
        }
    }

    private void ucitajSve() {
        try {
            PravnoLice kriterijum = new PravnoLice();
            List<PravnoLice> lista = ClientController.getInstance().pretrazujPravnaLica(kriterijum);
            tableModel.setLista(lista);
        } catch (Exception e) {
            tableModel.setLista(new ArrayList<>());
            javax.swing.JOptionPane.showMessageDialog(this, "Greška pri učitavanju: " + e.getMessage());
        }
    }

    private void popuniPoljaIzTabele() {
        int row = tblPravnaLica.getSelectedRow();
        if (row < 0) return;
        PravnoLice pl = tableModel.getPravnoLiceAt(row);
        txtNazivFirme.setText(pl.getNazivFirme());
        txtPIB.setText(pl.getPIB());
        txtMaticniBroj.setText(pl.getMaticniBroj() != null ? pl.getMaticniBroj() : "");
        // Sva polja vidljiva za editovanje
        txtAdresa.setVisible(true);
        txtTelefon.setVisible(true);
        txtEmail.setVisible(true);
        txtAdresa.setText(pl.getAdresa() != null ? pl.getAdresa() : "");
        txtTelefon.setText(pl.getTelefon() != null ? pl.getTelefon() : "");
        txtEmail.setText(pl.getEmail() != null ? pl.getEmail() : "");
        // Postavi cmbKlijent (ActionListener nece sakriti polja jer je red izabran)
        int cmbIdx = 0;
        for (int i = 0; i < listaKlijenata.size(); i++) {
            if (listaKlijenata.get(i).getIdKlijent() == pl.getIdKlijent()) {
                cmbIdx = i + 1;
                break;
            }
        }
        cmbKlijent.setSelectedIndex(cmbIdx);
    }

    private PravnoLice kreirajIzPolja() {
        String nazivFirme = txtNazivFirme.getText().trim();
        String pib = txtPIB.getText().trim();
        String maticniBroj = txtMaticniBroj.getText().trim();
        String adresa = txtAdresa.getText().trim();
        String telefon = txtTelefon.getText().trim();
        String email = txtEmail.getText().trim();
        if (nazivFirme.isEmpty() || pib.isEmpty() || maticniBroj.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Naziv firme, PIB i matični broj su obavezni.");
            return null;
        }
        PravnoLice pl = new PravnoLice();
        pl.setNazivFirme(nazivFirme);
        pl.setPIB(pib);
        pl.setMaticniBroj(maticniBroj);
        pl.setAdresa(adresa);
        pl.setTelefon(telefon);
        pl.setEmail(email);
        return pl;
    }

    private void ocisti() {
        txtNazivFirme.setText("");
        txtPIB.setText("");
        txtMaticniBroj.setText("");
        txtAdresa.setText("");
        txtTelefon.setText("");
        txtEmail.setText("");
        txtAdresa.setVisible(true);
        txtTelefon.setVisible(true);
        txtEmail.setVisible(true);
        cmbKlijent.setSelectedIndex(0);
        tblPravnaLica.clearSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtPIB = new javax.swing.JTextField();
        txtNazivFirme = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnDodaj = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtAdresa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPravnaLica = new javax.swing.JTable();
        btnPretrazi = new javax.swing.JButton();
        btnPromeni = new javax.swing.JButton();
        btnOcisti = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtTelefon = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        cmbKlijent = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMaticniBroj = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtPIB.addActionListener(this::txtPIBActionPerformed);

        txtNazivFirme.addActionListener(this::txtNazivFirmeActionPerformed);

        jLabel1.setText("Naziv firme");

        jLabel2.setText("PIB");

        btnDodaj.setText("Dodaj");
        btnDodaj.addActionListener(this::btnDodajActionPerformed);

        jLabel6.setText("Adresa");

        tblPravnaLica.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblPravnaLica);

        btnPretrazi.setText("Pretraži");
        btnPretrazi.addActionListener(this::btnPretraziActionPerformed);

        btnPromeni.setText("Promeni");
        btnPromeni.addActionListener(this::btnPromeniActionPerformed);

        btnOcisti.setText("Očisti");
        btnOcisti.addActionListener(this::btnOcistiActionPerformed);

        btnObrisi.setText("Obriši");
        btnObrisi.addActionListener(this::btnObrisiActionPerformed);

        jLabel3.setText("Telefon");

        jLabel4.setText("Email");

        cmbKlijent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Novi klijent --" }));

        jLabel5.setText("Klijent");

        jLabel7.setText("Maticni broj");

        txtMaticniBroj.addActionListener(this::txtMaticniBrojActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnObrisi)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnPretrazi)
                                    .addComponent(btnPromeni))))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtNazivFirme, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtPIB, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtMaticniBroj, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(73, 73, 73)
                                        .addComponent(btnOcisti, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnDodaj)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(134, 134, 134))
                                    .addComponent(txtAdresa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbKlijent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(56, 56, 56))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)
                        .addComponent(cmbKlijent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtAdresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNazivFirme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDodaj)
                    .addComponent(btnOcisti)
                    .addComponent(txtPIB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaticniBroj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnPretrazi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPromeni)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnObrisi)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNazivFirmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNazivFirmeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNazivFirmeActionPerformed

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        PravnoLice pl = kreirajIzPolja();
        if (pl == null) return;
        int idx = cmbKlijent.getSelectedIndex();
        try {
            if (idx == 0) {
                // SK8: Novi klijent — KREIRAJ_PRAVNO_LICE
                ClientController.getInstance().kreirajPravnoLice(pl);
            } else {
                // SK9: Postojeći klijent — UBACI_PRAVNO_LICE
                Klijent k = listaKlijenata.get(idx - 1);
                pl.setIdKlijent(k.getIdKlijent());
                pl.setAdresa(k.getAdresa());
                pl.setTelefon(k.getTelefon());
                pl.setEmail(k.getEmail());
                ClientController.getInstance().ubacijPravnoLice(pl);
            }
            ucitajSve(); ucitajKlijente(); ocisti();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Greška: " + e.getMessage());
        }
    }//GEN-LAST:event_btnDodajActionPerformed

    private void txtPIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPIBActionPerformed

    private void btnOcistiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOcistiActionPerformed
        ocisti();
        ucitajSve();
    }//GEN-LAST:event_btnOcistiActionPerformed

    private void btnPretraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPretraziActionPerformed
        try {
            PravnoLice kriterijum = kreirajIzPolja();
            if (kriterijum == null) kriterijum = new PravnoLice();
            List<PravnoLice> lista = ClientController.getInstance().pretrazujPravnaLica(kriterijum);
            tableModel.setLista(lista);
        } catch (Exception e) {
            tableModel.setLista(new ArrayList<>());
            javax.swing.JOptionPane.showMessageDialog(this, "Greška: " + e.getMessage());
        }
    }//GEN-LAST:event_btnPretraziActionPerformed

    private void btnPromeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromeniActionPerformed
        int row = tblPravnaLica.getSelectedRow();
        if (row < 0) { javax.swing.JOptionPane.showMessageDialog(this, "Odaberite pravno lice iz tabele."); return; }
        PravnoLice pl = kreirajIzPolja();
        if (pl == null) return;
        pl.setIdKlijent(tableModel.getPravnoLiceAt(row).getIdKlijent());
        try {
            ClientController.getInstance().promeniPravnoLice(pl);
            ucitajSve(); ucitajKlijente(); ocisti();
        } catch (Exception e) { javax.swing.JOptionPane.showMessageDialog(this, "Greška: " + e.getMessage()); }
    }//GEN-LAST:event_btnPromeniActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
        int row = tblPravnaLica.getSelectedRow();
        if (row < 0) { javax.swing.JOptionPane.showMessageDialog(this, "Odaberite pravno lice iz tabele."); return; }
        int potvrda = javax.swing.JOptionPane.showConfirmDialog(this, "Brisanje pravnog lica je nepovratno. Nastavi?", "Potvrda", javax.swing.JOptionPane.YES_NO_OPTION);
        if (potvrda != javax.swing.JOptionPane.YES_OPTION) return;
        int id = tableModel.getPravnoLiceAt(row).getIdKlijent();
        try {
            ClientController.getInstance().obrisiPravnoLice(id);
            ucitajSve(); ucitajKlijente(); ocisti();
        } catch (Exception e) { javax.swing.JOptionPane.showMessageDialog(this, "Greška: " + e.getMessage()); }
    }//GEN-LAST:event_btnObrisiActionPerformed

    private void txtMaticniBrojActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaticniBrojActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaticniBrojActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new PravnoLiceForm(null).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDodaj;
    private javax.swing.JButton btnObrisi;
    private javax.swing.JButton btnOcisti;
    private javax.swing.JButton btnPretrazi;
    private javax.swing.JButton btnPromeni;
    private javax.swing.JComboBox<String> cmbKlijent;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPravnaLica;
    private javax.swing.JTextField txtAdresa;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaticniBroj;
    private javax.swing.JTextField txtNazivFirme;
    private javax.swing.JTextField txtPIB;
    private javax.swing.JTextField txtTelefon;
    // End of variables declaration//GEN-END:variables
}
