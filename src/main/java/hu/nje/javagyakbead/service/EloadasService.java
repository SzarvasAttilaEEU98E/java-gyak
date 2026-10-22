
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.entity.Eloadas;
import hu.nje.javagyakbead.repository.EloadasRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EloadasService {

    private final EloadasRepository eloadasRepository;

    public EloadasService(EloadasRepository eloadasRepository) {
        this.eloadasRepository = eloadasRepository;
    }

    public Page<Eloadas> getAllEloadasok(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("id").ascending()
        );

        return eloadasRepository.findAll(pageable);
    }
}
