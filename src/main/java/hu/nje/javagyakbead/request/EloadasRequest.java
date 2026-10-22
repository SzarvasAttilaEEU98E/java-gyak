
package hu.nje.javagyakbead.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class EloadasRequest {

    private Long id;

    @NotNull(message = "A film kiválasztása kötelező.")
    private Long filmId;

    @NotNull(message = "A mozi kiválasztása kötelező.")
    private Long moziId;

    @NotNull(message = "Az előadás dátuma kötelező.")
    private LocalDate datum;

    @NotNull(message = "A nézőszám megadása kötelező.")
    @Min(value = 0, message = "A nézőszám nem lehet negatív.")
    private Integer nezoszam;

    @NotNull(message = "A bevétel megadása kötelező.")
    @Min(value = 0, message = "A bevétel nem lehet negatív.")
    private Long bevetel;

    public EloadasRequest() {
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
