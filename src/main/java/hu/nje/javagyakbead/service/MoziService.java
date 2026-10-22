
package hu.nje.javagyakbead.service;

import hu.nje.javagyakbead.entity.Mozi;
import hu.nje.javagyakbead.repository.MoziRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoziService {

    private final MoziRepository moziRepository;

    public MoziService(MoziRepository moziRepository) {
        this.moziRepository = moziRepository;
    }

    public List<Mozi> getAllMozik() {
        return moziRepository.findAll();
    }
}
