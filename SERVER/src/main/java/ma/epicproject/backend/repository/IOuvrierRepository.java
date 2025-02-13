package ma.epicproject.backend.repository;

import ma.epicproject.backend.entity.Ouvrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IOuvrierRepository extends JpaRepository<Ouvrier,Long>, JpaSpecificationExecutor<Ouvrier> {

}
