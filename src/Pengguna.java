/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dwi Sekar
 */
public abstract class Pengguna {
    private int id;
    private String nama;
    private String email;
    private String sandi;
    private String hakAkses;

    public Pengguna(int id, String nama, String email, String sandi, String hakAkses) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.sandi = sandi;
        this.hakAkses = hakAkses;
    }

    public Pengguna(String nama, String email) {
        this.nama = nama;
        this.email = email;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSandi() { return sandi; }
    public void setSandi(String sandi) { this.sandi = sandi; }
    public String getHakAkses() { return hakAkses; }
    public void setHakAkses(String hakAkses) { this.hakAkses = hakAkses; }

    public abstract void tampilProfil();   
}
