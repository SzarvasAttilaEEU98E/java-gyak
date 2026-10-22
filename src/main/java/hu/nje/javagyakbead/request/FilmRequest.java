
package hu.nje.javagyakbead.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FilmRequest {

    private Long id;

    @NotBlank(message = "A film címe kötelező.")
    private String cim;

    @NotNull(message = "A megjelenési év kötelező.")
    @Min(value = 1888, message = "A megjelenési év nem lehet 1888 előtti.")
    private Integer ev;

    @NotNull(message = "A film hosszának megadása kötelező.")
    @Min(value = 1, message = "A film hossza legalább 1 perc legyen.")
    @Max(value = 400, message = "A film hossza legfeljebb 400 perc lehet.")
    private Integer hossz;

    public FilmRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public Integer getEv() {
        return ev;
    }

    public void setEv(Integer ev) {
        this.ev = ev;
    }

    public Integer getHossz() {
        return hossz;
    }

    public void setHossz(Integer hossz) {
        this.hossz = hossz;
    }
}
