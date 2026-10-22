
package hu.nje.javagyakbead.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UzenetRequest {

    @NotBlank(message = "A név megadása kötelező.")
    @Size(max = 100, message = "A név legfeljebb 100 karakter lehet.")
    private String nev;

    @NotBlank(message = "Az e-mail-cím megadása kötelező.")
    @Email(message = "Érvényes e-mail-címet adj meg.")
    @Size(max = 255, message = "Az e-mail-cím legfeljebb 255 karakter lehet.")
    private String email;

    @NotBlank(message = "A tárgy megadása kötelező.")
    @Size(max = 150, message = "A tárgy legfeljebb 150 karakter lehet.")
    private String targy;

    @NotBlank(message = "Az üzenet szövegének megadása kötelező.")
    @Size(
            min = 10,
            max = 5000,
            message = "Az üzenet hossza 10 és 5000 karakter között lehet."
    )
    private String szoveg;

    public UzenetRequest() {
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
}
