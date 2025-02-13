package ma.epicproject.backend.repository;

import ma.epicproject.backend.entity.ServicePropose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IServiceProposeRepository extends JpaRepository<ServicePropose,Long>, JpaSpecificationExecutor<ServicePropose> {

}
