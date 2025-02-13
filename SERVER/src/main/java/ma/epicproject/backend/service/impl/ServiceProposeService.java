package ma.epicproject.backend.service.impl;


import ma.epicproject.backend.common.exception.EntityNotFoundException;
import ma.epicproject.backend.criteria.ServiceProposeCriteria;
import ma.epicproject.backend.dto.ServiceProposeDto;
import ma.epicproject.backend.service.IServiceProposeService;
import ma.epicproject.backend.specification.ServiceProposeSpecification;
import ma.epicproject.backend.entity.ServicePropose;
import ma.epicproject.backend.repository.IServiceProposeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceProposeService implements IServiceProposeService {

    @Autowired
    private IServiceProposeRepository serviceProposeRepository;


    /**
     * createServicePropose.
     * service pour ajouter une servicePropose
     * @author abdessamad
     * @param serviceProposeDto
     * @return ServiceProposeDto
     * @throws Exception
     */
    @Override
    public ServiceProposeDto createServicePropose(ServiceProposeDto serviceProposeDto) throws Exception {
        ServicePropose servicePropose = new ServicePropose();
        servicePropose = serviceProposeDto.convertToEntity(servicePropose, serviceProposeDto);
        servicePropose = serviceProposeRepository.save(servicePropose);
        serviceProposeDto.setId(servicePropose.getId());
        return serviceProposeDto;
    }


    /**
     * updateServicePropose.
     * service pour mettre à jour une servicePropose
     * @author abdessamad
     * @param serviceProposeDto
     * @return ServiceProposeDto
     * @throws Exception
     */
    @Override
    public ServiceProposeDto updateServicePropose(Long id, ServiceProposeDto serviceProposeDto) throws Exception {
        ServicePropose servicePropose = serviceProposeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { ServicePropose.class.getSimpleName(), serviceProposeDto.getId().toString() }));
        serviceProposeDto.setId(id);
        servicePropose = serviceProposeDto.convertToEntity(servicePropose, serviceProposeDto);
        serviceProposeRepository.save(servicePropose);
        return serviceProposeDto;
    }

    /**
     * deleteServicePropose.
     *
     * @param idList
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteServicePropose(List<Long> idList) throws Exception {


        if (idList != null)
            for (Long id : idList) {
                ServicePropose toBeDeleted = serviceProposeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { ServicePropose.class.getSimpleName(), id.toString() }));

                serviceProposeRepository.delete(toBeDeleted);

            }
    }

    /**
     * getServiceProposesByCriteria.
     * service pour récuperer une list des serviceProposes suivant des critère
     * @author abdessamad
     * @param serviceProposeCriteria
     * @return List<ServiceProposeDto>
     */
    @Override
    public List<ServiceProposeDto> getServiceProposesByCriteria(ServiceProposeCriteria serviceProposeCriteria) {
        Specification<ServicePropose> specification = new ServiceProposeSpecification(serviceProposeCriteria);
        if(serviceProposeCriteria.isPeagable()){
            Pageable pageable = PageRequest.of(0,serviceProposeCriteria.getMaxResults());
            return serviceProposeRepository.findAll(specification,pageable)
                    .stream()
                    .map((servicePropose -> new ServiceProposeDto(servicePropose))).collect(Collectors.toList());
        }else
            return serviceProposeRepository.findAll(specification)
                    .stream()
                    .map((servicePropose -> new ServiceProposeDto(servicePropose))).toList();
    }

    /**
     * paginatedListHopitals.
     *
     * @param serviceProposeCriteria
     * @param page
     * @param pageSize
     * @param order
     * @param sortField
     * @return List<HopitalDto>
     * @throws Exception
     */
    @Override
    public List<ServiceProposeDto> paginatedListServiceProposes(ServiceProposeCriteria serviceProposeCriteria, int page, int pageSize, String order, String sortField) throws Exception {

        Specification<ServicePropose> specification = new ServiceProposeSpecification(serviceProposeCriteria);
        order = order != null && !order.isEmpty() ? order : "desc";
        sortField = sortField != null && !sortField.isEmpty() ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);

        return serviceProposeRepository.findAll(specification, pageable)
                .stream()
                .map(servicePropose -> new ServiceProposeDto(servicePropose))
                .collect(Collectors.toList());
    }

    /**
     * getServiceProposeDataSize.
     * service pour calculer le nombre des nuplet pour une requete prédéfinie
     * @author abdessamad
     * @param serviceProposeCriteria
     * @return entier
     */
    @Override
    public int getServiceProposeDataSize(ServiceProposeCriteria serviceProposeCriteria) {
        Specification<ServicePropose> specification = new ServiceProposeSpecification(serviceProposeCriteria, true);
        return ((Long)  serviceProposeRepository.count(specification)).intValue();
    }

    /**
     * getServiceProposeById.
     *
     * @param serviceProposeId
     * @return ServiceProposeDto
     * @throws Exception
     */
    public ServiceProposeDto getServiceProposeById(Long serviceProposeId) throws Exception {

        ServicePropose servicePropose = serviceProposeRepository.findById(serviceProposeId).orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { ServicePropose.class.getSimpleName(), serviceProposeId.toString() }));

        return  new ServiceProposeDto(servicePropose, true, 0);

    }

}




