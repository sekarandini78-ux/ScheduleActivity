package repository;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dwi Sekar
 */
public interface Pengelolaan {
    void tambahData();
    void tampilData();
    void ubahData();
    void hapusData();

    default void infoAplikasi() {
        System.out.println("=== Aplikasi Pengelolaan Jadwal Kegiatan ===");
    }

    static void pesanSelesai() {
        System.out.println("Operasi selesai dilakukan");
    }
}
