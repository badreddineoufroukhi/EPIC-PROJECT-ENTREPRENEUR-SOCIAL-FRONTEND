package ma.epicproject.backend.repository;

import ma.epicproject.backend.entity.ServicePropose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IServiceProposeRepository extends JpaRepository<ServicePropose,Long>, JpaSpecificationExecutor<ServicePropose> {

    @Modifying
    @Transactional
    @Query("DELETE FROM ServicePropose s WHERE s.ouvrier.id = :ouvrierId")
    void deleteByOuvrierId(@Param("ouvrierId") Long ouvrierId);

    @Modifying
    @Transactional
    @Query("SELECT s.id FROM ServicePropose s WHERE s.ouvrier.id = :ouvrierId")
    List<Long> getListServiceProposeIdByOuvrierId(@Param("ouvrierId") Long ouvrierId);
}
