package ma.epicproject.backend.repository;

import ma.epicproject.backend.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IDemandeRepository extends JpaRepository<Demande,Long>, JpaSpecificationExecutor<Demande> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Demande s WHERE s.servicePropose.id = :serviceProposeId")
    void deleteByServiceProposeId(@Param("serviceProposeId") Long serviceProposeId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Demande s WHERE s.client.id = :clientId")
    void deleteByClientId(@Param("clientId") Long clientId);
}
