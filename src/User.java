/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dwi Sekar
 */
    public class User extends Pengguna implements Pengelolaan {
        
    public User(int id, String nama, String email, String sandi, String hakAkses) {
        super(id, nama, email, sandi, hakAkses);
    }

    @Override
    public void tampilProfil() {
        System.out.println("Profil Pengguna");
        System.out.println("ID       : " + getId());
        System.out.println("Nama     : " + getNama());
        System.out.println("Email    : " + getEmail());
        System.out.println("Hak Akses: " + getHakAkses());
    }

    @Override
    public void tambahData() {
        System.out.println("Pengguna mengajukan jadwal baru");
    }

    @Override
    public void tampilData() {
        System.out.println("Pengguna melihat jadwal miliknya");
    }

    @Override
    public void ubahData() {
        System.out.println("Pengguna tidak memiliki akses ubah data");
    }

    @Override
    public void hapusData() {
        System.out.println("Pengguna tidak memiliki akses hapus data");
    }
}
