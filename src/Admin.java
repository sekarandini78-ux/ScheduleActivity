/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dwi Sekar
 */
 public class Admin extends Pengguna implements Pengelolaan {
     
    public Admin(int id, String nama, String email, String sandi, String hakAkses) {
        super(id, nama, email, sandi, hakAkses);
    }

    @Override
    public void tampilProfil() {
        System.out.println("Profil Admin");
        System.out.println("ID       : " + getId());
        System.out.println("Nama     : " + getNama());
        System.out.println("Email    : " + getEmail());
        System.out.println("Hak Akses: " + getHakAkses());
    }

    @Override
    public void tambahData() {
        System.out.println("Admin menambahkan jadwal baru");
    }

    @Override
    public void tampilData() {
        System.out.println("Admin melihat semua jadwal kegiatan");
    }

    @Override
    public void ubahData() {
        System.out.println("Admin mengubah data jadwal");
    }

    @Override
    public void hapusData() {
        System.out.println("Admin menghapus data jadwal");
    }
    public void ubahData(String keterangan) {
        System.out.println("Mengubah: " + keterangan);
    }
    public void ubahData(String keterangan, String tanggal) {
        System.out.println("Mengubah: " + keterangan + " | Tanggal: " + tanggal);
    }   
}
