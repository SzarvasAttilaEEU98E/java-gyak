
package hu.nje.javagyakbead.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "eloadas")
public class Eloadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filmid")
    private Long filmId;

    @Column(name = "moziid")
    private Long moziId;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "nezoszam")
    private Integer nezoszam;

    @Column(name = "bevetel")
    private Long bevetel;

    public Eloadas() {
    }

    public Eloadas(Long filmId, Long moziId,
                   LocalDate datum, Integer nezoszam,
                   Long bevetel) {
        this.filmId = filmId;
        this.moziId = moziId;
        this.datum = datum;
        this.nezoszam = nezoszam;
        this.bevetel = bevetel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    public Long getMoziId() {
        return moziId;
    }

    public void setMoziId(Long moziId) {
        this.moziId = moziId;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Integer getNezoszam() {
        return nezoszam;
    }

    public void setNezoszam(Integer nezoszam) {
        this.nezoszam = nezoszam;
    }

    public Long getBevetel() {
        return bevetel;
    }

    public void setBevetel(Long bevetel) {
        this.bevetel = bevetel;
    }
}
