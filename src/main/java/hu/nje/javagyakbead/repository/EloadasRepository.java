
package hu.nje.javagyakbead.repository;

import hu.nje.javagyakbead.entity.Eloadas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EloadasRepository extends JpaRepository<Eloadas, Long> {

}
