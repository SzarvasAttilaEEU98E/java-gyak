
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.entity.Uzenet;
import hu.nje.javagyakbead.repository.UzenetRepository;
import hu.nje.javagyakbead.request.UzenetRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UzenetService {

    private final UzenetRepository uzenetRepository;

    public UzenetService(UzenetRepository uzenetRepository) {
        this.uzenetRepository = uzenetRepository;
    }

    // Új kapcsolatfelvételi üzenet mentése
    @Transactional
    public Uzenet saveUzenet(UzenetRequest request) {

        // Új entitás létrehozása
        Uzenet uzenet = new Uzenet();

        // Request adatok átmásolása
        uzenet.setNev(request.getNev().trim());
        uzenet.setEmail(request.getEmail().trim());
        uzenet.setTargy(request.getTargy().trim());
        uzenet.setSzoveg(request.getSzoveg().trim());

        // Mentés az adatbázisba
        return uzenetRepository.save(uzenet);
    }

    // Üzenetek lekérdezése fordított időrendben
    @Transactional(readOnly = true)
    public List<Uzenet> getAllUzenetek() {

        return uzenetRepository.findAllByOrderByKuldesIdejeDesc();
    }
}
