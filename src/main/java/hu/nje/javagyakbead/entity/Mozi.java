
package hu.nje.javagyakbead.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mozi")
public class Mozi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nev")
    private String nev;

    @Column(name = "varos")
    private String varos;

    @Column(name = "ferohely")
    private Integer ferohely;

    public Mozi() {
    }

    public Mozi(String nev, String varos, Integer ferohely) {
        this.nev = nev;
        this.varos = varos;
        this.ferohely = ferohely;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getVaros() {
        return varos;
    }

    public void setVaros(String varos) {
        this.varos = varos;
    }

    public Integer getFerohely() {
        return ferohely;
    }

    public void setFerohely(Integer ferohely) {
        this.ferohely = ferohely;
    }
}
