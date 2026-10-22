
package hu.nje.javagyakbead.repository;

import hu.nje.javagyakbead.entity.Mozi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoziRepository extends JpaRepository<Mozi, Long> {

}
