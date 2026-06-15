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
        String sql = "INSERT INTO kegiatan (nama, tanggal, waktu, tempat, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, k.getNamaKegiatan());
            ps.setString(2, k.getTanggal());
            ps.setString(3, k.getWaktu());
            ps.setString(4, k.getTempat());
            ps.setString(5, k.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal tambah: " + e.getMessage());
            return false;
        }
    }

    public void tampilSemuaKegiatan() {
        String sql = "SELECT * FROM kegiatan";
        try (PreparedStatement ps = koneksi.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(new Kegiatan(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("tanggal"),
                    rs.getString("waktu"),
                    rs.getString("tempat"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Gagal tampil: " + e.getMessage());
        }
    }

}
