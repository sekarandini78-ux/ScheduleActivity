package gui;
import entity.*;
import repository.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.time.*;
import java.time.temporal.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.text.SimpleDateFormat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author rafiqi
 */
public class HalamanUtama extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HalamanUtama.class.getName());
    private Pengguna penggunaAktif;
    /**
     * Creates new form HalamanUtama
     */
    
    public HalamanUtama(Pengguna pengguna) {
        initComponents();
        this.penggunaAktif = pengguna;
        setLocationRelativeTo(null);
        jLabel2.setText("Selamat Datang, " + penggunaAktif.getNama());
        tampilJadwalHariIni();
        tampilPengingatTerdekat();
        mulaiNotifikasiOtomatis();
    }
    
    public HalamanUtama() {
        initComponents();
        setLocationRelativeTo(null);
        setTitle("MySchedule");
    }
    
    private void mulaiNotifikasiOtomatis() {
        new javax.swing.Timer(30_000, e -> {
            CRUD crud = new CRUD();
            crud.updateStatusOtomatis();
            cekJadwalUntukNotifikasi();
        }).start();
    }

    private void cekJadwalUntukNotifikasi() {
        CRUD crud = new CRUD();
        Object[] pengaturan = crud.ambilPengaturanNotifikasi(penggunaAktif.getId());

        boolean aktif = (boolean) pengaturan[0];
        int batasMenit = (int) pengaturan[1];

        System.out.println("=== Cek Notifikasi ===");
        System.out.println("Notifikasi aktif: " + aktif + " | Waktu pengingat: " + batasMenit + " menit");

        if (!aktif) {
            System.out.println("Notifikasi dimatikan, tidak ada yang dicek");
            return;
        }

        try {
            ResultSet rs = crud.tampilSemuaKegiatan(penggunaAktif.getId());
            LocalDateTime sekarang = LocalDateTime.now();
            System.out.println("Waktu sekarang: " + sekarang);

            while (rs.next()) {
                String status = rs.getString("status");
                if (!"Belum Selesai".equalsIgnoreCase(status)) continue;

                String tgl = rs.getString("tanggal");
                String jam = rs.getString("jam");
                System.out.println("Cek jadwal: " + tgl + " " + jam);

                LocalDateTime waktuKeg;
                try {
                    waktuKeg = LocalDateTime.of(LocalDate.parse(tgl), LocalTime.parse(jam));
                } catch (Exception e) {
                    waktuKeg = LocalDateTime.of(LocalDate.parse(tgl), LocalTime.parse(jam + ":00"));
                }

                long sisaMenit = ChronoUnit.MINUTES.between(sekarang, waktuKeg);
                System.out.println("Sisa waktu: " + sisaMenit + " menit");

                if (sisaMenit > 0 && sisaMenit <= batasMenit) {
                    System.out.println("WAKTUNYA! Tampilkan notifikasi sekarang");
                    tampilkanPesanNotifikasi(rs.getString("nama_kegiatan"), sisaMenit);
                }
            }
            rs.close();

        } catch (Exception ex) {
            System.out.println("Terjadi kesalahan: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void tampilkanPesanNotifikasi(String nama, long sisa) {
    // Ukuran diperbesar lagi: lebar 430, tinggi 160
    JWindow notif = new JWindow();
    notif.setSize(430, 160);
    notif.setLayout(new BorderLayout(10, 10));
    notif.setAlwaysOnTop(true);

    Dimension ukuranLayar = Toolkit.getDefaultToolkit().getScreenSize();
    int posisiX = ukuranLayar.width - notif.getWidth() - 20;
    int posisiY = ukuranLayar.height - notif.getHeight() - 50;
    notif.setLocation(posisiX, posisiY);

    JPanel panelUtama = new JPanel();
    panelUtama.setBackground(new Color(30, 38, 54));
    panelUtama.setLayout(new BorderLayout(12, 8));
    panelUtama.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));

    JPanel panelAtas = new JPanel(new BorderLayout(10, 0));
    panelAtas.setOpaque(false);

    JLabel ikonLonceng = new JLabel("🔔");
    ikonLonceng.setFont(new Font("Segoe UI", Font.PLAIN, 22));
    ikonLonceng.setForeground(Color.WHITE);

    JLabel teksJudul = new JLabel("Pengingat Jadwal");
    teksJudul.setFont(new Font("Segoe UI", Font.BOLD, 16));
    teksJudul.setForeground(Color.WHITE);

    JButton tombolTutup = new JButton("×");
    tombolTutup.setFont(new Font("Segoe UI", Font.BOLD, 18));
    tombolTutup.setForeground(Color.WHITE);
    tombolTutup.setOpaque(false);
    tombolTutup.setBorderPainted(false);
    tombolTutup.setContentAreaFilled(false);
    tombolTutup.setFocusPainted(false);
    tombolTutup.addActionListener(e -> notif.dispose());

    panelAtas.add(ikonLonceng, BorderLayout.WEST);
    panelAtas.add(teksJudul, BorderLayout.CENTER);
    panelAtas.add(tombolTutup, BorderLayout.EAST);

    
    JLabel teksIsi = new JLabel(
    "<html>" +
    "🌻 Semangat terus ya!<br>" +
    "<b>" + nama + "</b><br>" +
    "Segera dimulai dalam " + sisa + " menit lagi 🚀<br>" +
    "Semoga hari ini menyenangkan dan lancar semua! 😊" +
    "</html>"
);
    teksIsi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    teksIsi.setForeground(new Color(220, 225, 235));

    panelUtama.add(panelAtas, BorderLayout.NORTH);
    panelUtama.add(teksIsi, BorderLayout.CENTER);
    notif.add(panelUtama);

    notif.setVisible(true);

    new javax.swing.Timer(8000, aksi -> {
        notif.dispose();
        ((javax.swing.Timer) aksi.getSource()).stop();
    }).start();

    System.out.println("✅ Notifikasi pojok berhasil ditampilkan");
}
    
    private void tampilJadwalHariIni() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        CRUD crud = new CRUD();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalSekarang = sdf.format(new Date());

        try {
            ResultSet rs = crud.ambilJadwalHariIni(penggunaAktif.getId(), tanggalSekarang);
            int nomorUrut = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                    nomorUrut++,
                    rs.getString("nama_kegiatan"),
                    rs.getString("tanggal"),
                    rs.getString("jam"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat jadwal: " + e.getMessage());
        }
    }
    
    private void tampilPengingatTerdekat() {
        jPanel2.removeAll();
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        CRUD crud = new CRUD();
        LocalDateTime sekarang = LocalDateTime.now();

        String tampilan = "";
        long selisihTerkecil = Long.MAX_VALUE;

        try {
            crud.updateStatusOtomatis();
            ResultSet rs = crud.tampilSemuaKegiatan(penggunaAktif.getId());

            while (rs.next()) {
                String status = rs.getString("status");
                if (!"Belum Selesai".equalsIgnoreCase(status)) continue;

                String nama = rs.getString("nama_kegiatan");
                String kategori = rs.getString("kategori");
                String tgl = rs.getString("tanggal");
                String jam = rs.getString("jam");
                String prioritas = rs.getString("prioritas");

                LocalDateTime waktuKegiatan = LocalDateTime.of(
                    LocalDate.parse(tgl),
                    LocalTime.parse(jam)
                );

                if (waktuKegiatan.isBefore(sekarang)) continue;

                long selisihMenit = ChronoUnit.MINUTES.between(sekarang, waktuKegiatan);

                if (selisihMenit < selisihTerkecil) {
                    selisihTerkecil = selisihMenit;

                    String sisaWaktu;
                    String warna;

                    if (selisihMenit <= 30) {
                        sisaWaktu = selisihMenit + " menit lagi";
                        warna = "red";
                    } else if (selisihMenit < 1440) {
                        long j = selisihMenit / 60;
                        long m = selisihMenit % 60;
                        sisaWaktu = j + " jam " + m + " menit lagi";
                        warna = "black";
                    } else {
                        long h = selisihMenit / (60 * 24);
                        sisaWaktu = h + " hari lagi";
                        warna = "black";
                    }

                    tampilan = String.format(
                        "<html><b>%s | %s</b><br>Tanggal: %s | Jam: %s | Prioritas: %s | <span style='color:%s; font-weight:bold;'>%s</span></html>",
                        nama, kategori, tgl, jam, prioritas, warna, sisaWaktu
                    );
                }
            }

            rs.close();

            if (selisihTerkecil == Long.MAX_VALUE) {
                jPanel2.add(new JLabel("Tidak ada jadwal mendatang"));
            } else {
                JLabel lbl = new JLabel(tampilan);
                lbl.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
                jPanel2.add(lbl);
            }

            jPanel2.revalidate();
            jPanel2.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat pengingat: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * 
     * 
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 204, 204));
        setMaximumSize(new java.awt.Dimension(589, 410));
        setMinimumSize(new java.awt.Dimension(694, 426));
        setResizable(false);
        setSize(new java.awt.Dimension(589, 410));
        getContentPane().setLayout(null);

        jPanel3.setBackground(new java.awt.Color(1, 1, 107));
        jPanel3.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 3, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Selamat Datang, User");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(6, 26, 161, 20);

        jButton1.setText("Tambah Jadwal");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        jPanel3.add(jButton1);
        jButton1.setBounds(16, 208, 130, 23);

        jButton2.setText("Lihat Jadwal");
        jButton2.addActionListener(this::jButton2ActionPerformed);
        jPanel3.add(jButton2);
        jButton2.setBounds(16, 249, 130, 23);

        jButton3.setText("Pengingat");
        jButton3.addActionListener(this::jButton3ActionPerformed);
        jPanel3.add(jButton3);
        jButton3.setBounds(16, 290, 130, 23);

        jButton4.setText("Riwayat");
        jButton4.addActionListener(this::jButton4ActionPerformed);
        jPanel3.add(jButton4);
        jButton4.setBounds(16, 165, 130, 23);

        jButton5.setText("Pengaturan");
        jButton5.addActionListener(this::jButton5ActionPerformed);
        jPanel3.add(jButton5);
        jButton5.setBounds(16, 124, 130, 23);

        jButton6.setText("Logout");
        jButton6.addActionListener(this::jButton6ActionPerformed);
        jPanel3.add(jButton6);
        jButton6.setBounds(50, 350, 111, 23);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("MENU");
        jPanel3.add(jLabel4);
        jLabel4.setBounds(16, 89, 42, 17);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel1.setLayout(null);

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
        jScrollPane1.setBounds(30, 100, 460, 143);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setText("Pengelola Schedule Activity");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(40, 30, 321, 28);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Jadwal Hari Ini");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(40, 70, 101, 17);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("PENGINGAT TERDEKAT");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 260, 158, 17);

        jPanel2.setBackground(new java.awt.Color(1, 1, 107));
        jPanel2.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 78, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 290, 480, 78);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/WhatsApp Image 2026-06-26 at 17.30.46.jpeg"))); // NOI18N
        jPanel1.add(jLabel6);
        jLabel6.setBounds(-170, 0, 681, 400);

        jPanel3.add(jPanel1);
        jPanel1.setBounds(178, 0, 500, 390);

        getContentPane().add(jPanel3);
        jPanel3.setBounds(0, 0, 680, 390);
        jPanel3.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        TambahJadwalFrame tambah = new TambahJadwalFrame(penggunaAktif.getId());
        tambah.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        tambah.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                setVisible(true); // Tampilkan kembali halaman utama
                tampilJadwalHariIni();
                tampilPengingatTerdekat();
            }
        });
        tambah.setVisible(true);    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
        LihatJadwalFrame lihat = new LihatJadwalFrame(penggunaAktif.getId());
        lihat.setVisible(true);
         
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.setVisible(false);
        PengingatFrame pengingat = new PengingatFrame(penggunaAktif.getId());
        pengingat.setVisible(true);
        pengingat.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                setVisible(true);
                tampilJadwalHariIni();
                tampilPengingatTerdekat();
            }
        }); 
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.setVisible(false);
        PengaturanFrame pengaturan = new PengaturanFrame(penggunaAktif);
        pengaturan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        pengaturan.setVisible(true);
        pengaturan.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                setVisible(true);
            }
        });
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int tanya = JOptionPane.showConfirmDialog(this, "Yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (tanya == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.setVisible(false);
        RiwayatKegiatanFrame riwayat = new RiwayatKegiatanFrame(penggunaAktif.getId());
        riwayat.setVisible(true);
        riwayat.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                setVisible(true);
                tampilJadwalHariIni();
                tampilPengingatTerdekat();
            }
        });
    }//GEN-LAST:event_jButton4ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new HalamanUtama().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
