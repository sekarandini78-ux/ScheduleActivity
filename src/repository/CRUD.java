package repository;
import entity.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
