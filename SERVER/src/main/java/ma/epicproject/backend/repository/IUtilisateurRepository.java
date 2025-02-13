package ma.epicproject.backend.repository;

import ma.epicproject.backend.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IUtilisateurRepository extends JpaRepository<Utilisateur,Long>, JpaSpecificationExecutor<Utilisateur> {

}
