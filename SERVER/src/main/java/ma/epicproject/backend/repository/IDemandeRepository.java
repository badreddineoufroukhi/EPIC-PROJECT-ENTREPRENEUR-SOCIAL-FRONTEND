package ma.epicproject.backend.repository;

import ma.epicproject.backend.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IDemandeRepository extends JpaRepository<Demande,Long>, JpaSpecificationExecutor<Demande> {

}
