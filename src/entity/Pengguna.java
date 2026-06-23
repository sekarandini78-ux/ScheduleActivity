package entity;

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
    private String username;
    private String email;
    private String sandi;
    private String hakAkses;

    public Pengguna(int id, String nama, String username, String email, String sandi, String hakAkses) {
        this.id = id;
        this.nama = nama;
        this.username = username;
        this.email = email;
        this.sandi = sandi;
        this.hakAkses = hakAkses;
    }

    public Pengguna(String nama, String username, String email) {
        this.nama = nama;
        this.username = username;
        this.email = email;
    }
    
    public int getId() { 
        return id; 
    }
    public void setId(int id) { 
        this.id = id; 
    }
    public String getNama() { 
        return nama; 
    }
    public void setNama(String nama) { 
        this.nama = nama; 
    }
    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }
    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }
    public String getSandi() { 
        return sandi; 
    }
    public void setSandi(String sandi) { 
        this.sandi = sandi; 
    }
    public String getHakAkses() { 
        return hakAkses; 
    }
    public void setHakAkses(String hakAkses) { 
        this.hakAkses = hakAkses; 
    }

    public void tampilProfil(){
    System.out.println("Data Awal Pengguna:");
    System.out.println("ID   : " + getId());
    System.out.println("Nama : " + getNama());
    System.out.println("Username : " + getUsername());
    System.out.println("Email    : " + getEmail());
    }
    
  public static void main(String[] args) {
    Pengguna akunAdmin = new Admin(1, "Dwi Sekar Andini", "sekar", "dwi.sekar@mail.com", "admin123", "admin");

    if (akunAdmin instanceof Admin) {
        Admin admin = (Admin) akunAdmin;
        admin.tambahData();
        admin.ubahData("Proyek Akhir", "2026-06-15"); 
    }

    Pengguna akunUser = new User(2, "Adelia Dwi Arafah", "adelia", "adelia.dwi@mail.com", "user123", "user");

    if (akunUser instanceof User) {
        User user = (User) akunUser;
        user.tampilData();
    }

    System.out.println("\n=== Data Profil Pengguna ===");
    Admin profil = new Admin(1, "Dwi Sekar Andini", "sekar", "dwi.sekar@mail.com", "admin123", "admin");
    profil.tampilProfil();
    }
}
