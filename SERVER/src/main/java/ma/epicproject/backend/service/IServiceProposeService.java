package ma.epicproject.backend.service;

import ma.epicproject.backend.criteria.ServiceProposeCriteria;
import ma.epicproject.backend.dto.ServiceProposeDto;

import java.util.List;

public interface IServiceProposeService {

    ServiceProposeDto createServicePropose(ServiceProposeDto serviceProposeDto) throws Exception;

    ServiceProposeDto updateServicePropose(Long id, ServiceProposeDto serviceProposeDto) throws Exception;

    /**
     * deleteServicePropose.
     *
     * @param serviceProposeList
     * @throws Exception
     */
    void deleteServicePropose(List<Long> idList) throws Exception;


    List<ServiceProposeDto> getServiceProposesByCriteria(ServiceProposeCriteria serviceProposeCriteria);

    /**
     * paginatedListServiceProposes.
     *
     * @param serviceProposeCriteria
     * @param page
     * @param pageSize
     * @param order
     * @param sortField
     * @return List<ServiceProposeDto>
     * @throws Exception
     */

    List<ServiceProposeDto> paginatedListServiceProposes(ServiceProposeCriteria serviceProposeCriteria, int page, int pageSize, String order, String sortField) throws Exception;
    int getServiceProposeDataSize(ServiceProposeCriteria serviceProposeCriteria);

    /**
     * getServiceProposeById.
     *
     * @param serviceProposeId
     * @return ServiceProposeDto
     * @throws Exception
     */

    ServiceProposeDto getServiceProposeById(Long serviceProposeId) throws Exception;
}
