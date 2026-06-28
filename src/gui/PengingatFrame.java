package gui;
import entity.*;
import repository.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.awt.Color;
import java.awt.FlowLayout;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Dwi Sekar
 */
public class PengingatFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PengingatFrame.class.getName());
    CRUD crud = new CRUD();
    private int idPengguna;
    private java.util.List<Kegiatan> daftarPengingat;
    
    /**
     * Creates new form PengingatFrame
     */
    public PengingatFrame() {
        initComponents();
        setLocationRelativeTo(null);
        btnTandaiSelesai.addActionListener(this::btnTandaiSelesaiActionPerformed);
        btnBalik.addActionListener(this::btnBalikActionPerformed);
    }
    
    public PengingatFrame(int idPengguna) {
        this();
        this.idPengguna = idPengguna;
        tampilkanPengingat();
    }

    private void tampilkanPengingat() {
        PanelDaftar.removeAll();
        daftarPengingat = new ArrayList<>();
        crud.updateStatusOtomatis();

        try {
            ResultSet rs = crud.tampilSemuaKegiatan(idPengguna);
            SimpleDateFormat formatTgl = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatJam = new SimpleDateFormat("HH:mm");
            Date sekarang = new Date();

            while (rs.next()) {
                String status = rs.getString("status");
                if (!status.equalsIgnoreCase("Belum Selesai")) continue;

                Date tglKegiatan = formatTgl.parse(rs.getString("tanggal"));
                Date jamKegiatan = formatJam.parse(rs.getString("jam"));

                Calendar kalTgl = Calendar.getInstance();
                kalTgl.setTime(tglKegiatan);
                Calendar kalJam = Calendar.getInstance();
                kalJam.setTime(jamKegiatan);
                
                Calendar waktuKegiatan = Calendar.getInstance();
                waktuKegiatan.set(kalTgl.get(Calendar.YEAR), kalTgl.get(Calendar.MONTH), kalTgl.get(Calendar.DAY_OF_MONTH),
                                  kalJam.get(Calendar.HOUR_OF_DAY), kalJam.get(Calendar.MINUTE), 0);
                
                long selisihMs = waktuKegiatan.getTimeInMillis() - sekarang.getTime();
                long selisihHari = selisihMs / (1000 * 60 * 60 * 24);

                boolean tampil = false;
                if (selisihHari > 0 && selisihHari <= 3) {
                    tampil = true;
                } else if (selisihHari == 0 && selisihMs > 0) {
                    tampil = true;
                }

                if (tampil) {
                    Kegiatan k = new Kegiatan(
                        rs.getInt("id_kegiatan"),
                        rs.getInt("id_pengguna"),
                        rs.getString("nama_kegiatan"),
                        rs.getString("kategori"),
                        rs.getString("tanggal"),
                        rs.getString("jam"),
                        rs.getString("prioritas"),
                        rs.getString("keterangan"),
                        rs.getString("status")
                    );
                    daftarPengingat.add(k);
                }
            }

            daftarPengingat.sort((a, b) -> {
                int bandingTgl = a.getTanggal().compareTo(b.getTanggal());
                if (bandingTgl != 0) return bandingTgl;
                return a.getJam().compareTo(b.getJam());
            });

            if (daftarPengingat.isEmpty()) {
                PanelDaftar.add(new JLabel("Tidak ada jadwal yang mendekati waktunya"));
            } else {
                for (Kegiatan k : daftarPengingat) {
                    JPanel panelItem = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    panelItem.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
                    panelItem.setBackground(Color.WHITE);

                    Date tgl = formatTgl.parse(k.getTanggal());
                    Date jam = formatJam.parse(k.getJam());

                    Calendar kalTgl = Calendar.getInstance();
                    kalTgl.setTime(tgl);
                    Calendar kalJam = Calendar.getInstance();
                    kalJam.setTime(jam);
                    
                    Calendar waktuKegiatan = Calendar.getInstance();
                    waktuKegiatan.set(kalTgl.get(Calendar.YEAR), kalTgl.get(Calendar.MONTH), kalTgl.get(Calendar.DAY_OF_MONTH),
                                      kalJam.get(Calendar.HOUR_OF_DAY), kalJam.get(Calendar.MINUTE), 0);
                    
                    long selisihMs = waktuKegiatan.getTimeInMillis() - sekarang.getTime();

                    String sisaWaktu = "";
                    Color warnaTeks = Color.BLACK;

                    if (selisihMs < 0) {
                        sisaWaktu = "Sudah lewat";
                        warnaTeks = new Color(128, 128, 128);
                    } else {
                        long menit = selisihMs / (1000 * 60);
                        long jamSisa = menit / 60;
                        long hariSisa = jamSisa / 24;

                        if (hariSisa > 0) {
                            sisaWaktu = hariSisa + " hari lagi";
                            warnaTeks = Color.BLACK;
                        } else if (jamSisa > 0) {
                            long sisaMenit = menit % 60;
                            sisaWaktu = jamSisa + " jam " + sisaMenit + " menit lagi";
                            if (menit <= 30) {
                                warnaTeks = new Color(204, 0, 0);
                            } else {
                                warnaTeks = Color.BLACK;
                            }
                        } else {
                            sisaWaktu = menit + " menit lagi";
                            if (menit <= 30) {
                                warnaTeks = new Color(204, 0, 0);
                            } else {
                                warnaTeks = Color.BLACK;
                            }
                        }
                    }

                    String teks = String.format(
                        "<html><b>%s</b> | %s<br>Tanggal: %s | Jam: %s | Prioritas: %s | <span style='color:%s; font-weight:bold;'>%s</span></html>",
                        k.getNamaKegiatan(), k.getKategori(),
                        k.getTanggal(), k.getJam(), k.getPrioritas(),
                        "rgb(" + warnaTeks.getRed() + "," + warnaTeks.getGreen() + "," + warnaTeks.getBlue() + ")",
                        sisaWaktu
                    );

                    JLabel lbl = new JLabel(teks);
                    lbl.setOpaque(true);
                    lbl.setBackground(Color.WHITE);
                    panelItem.add(lbl);
                    PanelDaftar.add(panelItem);
                }
            }

            PanelDaftar.revalidate();
            PanelDaftar.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat pengingat: " + e.getMessage());
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
        lblJudul = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        lblDaftarPengingat = new javax.swing.JLabel();
        scrollDaftarPengingat = new javax.swing.JScrollPane();
        PanelDaftar = new javax.swing.JPanel();
        btnTandaiSelesai = new javax.swing.JButton();
        btnBalik = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(700, 420));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        lblJudul.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lblJudul.setForeground(new java.awt.Color(153, 153, 255));
        lblJudul.setText("Pengingat");
        jPanel1.add(lblJudul);
        lblJudul.setBounds(289, 26, 97, 26);
        jPanel1.add(jSeparator1);
        jSeparator1.setBounds(398, 42, 70, 10);
        jPanel1.add(jSeparator2);
        jSeparator2.setBounds(207, 42, 70, 10);
        jPanel1.add(jSeparator3);
        jSeparator3.setBounds(190, 58, 295, 10);

        lblDaftarPengingat.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        lblDaftarPengingat.setText("Daftar Pengingat");
        jPanel1.add(lblDaftarPengingat);
        lblDaftarPengingat.setBounds(120, 80, 150, 19);

        scrollDaftarPengingat.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollDaftarPengingat.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        PanelDaftar.setLayout(new javax.swing.BoxLayout(PanelDaftar, javax.swing.BoxLayout.Y_AXIS));
        scrollDaftarPengingat.setViewportView(PanelDaftar);

        jPanel1.add(scrollDaftarPengingat);
        scrollDaftarPengingat.setBounds(19, 113, 641, 218);

        btnTandaiSelesai.setBackground(new java.awt.Color(204, 255, 204));
        btnTandaiSelesai.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTandaiSelesai.setForeground(new java.awt.Color(0, 204, 0));
        btnTandaiSelesai.setText("Tandai Selesai");
        btnTandaiSelesai.addActionListener(this::btnTandaiSelesaiActionPerformed);
        jPanel1.add(btnTandaiSelesai);
        btnTandaiSelesai.setBounds(30, 349, 107, 23);

        btnBalik.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBalik.setText("Kembali");
        btnBalik.addActionListener(this::btnBalikActionPerformed);
        jPanel1.add(btnBalik);
        btnBalik.setBounds(585, 349, 75, 23);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/WhatsApp Image 2026-06-28 at 11.08.44.jpeg"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(-3, 0, 700, 390);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 691, 390);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTandaiSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTandaiSelesaiActionPerformed
        if (daftarPengingat == null || daftarPengingat.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Tidak ada jadwal untuk ditandai selesai!");
            return;
        }
        
        Kegiatan k = daftarPengingat.get(0);
        k.setStatus("Selesai");

        boolean sukses = crud.ubahKegiatan(k);

        if (sukses) {
            JOptionPane.showMessageDialog(null, "Jadwal berhasil ditandai selesai!");
            tampilkanPengingat();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal mengubah status!");
        }                                                                           
    }//GEN-LAST:event_btnTandaiSelesaiActionPerformed

    private void btnBalikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBalikActionPerformed
        dispose();
    }//GEN-LAST:event_btnBalikActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new PengingatFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDaftar;
    private javax.swing.JButton btnBalik;
    private javax.swing.JButton btnTandaiSelesai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblDaftarPengingat;
    private javax.swing.JLabel lblJudul;
    private javax.swing.JScrollPane scrollDaftarPengingat;
    // End of variables declaration//GEN-END:variables
}
