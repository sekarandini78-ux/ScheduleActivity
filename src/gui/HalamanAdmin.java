package gui;
import entity.*;
import repository.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;
import java.time.LocalDate;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author rafiqi
 */
public class HalamanAdmin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HalamanAdmin.class.getName());
    private javax.swing.JLabel lblTotalUser;
    private javax.swing.JLabel lblTotalJadwal;
    private javax.swing.JLabel lblJadwalHariIni;
    private javax.swing.JLabel lblPeringatan;
    /**
     * Creates new form HalamanAdmin
     */
    public HalamanAdmin() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("Admin Schedule Activity");
        aturTampilan();
        muatSemuaData();
        
    }
    
    private void aturTampilan() {
        jLabel2.setText("Admin Schedule Activity");
        jLabel2.setFont(new Font("Arial", Font.BOLD, 24));

        jLabel1.setText("Selamat Datang, Admin");
        jLabel3.setText("MENU ADMIN");
        jLabel4.setText("RINGKASAN");
        jLabel5.setText("Total User");
        jLabel7.setText("Total Jadwal");
        jLabel8.setText("Jadwal Hari Ini");
        jLabel9.setText("Peringatan");
        jLabel6.setText("JADWAL BARU");

        Color warnaTombol = new Color(240, 240, 255);
        jButton1.setBackground(warnaTombol);
        jButton2.setBackground(warnaTombol);
        jButton3.setBackground(warnaTombol);
        jButton5.setBackground(warnaTombol);
        jButton1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jButton2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jButton3.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        jButton5.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
    
    private void muatSemuaData() {
        CRUD crud = new CRUD();
        crud.updateStatusOtomatis();
        muatRingkasan();
        muatTabelJadwal();
    }

    private void muatRingkasan() {
        Connection conn = DbKoneksi.getKoneksi();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Koneksi database gagal!");
            return;
        }

        try {
            Statement stm = conn.createStatement();
            String hariIni = java.time.LocalDate.now().toString();
            String waktuSekarang = java.time.LocalTime.now().toString().substring(0, 8);

            ResultSet rsUser = stm.executeQuery("SELECT COUNT(*) AS total FROM tb_pengguna WHERE hak_akses = 'user'");
            if (rsUser.next()) {
                jLabel7.setText(rsUser.getString("total"));
            }

            ResultSet rsHariIni = stm.executeQuery(
                "SELECT COUNT(*) AS total FROM tb_kegiatan " +
                "WHERE tanggal = '" + hariIni + "' " +
                "AND status = 'Belum Selesai' " +
                "AND jam > '" + waktuSekarang + "'"
            );
            if (rsHariIni.next()) {
                jLabel9.setText(rsHariIni.getString("total"));
            }

            ResultSet rsSelesai = stm.executeQuery("SELECT COUNT(*) AS total FROM tb_kegiatan WHERE status = 'Selesai'");
            if (rsSelesai.next()) {
                jLabel11.setText(rsSelesai.getString("total"));
            }

            ResultSet rsBelum = stm.executeQuery("SELECT COUNT(*) AS total FROM tb_kegiatan WHERE status = 'Belum Selesai'");
            if (rsBelum.next()) {
                jLabel13.setText(rsBelum.getString("total"));
            }

            rsUser.close(); rsHariIni.close(); rsSelesai.close(); rsBelum.close();
            stm.close(); conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal baca ringkasan:\n" + e.getMessage());
        }
    }

    private void muatTabelJadwal() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        Connection conn = DbKoneksi.getKoneksi();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Koneksi database gagal!");
            return;
        }

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT nama_kegiatan, tanggal, jam FROM tb_kegiatan ORDER BY tanggal DESC LIMIT 10");

            int nomor = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                    nomor++,
                    rs.getString("nama_kegiatan"),
                    rs.getString("tanggal"),
                    rs.getString("jam")
                });
            }

            rs.close();
            stm.close();
            conn.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal baca jadwal:\n" + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        panelTotalUser = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        panelTotalKegiatan = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelSelesai = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(690, 400));
        setMinimumSize(new java.awt.Dimension(690, 400));
        setPreferredSize(new java.awt.Dimension(690, 400));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(690, 390));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Selamat Datang, Admin");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(6, 32, 156, 20);

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 3, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MENU ADMIN");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(6, 91, 105, 20);

        jButton1.setText("Kelola User");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel2.add(jButton1);
        jButton1.setBounds(6, 141, 147, 23);

        jButton2.setText("Kelola Jadwal");
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jPanel2.add(jButton2);
        jButton2.setBounds(6, 182, 147, 23);

        jButton3.setText("Laporan");
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jPanel2.add(jButton3);
        jButton3.setBounds(6, 223, 147, 23);

        jButton5.setText("Logout");
        jButton5.addActionListener(this::jButton5ActionPerformed);
        jPanel2.add(jButton5);
        jButton5.setBounds(92, 348, 72, 23);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 170, 390);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setText("Admin Schedule Aktivity");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(203, 6, 282, 28);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("RINGKASAN");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(526, 40, 82, 17);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Nama Kegiatan", "Tanggal", "Waktu"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(176, 234, 508, 134);

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 3, 18)); // NOI18N
        jLabel6.setText("JADWAL BARU");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(200, 200, 180, 25);

        jLabel5.setText("Total User");

        jLabel7.setText("26");

        javax.swing.GroupLayout panelTotalUserLayout = new javax.swing.GroupLayout(panelTotalUser);
        panelTotalUser.setLayout(panelTotalUserLayout);
        panelTotalUserLayout.setHorizontalGroup(
            panelTotalUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalUserLayout.createSequentialGroup()
                .addGroup(panelTotalUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTotalUserLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel5))
                    .addGroup(panelTotalUserLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel7)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        panelTotalUserLayout.setVerticalGroup(
            panelTotalUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalUserLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel1.add(panelTotalUser);
        panelTotalUser.setBounds(203, 75, 100, 100);

        jLabel8.setText("Total Kegiatan");

        jLabel9.setText("13");

        javax.swing.GroupLayout panelTotalKegiatanLayout = new javax.swing.GroupLayout(panelTotalKegiatan);
        panelTotalKegiatan.setLayout(panelTotalKegiatanLayout);
        panelTotalKegiatanLayout.setHorizontalGroup(
            panelTotalKegiatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalKegiatanLayout.createSequentialGroup()
                .addGroup(panelTotalKegiatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTotalKegiatanLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel8))
                    .addGroup(panelTotalKegiatanLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel9)))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        panelTotalKegiatanLayout.setVerticalGroup(
            panelTotalKegiatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalKegiatanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel1.add(panelTotalKegiatan);
        panelTotalKegiatan.setBounds(321, 75, 100, 100);

        jLabel10.setText("Selesai");

        jLabel11.setText("10");

        javax.swing.GroupLayout panelSelesaiLayout = new javax.swing.GroupLayout(panelSelesai);
        panelSelesai.setLayout(panelSelesaiLayout);
        panelSelesaiLayout.setHorizontalGroup(
            panelSelesaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelesaiLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelSelesaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(panelSelesaiLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel11)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        panelSelesaiLayout.setVerticalGroup(
            panelSelesaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSelesaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel1.add(panelSelesai);
        panelSelesai.setBounds(439, 75, 100, 100);

        jLabel12.setText("Belum Selesai");

        jLabel13.setText("10");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel12))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel13)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(557, 75, 106, 100);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/WhatsApp Image 2026-06-26 at 17.30.46.jpeg"))); // NOI18N
        jPanel1.add(jLabel14);
        jLabel14.setBounds(3, 0, 690, 380);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 690, 390);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int konfirmasi = JOptionPane.showConfirmDialog(this, 
            "Yakin ingin keluar dari akun Admin?", 
            "Konfirmasi Logout", 
            JOptionPane.YES_NO_OPTION);
    
        if (konfirmasi == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            this.dispose(); 
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new LaporanKegiatanFrame().setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new KelolaJadwalFrame().setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new KelolaUserFrame().setVisible(true);
        this.dispose(); 
    }//GEN-LAST:event_jButton1ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new HalamanAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel panelSelesai;
    private javax.swing.JPanel panelTotalKegiatan;
    private javax.swing.JPanel panelTotalUser;
    // End of variables declaration//GEN-END:variables
}
