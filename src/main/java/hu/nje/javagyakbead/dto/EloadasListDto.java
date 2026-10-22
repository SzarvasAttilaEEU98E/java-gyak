
package hu.nje.javagyakbead.dto;

import java.time.LocalDate;

public class EloadasListDto {

    private Long id;
    private String filmCim;
    private String moziNev;
    private LocalDate datum;
    private Integer nezoszam;
    private Long bevetel;

    public EloadasListDto(
            Long id,
            String filmCim,
            String moziNev,
            LocalDate datum,
            Integer nezoszam,
            Long bevetel) {

        this.id = id;
        this.filmCim = filmCim;
        this.moziNev = moziNev;
        this.datum = datum;
        this.nezoszam = nezoszam;
        this.bevetel = bevetel;
    }

    public Long getId() {
        return id;
    }

    public String getFilmCim() {
        return filmCim;
    }

    public String getMoziNev() {
        return moziNev;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public Integer getNezoszam() {
        return nezoszam;
    }

    public Long getBevetel() {
        return bevetel;
    }
}
