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
    private String namaKegiatan;
    private String tanggal;
    private String waktu;
    private String tempat;
    private String status;

    public Kegiatan(int idKegiatan, String namaKegiatan, String tanggal, String waktu, String tempat, String status) {
        this.idKegiatan = idKegiatan;
        this.namaKegiatan = namaKegiatan;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.tempat = tempat;
        this.status = status;
    }

    public int getIdKegiatan() { return idKegiatan; }
    public void setIdKegiatan(int idKegiatan) { this.idKegiatan = idKegiatan; }
    public String getNamaKegiatan() { return namaKegiatan; }
    public void setNamaKegiatan(String namaKegiatan) { this.namaKegiatan = namaKegiatan; }
    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
    public String getWaktu() { return waktu; }
    public void setWaktu(String waktu) { this.waktu = waktu; }
    public String getTempat() { return tempat; }
    public void setTempat(String tempat) { this.tempat = tempat; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return idKegiatan + " | " + namaKegiatan + " | " + tanggal + " | " + waktu + " | " + tempat + " | " + status;
    }
}
