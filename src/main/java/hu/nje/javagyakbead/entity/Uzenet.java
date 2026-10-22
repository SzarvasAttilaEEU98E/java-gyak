
package hu.nje.javagyakbead.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "uzenet")
public class Uzenet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nev;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 150)
    private String targy;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String szoveg;

    @Column(name = "kuldes_ideje", nullable = false)
    private LocalDateTime kuldesIdeje;

    public Uzenet() {
    }

    @PrePersist
    public void onCreate() {
        if (kuldesIdeje == null) {
            kuldesIdeje = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTargy() {
        return targy;
    }

    public void setTargy(String targy) {
        this.targy = targy;
    }

    public String getSzoveg() {
        return szoveg;
    }

    public void setSzoveg(String szoveg) {
        this.szoveg = szoveg;
    }

    public LocalDateTime getKuldesIdeje() {
        return kuldesIdeje;
    }

    public void setKuldesIdeje(LocalDateTime kuldesIdeje) {
        this.kuldesIdeje = kuldesIdeje;
    }
}
