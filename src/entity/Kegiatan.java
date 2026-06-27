package entity;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dwi Sekar
 */
public class Kegiatan {
    private int idKegiatan;
    private int idPengguna;
    private String namaKegiatan;
    private String kategori;
    private String tanggal;
    private String jam;
    private String prioritas;
    private String keterangan;
    private String status;

    public Kegiatan(int idKegiatan, int idPengguna, String namaKegiatan, String kategori, String tanggal, String jam, String prioritas, String keterangan, String status) {
        this.idKegiatan = idKegiatan;
        this.idPengguna = idPengguna;
        this.namaKegiatan = namaKegiatan;
        this.kategori = kategori;
        this.tanggal = tanggal;
        this.jam = jam;
        this.prioritas = prioritas;
        this.keterangan = keterangan;
        this.status = status;
    }
    public Kegiatan() {
    }
    
    public int getIdKegiatan() {
        return idKegiatan;
    }
    public void setIdKegiatan(int idKegiatan) {
        this.idKegiatan = idKegiatan;
    }

    public int getIdPengguna() {
        return idPengguna;
    }
    public void setIdPengguna(int idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }
    public void setNamaKegiatan(String namaKegiatan) {
        this.namaKegiatan = namaKegiatan;
    }

    public String getKategori() {
        return kategori;
    }
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getTanggal() {
        return tanggal;
    }
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }
    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getPrioritas() {
        return prioritas;
    }
    public void setPrioritas(String prioritas) {
        this.prioritas = prioritas;
    }

    public String getKeterangan() {
        return keterangan;
    }
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return idKegiatan + " | " + namaKegiatan + " | " + tanggal + " " + jam + " | " + status;
    }
}
