package repository;
import entity.*;
import java.sql.*;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dwi Sekar
 */

public class CRUD {
    private Connection koneksi;

    public CRUD() {
        koneksi = DbKoneksi.getKoneksi();
    }

    public boolean tambahKegiatan(Kegiatan k) {
        String sql = "INSERT INTO tb_kegiatan (id_pengguna, nama_kegiatan, kategori, tanggal, jam, prioritas, keterangan, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, k.getIdPengguna());
            ps.setString(2, k.getNamaKegiatan());
            ps.setString(3, k.getKategori());
            ps.setString(4, k.getTanggal());
            ps.setString(5, k.getJam());
            ps.setString(6, k.getPrioritas());
            ps.setString(7, k.getKeterangan());
            ps.setString(8, k.getStatus());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal tambah: " + e.getMessage());
            return false;
        }
    }

    public ResultSet tampilSemuaKegiatan(int idPengguna) {
        String sql = "SELECT * FROM tb_kegiatan WHERE id_pengguna = ? ORDER BY tanggal ASC, jam ASC";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setInt(1, idPengguna);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal tampil: " + e.getMessage());
            return null;
        }
    }

    public ResultSet ambilHariIni(int idPengguna, String tglSekarang) {
        String sql = "SELECT * FROM tb_kegiatan WHERE id_pengguna = ? AND tanggal = ? AND status = 'Belum Selesai'";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setInt(1, idPengguna);
            ps.setString(2, tglSekarang);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal ambil hari ini: " + e.getMessage());
            return null;
        }
    }
    
    // =======================================
    // TAMBAHAN UNTUK FITUR EDIT / UBAH JADWAL
    // Dipakai di: TambahJadwalFrame.java
    // =======================================
    
    public ResultSet ambilSatuKegiatan(int idKegiatan) {
        String sql = "SELECT * FROM tb_kegiatan WHERE id_kegiatan = ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setInt(1, idKegiatan);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal ambil satu data: " + e.getMessage());
            return null;
        }
    }
    
    public boolean ubahKegiatan(Kegiatan k) {
        String sql = "UPDATE tb_kegiatan SET nama_kegiatan=?, kategori=?, tanggal=?, jam=?, prioritas=?, keterangan=?, status=? WHERE id_kegiatan = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, k.getNamaKegiatan());
            ps.setString(2, k.getKategori());
            ps.setString(3, k.getTanggal());
            ps.setString(4, k.getJam());
            ps.setString(5, k.getPrioritas());
            ps.setString(6, k.getKeterangan());
            ps.setString(7, k.getStatus());
            ps.setInt(8, k.getIdKegiatan());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal ubah data: " + e.getMessage());
            return false;
        }
    }

    // =======================================
    // TAMBAHAN UNTUK FITUR HAPUS JADWAL
    // Dipakai di: LihatJadwalFrame.java
    // =======================================
    
    public boolean hapusKegiatan(int idKegiatan) {
        String sql = "DELETE FROM tb_kegiatan WHERE id_kegiatan = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, idKegiatan);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal hapus data: " + e.getMessage());
            return false;
        }
    }

    // =======================================
    // TAMBAHAN UNTUK FITUR CARI JADWAL
    // Dipakai di: LihatJadwalFrame.java
    // =======================================
    
    public ResultSet cariKegiatan(int idPengguna, String kataKunci) {
        String sql = "SELECT * FROM tb_kegiatan WHERE id_pengguna = ? AND (nama_kegiatan LIKE ? OR kategori LIKE ? OR status LIKE ?) ORDER BY tanggal, jam";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setInt(1, idPengguna);
            String kunci = "%" + kataKunci + "%";
            ps.setString(2, kunci);
            ps.setString(3, kunci);
            ps.setString(4, kunci);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal cari data: " + e.getMessage());
            return null;
        }
    }
    
    public boolean ubahPassword(int idPengguna, String passLama, String passBaru) {
    String sql = "UPDATE pengguna SET password = ? WHERE id_pengguna = ? AND password = ?";
    try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
        ps.setString(1, passBaru);
        ps.setInt(2, idPengguna);
        ps.setString(3, passLama);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

    
    // =======================================
    // TAMBAHAN UNTUK FITUR UBAH PASSWORD
    // Dipakai di: PengaturanFrame.java
    // =======================================
    
    public boolean ubahPasswordDenganLama(int idPengguna, String passLama, String passBaru) {
        String sql = "UPDATE pengguna SET password = ? WHERE id_pengguna = ? AND password = ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, passBaru);
            ps.setInt(2, idPengguna);
            ps.setString(3, passLama);
            int hasil = ps.executeUpdate();
            ps.close();
            return hasil > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ubahPassword(int idPengguna, String passBaru) {
        String sql = "UPDATE pengguna SET password = ? WHERE id_pengguna = ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, passBaru);
            ps.setInt(2, idPengguna);
            int hasil = ps.executeUpdate();
            ps.close();
            return hasil > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
