package repository;
import entity.*;
import java.sql.*;
import javax.swing.JOptionPane;

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

    // Konstruktor: Membuat koneksi ke database
    public CRUD() {
        koneksi = DbKoneksi.getKoneksi();
    }

    // ==================================================
    // BAGIAN 1: OPERASI DATA KEGIATAN / JADWAL
    // ==================================================

    /**
     * Menambahkan data kegiatan baru ke dalam database
     * @param k Objek kegiatan yang akan disimpan
     * @return true jika berhasil, false jika gagal
     */
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
            System.err.println("Gagal tambah kegiatan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Menampilkan semua kegiatan milik pengguna tertentu
     * @param idPengguna ID pengguna
     * @return ResultSet berisi daftar kegiatan
     */
    public ResultSet tampilSemuaKegiatan(int idPengguna) {
        String sql = "SELECT * FROM tb_kegiatan WHERE id_pengguna = ? ORDER BY tanggal ASC, jam ASC";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setInt(1, idPengguna);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal tampil semua kegiatan: " + e.getMessage());
            return null;
        }
    }

    /**
     * Mengambil kegiatan hari ini yang belum selesai
     * @param idPengguna ID pengguna
     * @param tglSekarang Tanggal saat ini
     * @return ResultSet berisi kegiatan hari ini
     */
    public ResultSet ambilHariIni(int idPengguna, String tglSekarang) {
        String sql = "SELECT * FROM tb_kegiatan WHERE id_pengguna = ? AND tanggal = ? AND status = 'Belum Selesai'";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setInt(1, idPengguna);
            ps.setString(2, tglSekarang);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal ambil kegiatan hari ini: " + e.getMessage());
            return null;
        }
    }

    /**
     * Mengambil satu data kegiatan berdasarkan ID kegiatan
     * @param idKegiatan ID kegiatan
     * @return ResultSet berisi data kegiatan
     */
    public ResultSet ambilSatuKegiatan(int idKegiatan) {
        String sql = "SELECT * FROM tb_kegiatan WHERE id_kegiatan = ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setInt(1, idKegiatan);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal ambil satu kegiatan: " + e.getMessage());
            return null;
        }
    }

    /**
     * Memperbarui data kegiatan yang sudah ada
     * @param k Objek kegiatan dengan data terbaru
     * @return true jika berhasil, false jika gagal
     */
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
            System.err.println("Gagal ubah kegiatan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Menghapus data kegiatan dari database
     * @param idKegiatan ID kegiatan yang akan dihapus
     * @return true jika berhasil, false jika gagal
     */
    public boolean hapusKegiatan(int idKegiatan) {
        String sql = "DELETE FROM tb_kegiatan WHERE id_kegiatan = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, idKegiatan);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal hapus kegiatan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mencari kegiatan berdasarkan kata kunci
     * @param idPengguna ID pengguna
     * @param kataKunci Kata yang dicari
     * @return ResultSet berisi hasil pencarian
     */
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
            System.err.println("Gagal cari kegiatan: " + e.getMessage());
            return null;
        }
    }

    /**
     * Mencari kegiatan untuk kebutuhan admin
     * @param kataKunci Kata yang dicari
     * @return ResultSet berisi hasil pencarian
     */
    public ResultSet cariKegiatanAdmin(String kataKunci) {
        String sql = "SELECT * FROM tb_kegiatan WHERE nama_kegiatan LIKE ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, "%" + kataKunci + "%");
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal cari kegiatan (admin): " + e.getMessage());
            return null;
        }
    }

    /**
     * Menampilkan semua kegiatan tanpa batasan pengguna (khusus admin)
     * @return ResultSet berisi semua kegiatan
     */
    public ResultSet tampilSemuaKegiatanAdmin() {
        String sql = "SELECT * FROM tb_kegiatan";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal tampil semua kegiatan (admin): " + e.getMessage());
            return null;
        }
    }

    /**
     * Mengedit data kegiatan dari sisi admin
     * @param idKegiatan ID kegiatan
     * @param namaKegiatan Nama kegiatan baru
     * @param kategori Kategori baru
     * @param tanggal Tanggal baru
     * @param jam Jam baru
     * @param prioritas Prioritas baru
     * @param status Status baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean editKegiatan(int idKegiatan, String namaKegiatan, String kategori, String tanggal, String jam, String prioritas, String status) {
        String sql = "UPDATE tb_kegiatan SET nama_kegiatan=?, kategori=?, tanggal=?, jam=?, prioritas=?, status=? WHERE id_kegiatan=?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, namaKegiatan);
            ps.setString(2, kategori);
            ps.setString(3, tanggal);
            ps.setString(4, jam);
            ps.setString(5, prioritas);
            ps.setString(6, status);
            ps.setInt(7, idKegiatan);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal edit kegiatan (admin): " + e.getMessage());
            return false;
        }
    }

    /**
     * Membuat laporan kegiatan berdasarkan rentang tanggal
     * @param tanggalAwal Tanggal mulai
     * @param tanggalAkhir Tanggal selesai
     * @return ResultSet berisi data laporan
     */
    public ResultSet laporanKegiatan(String tanggalAwal, String tanggalAkhir) {
        String sql = "SELECT * FROM tb_kegiatan WHERE tanggal BETWEEN ? AND ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, tanggalAwal);
            ps.setString(2, tanggalAkhir);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal buat laporan kegiatan: " + e.getMessage());
            return null;
        }
    }

    /**
     * Mengambil semua jadwal pada tanggal tertentu
     * @param idPengguna ID pengguna
     * @param tanggal Tanggal yang dicari
     * @return ResultSet berisi jadwal pada tanggal tersebut
     */
    public ResultSet ambilJadwalHariIni(int idPengguna, String tanggal) {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM tb_kegiatan WHERE id_pengguna = ? AND tanggal = ? ORDER BY jam ASC";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setInt(1, idPengguna);
            ps.setString(2, tanggal);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal ambil jadwal hari ini: " + e.getMessage());
        }
        return rs;
    }

    /**
     * Memperbarui status kegiatan secara otomatis jika sudah lewat waktu
     */
    public void updateStatusOtomatis() {
        String sql = "UPDATE tb_kegiatan " +
                     "SET status = 'Selesai' " +
                     "WHERE status = 'Belum Selesai' " +
                     "AND (tanggal < CURDATE() OR (tanggal = CURDATE() AND jam < CURTIME()))";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            int jumlah = ps.executeUpdate();
            if (jumlah > 0) {
                System.out.println("✅ Ada " + jumlah + " jadwal yang otomatis diubah menjadi Selesai");
            }
        } catch (SQLException e) {
            System.err.println("❌ Gagal update status otomatis: " + e.getMessage());
        }
    }

    // ==================================================
    // BAGIAN 2: OPERASI DATA PENGGUNA / USER
    // ==================================================

    /**
     * Memproses login pengguna
     * @param username Nama pengguna
     * @param sandi Kata sandi
     * @return Objek Pengguna jika berhasil, null jika gagal
     */
    public Pengguna login(String username, String sandi) {
        if (koneksi == null) {
            System.err.println("Koneksi database belum dibuka!");
            return null;
        }
        String sql = "SELECT * FROM tb_pengguna WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, sandi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_pengguna");
                String nama = rs.getString("nama_lengkap");
                String email = rs.getString("email");
                String hakAkses = rs.getString("hak_akses");

                if (hakAkses.equalsIgnoreCase("admin")) {
                    return new Admin(id, nama, username, email, sandi, hakAkses);
                } else {
                    return new User(id, nama, username, email, sandi, hakAkses);
                }
            }
        } catch (SQLException e) {
            System.err.println("Login gagal: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mengambil data lengkap pengguna berdasarkan ID
     * @param idPengguna ID pengguna
     * @return Objek Pengguna jika ditemukan, null jika tidak
     */
    public Pengguna ambilDataPengguna(int idPengguna) {
        String sql = "SELECT * FROM tb_pengguna WHERE id_pengguna = ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setInt(1, idPengguna);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id_pengguna");
                String nama = rs.getString("nama_lengkap");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String sandi = rs.getString("password");
                String hakAkses = rs.getString("hak_akses");

                if (hakAkses.equalsIgnoreCase("admin")) {
                    return new Admin(id, nama, username, email, sandi, hakAkses);
                } else {
                    return new User(id, nama, username, email, sandi, hakAkses);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal ambil data pengguna: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mendaftarkan pengguna baru ke dalam database
     * @param user Objek pengguna baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean registerUser(User user) {
        String sql = "INSERT INTO tb_pengguna (nama_lengkap, username, email, password, hak_akses) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, user.getNama());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getSandi());
            ps.setString(5, user.getHakAkses());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Registrasi gagal: " + e.getMessage());
            return false;
        }
    }

    /**
     * Menampilkan semua data pengguna
     * @return ResultSet berisi daftar pengguna
     */
    public ResultSet tampilSemuaUser() {
        String sql = "SELECT * FROM tb_pengguna";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal tampil semua pengguna: " + e.getMessage());
            return null;
        }
    }

    /**
     * Menghapus data pengguna
     * @param idPengguna ID pengguna yang akan dihapus
     * @return true jika berhasil, false jika gagal
     */
    public boolean hapusUser(int idPengguna) {
        String sql = "DELETE FROM tb_pengguna WHERE id_pengguna=?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setInt(1, idPengguna);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal hapus pengguna: " + e.getMessage());
            return false;
        }
    }

    /**
     * Memperbarui data pengguna
     * @param idPengguna ID pengguna
     * @param namaLengkap Nama lengkap baru
     * @param username Nama pengguna baru
     * @param email Email baru
     * @param hakAkses Hak akses baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean editUser(int idPengguna, String namaLengkap, String username, String email, String hakAkses) {
        String sql = "UPDATE tb_pengguna SET nama_lengkap=?, username=?, email=?, hak_akses=? WHERE id_pengguna=?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, namaLengkap);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, hakAkses);
            ps.setInt(5, idPengguna);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Gagal edit pengguna: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mencari pengguna berdasarkan nama atau username
     * @param kataKunci Kata yang dicari
     * @return ResultSet berisi hasil pencarian
     */
    public ResultSet cariUser(String kataKunci) {
        String sql = "SELECT * FROM tb_pengguna WHERE nama_lengkap LIKE ? OR username LIKE ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, "%" + kataKunci + "%");
            ps.setString(2, "%" + kataKunci + "%");
            return ps.executeQuery();
        } catch (SQLException e) {
            System.err.println("Gagal cari pengguna: " + e.getMessage());
            return null;
        }
    }

    /**
     * Mencari ID pengguna berdasarkan username
     * @param username Nama pengguna
     * @return ID pengguna jika ditemukan, 0 jika tidak
     */
    public int cariIdPenggunaDariUsername(String username) {
        String sql = "SELECT id_pengguna FROM tb_pengguna WHERE username = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_pengguna");
            }
        } catch (SQLException e) {
            System.err.println("Gagal cari ID pengguna: " + e.getMessage());
        }
        return 0;
    }

    // ==================================================
    // BAGIAN 3: OPERASI PASSWORD
    // ==================================================

    /**
     * Mengubah password dengan verifikasi password lama
     * @param idPengguna ID pengguna
     * @param passLama Password lama
     * @param passBaru Password baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean ubahPasswordDenganLama(int idPengguna, String passLama, String passBaru) {
        String sql = "UPDATE tb_pengguna SET password = ? WHERE id_pengguna = ? AND password = ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, passBaru);
            ps.setInt(2, idPengguna);
            ps.setString(3, passLama);
            int hasil = ps.executeUpdate();
            return hasil > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Mengubah password secara langsung tanpa cek password lama (khusus admin)
     * @param idPengguna ID pengguna
     * @param passBaru Password baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean ubahPasswordLangsung(int idPengguna, String passBaru) {
        String sql = "UPDATE tb_pengguna SET password = ? WHERE id_pengguna = ?";
        try {
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, passBaru);
            ps.setInt(2, idPengguna);
            int hasil = ps.executeUpdate();
            return hasil > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==================================================
    // BAGIAN 4: PENGATURAN & NOTIFIKASI
    // ==================================================

    /**
     * Menyimpan pengaturan notifikasi pengguna
     * @param idPengguna ID pengguna
     * @param aktif Status aktif/tidaknya notifikasi
     * @param menit Waktu pengingat dalam menit
     * @return true jika berhasil, false jika gagal
     */
    public boolean simpanPengaturanNotifikasi(int idPengguna, boolean aktif, int menit) {
        String sql = "UPDATE tb_pengguna SET aktif_notifikasi = ?, waktu_pengingat = ? WHERE id_pengguna = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setBoolean(1, aktif);
            ps.setInt(2, menit);
            ps.setInt(3, idPengguna);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan pengaturan: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mengambil pengaturan notifikasi pengguna
     * @param idPengguna ID pengguna
     * @return Array berisi status aktif dan waktu pengingat
     */
    public Object[] ambilPengaturanNotifikasi(int idPengguna) {
        String sql = "SELECT aktif_notifikasi, waktu_pengingat FROM tb_pengguna WHERE id_pengguna = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, idPengguna);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Object[]{rs.getBoolean("aktif_notifikasi"), rs.getInt("waktu_pengingat")};
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil pengaturan: " + e.getMessage());
        }
        // Nilai default jika tidak ditemukan
        return new Object[]{false, 15};
    }
}