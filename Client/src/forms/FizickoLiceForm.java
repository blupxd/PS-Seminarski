/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package forms;

import controllers.ClientController;
import models.FizickoLice;
import models.Klijent;
import models.Radnik;
import tablemodels.FizickoLiceTableModel;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author matij
 */
public class FizickoLiceForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FizickoLiceForm.class.getName());

    /**
     * Creates new form RadnikForm
     */
    private Radnik radnik;
    private FizickoLiceTableModel tableModel;
    private java.util.List<Klijent> listaKlijenata = new java.util.ArrayList<>();

    public FizickoLiceForm(Radnik radnik) {
        this.radnik = radnik;
        initComponents();
        setTitle("Fizička lica");
        tableModel = new FizickoLiceTableModel();
        tblRadnici.setModel(tableModel);
        tblRadnici.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRadnici.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) popuniPoljaIzTabele();
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        ucitajKlijente();
        cmbKlijent.addActionListener(evt -> {
            if (tblRadnici.getSelectedRow() >= 0) return;
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
            FizickoLice kriterijum = new FizickoLice();
            List<FizickoLice> lista = ClientController.getInstance().pretrazujFizickaLica(kriterijum);
            tableModel.setLista(lista);
        } catch (Exception e) {
            tableModel.setLista(new ArrayList<>());
            String msg = e.getMessage();
            if (msg == null || !msg.toLowerCase().contains("ne može da nađe")) {
                javax.swing.JOptionPane.showMessageDialog(this, "Greška pri učitavanju: " + msg);
            }
        }
    }

    private void popuniPoljaIzTabele() {
        int row = tblRadnici.getSelectedRow();
        if (row < 0) return;
        FizickoLice fl = tableModel.getFizickoLiceAt(row);
        txtIme.setText(fl.getIme());
        txtPrezime.setText(fl.getPrezime());
        txtJMBG.setText(fl.getJMBG());
        txtBrojLicneKarte.setText(fl.getBrojLicneKarte());
        txtAdresa.setVisible(true);
        txtTelefon.setVisible(true);
        txtEmail.setVisible(true);
        txtAdresa.setText(fl.getAdresa() != null ? fl.getAdresa() : "");
        txtTelefon.setText(fl.getTelefon() != null ? fl.getTelefon() : "");
        txtEmail.setText(fl.getEmail() != null ? fl.getEmail() : "");
        int cmbIdx = 0;
        for (int i = 0; i < listaKlijenata.size(); i++) {
            if (listaKlijenata.get(i).getIdKlijent() == fl.getIdKlijent()) {
                cmbIdx = i + 1;
                break;
            }
        }
        cmbKlijent.setSelectedIndex(cmbIdx);
    }

    private FizickoLice kreirajIzPolja() {
        String ime = txtIme.getText().trim();
        String prezime = txtPrezime.getText().trim();
        String jmbg = txtJMBG.getText().trim();
        String blk = txtBrojLicneKarte.getText().trim();
        String adresa = txtAdresa.getText().trim();
        String telefon = txtTelefon.getText().trim();
        String email = txtEmail.getText().trim();
        if (ime.isEmpty() || prezime.isEmpty() || jmbg.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ime, prezime i JMBG su obavezni.");
            return null;
        }
        if (jmbg.length() != 13) {
            javax.swing.JOptionPane.showMessageDialog(this, "JMBG mora imati tačno 13 cifara.");
            return null;
        }
        FizickoLice fl = new FizickoLice();
        fl.setIme(ime);
        fl.setPrezime(prezime);
        fl.setJMBG(jmbg);
        fl.setBrojLicneKarte(blk);
        fl.setAdresa(adresa);
        fl.setTelefon(telefon);
        fl.setEmail(email);
        return fl;
    }

    private void ocisti() {
        txtIme.setText("");
        txtPrezime.setText("");
        txtJMBG.setText("");
        txtBrojLicneKarte.setText("");
        txtAdresa.setText("");
        txtTelefon.setText("");
        txtEmail.setText("");
        txtAdresa.setVisible(true);
        txtTelefon.setVisible(true);
        txtEmail.setVisible(true);
        cmbKlijent.setSelectedIndex(0);
        tblRadnici.clearSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtIme = new javax.swing.JTextField();
        txtPrezime = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtJMBG = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnDodaj = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRadnici = new javax.swing.JTable();
        btnPretrazi = new javax.swing.JButton();
        btnPromeni = new javax.swing.JButton();
        btnOcisti = new javax.swing.JButton();
        btnObrisi = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTelefon = new javax.swing.JTextField();
        txtAdresa = new javax.swing.JTextField();
        txtBrojLicneKarte = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cmbKlijent = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtPrezime.addActionListener(this::txtPrezimeActionPerformed);

        jLabel1.setText("Ime");

        jLabel2.setText("Prezime");

        jLabel3.setText("Broj lične karte");

        jLabel4.setText("JMBG");

        btnDodaj.setText("Dodaj");
        btnDodaj.addActionListener(this::btnDodajActionPerformed);

        jLabel6.setText("Email");

        tblRadnici.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblRadnici);

        btnPretrazi.setText("Pretraži");
        btnPretrazi.addActionListener(this::btnPretraziActionPerformed);

        btnPromeni.setText("Promeni");
        btnPromeni.addActionListener(this::btnPromeniActionPerformed);

        btnOcisti.setText("Očisti");
        btnOcisti.addActionListener(this::btnOcistiActionPerformed);

        btnObrisi.setText("Obriši");
        btnObrisi.addActionListener(this::btnObrisiActionPerformed);

        jLabel5.setText("Adresa");

        jLabel7.setText("Telefon");

        jLabel8.setText("Klijent");

        cmbKlijent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Novi klijent --" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDodaj)
                                .addGap(18, 18, 18)
                                .addComponent(btnPretrazi))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnOcisti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPromeni, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)))
                    .addComponent(btnObrisi)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJMBG, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtBrojLicneKarte, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIme, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtPrezime, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtAdresa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbKlijent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(5, 5, 5)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(5, 5, 5)
                                .addComponent(txtTelefon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(28, 28, 28))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(5, 5, 5)
                                .addComponent(cmbKlijent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(txtAdresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(5, 5, 5)
                            .addComponent(txtIme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(5, 5, 5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtJMBG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtBrojLicneKarte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(5, 5, 5)
                            .addComponent(txtPrezime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnOcisti))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnDodaj)
                            .addComponent(btnPretrazi))))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPromeni))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnObrisi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPrezimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrezimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrezimeActionPerformed

    private void btnPretraziActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPretraziActionPerformed
        try {
            FizickoLice kriterijum = new FizickoLice();
            kriterijum.setIme(txtIme.getText().trim());
            kriterijum.setPrezime(txtPrezime.getText().trim());
            kriterijum.setJMBG(txtJMBG.getText().trim());
            List<FizickoLice> lista = ClientController.getInstance().pretrazujFizickaLica(kriterijum);
            tableModel.setLista(lista);
        } catch (Exception e) {
            tableModel.setLista(new ArrayList<>());
            javax.swing.JOptionPane.showMessageDialog(this, "Greška: " + e.getMessage());
        }
    }//GEN-LAST:event_btnPretraziActionPerformed

    private void btnDodajActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDodajActionPerformed
        FizickoLice fl = kreirajIzPolja();
        if (fl == null) return;
        int idx = cmbKlijent.getSelectedIndex();
        try {
            if (idx == 0) {
                ClientController.getInstance().kreirajFizickoLice(fl);
            } else {
                Klijent k = listaKlijenata.get(idx - 1);
                fl.setIdKlijent(k.getIdKlijent());
                fl.setAdresa(k.getAdresa());
                fl.setTelefon(k.getTelefon());
                fl.setEmail(k.getEmail());
                ClientController.getInstance().ubaciFizickoLice(fl);
            }
            ucitajSve(); ucitajKlijente(); ocisti();
        } catch (Exception e) { javax.swing.JOptionPane.showMessageDialog(this, "Greška: " + e.getMessage()); }
    }//GEN-LAST:event_btnDodajActionPerformed

    private void btnOcistiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOcistiActionPerformed
        ocisti();
        ucitajSve();
    }//GEN-LAST:event_btnOcistiActionPerformed

    private void btnPromeniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPromeniActionPerformed
        int row = tblRadnici.getSelectedRow();
        if (row < 0) { javax.swing.JOptionPane.showMessageDialog(this, "Odaberite fizičko lice iz tabele."); return; }
        FizickoLice fl = kreirajIzPolja();
        if (fl == null) return;
        fl.setIdKlijent(tableModel.getFizickoLiceAt(row).getIdKlijent());
        try {
            ClientController.getInstance().promeniFizickoLice(fl);
            ucitajSve(); ucitajKlijente(); ocisti();
        } catch (Exception e) { javax.swing.JOptionPane.showMessageDialog(this, "Greška: " + e.getMessage()); }
    }//GEN-LAST:event_btnPromeniActionPerformed

    private void btnObrisiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObrisiActionPerformed
        int row = tblRadnici.getSelectedRow();
        if (row < 0) { javax.swing.JOptionPane.showMessageDialog(this, "Odaberite fizičko lice iz tabele."); return; }
        int potvrda = javax.swing.JOptionPane.showConfirmDialog(this, "Brisanje fizičkog lica je nepovratno. Nastavi?", "Potvrda", javax.swing.JOptionPane.YES_NO_OPTION);
        if (potvrda != javax.swing.JOptionPane.YES_OPTION) return;
        int id = tableModel.getFizickoLiceAt(row).getIdKlijent();
        try {
            ClientController.getInstance().obrisiFizickoLice(id);
            ucitajSve(); ucitajKlijente(); ocisti();
        } catch (Exception e) { javax.swing.JOptionPane.showMessageDialog(this, "Greška: " + e.getMessage()); }
    }//GEN-LAST:event_btnObrisiActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new FizickoLiceForm(null).setVisible(true));
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblRadnici;
    private javax.swing.JTextField txtAdresa;
    private javax.swing.JTextField txtBrojLicneKarte;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIme;
    private javax.swing.JTextField txtJMBG;
    private javax.swing.JTextField txtPrezime;
    private javax.swing.JTextField txtTelefon;
    // End of variables declaration//GEN-END:variables
}
