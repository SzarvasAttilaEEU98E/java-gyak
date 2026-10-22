
package hu.nje.javagyakbead.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MoziRequest {

    private Long id;

    @NotBlank(message = "A mozi neve kötelező.")
    private String nev;

    @NotBlank(message = "A város megadása kötelező.")
    private String varos;

    @NotNull(message = "A férőhelyek számának megadása kötelező.")
    @Min(value = 1, message = "A férőhelyek száma legalább 1 legyen.")
    @Max(value = 10000, message = "A férőhelyek száma legfeljebb 10000 lehet.")
    private Integer ferohely;

    public MoziRequest() {
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
