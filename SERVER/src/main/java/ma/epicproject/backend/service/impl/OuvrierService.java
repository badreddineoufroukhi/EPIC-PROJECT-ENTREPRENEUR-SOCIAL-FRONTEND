package ma.epicproject.backend.service.impl;


import ma.epicproject.backend.common.exception.EntityNotFoundException;
import ma.epicproject.backend.criteria.OuvrierCriteria;
import ma.epicproject.backend.dto.OuvrierDto;
import ma.epicproject.backend.repository.IDemandeRepository;
import ma.epicproject.backend.repository.IServiceProposeRepository;
import ma.epicproject.backend.service.IOuvrierService;
import ma.epicproject.backend.specification.OuvrierSpecification;
import ma.epicproject.backend.entity.Ouvrier;
import ma.epicproject.backend.repository.IOuvrierRepository;
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
public class OuvrierService implements IOuvrierService {

    @Autowired
    private IOuvrierRepository ouvrierRepository;
    @Autowired
    private IServiceProposeRepository serviceProposeRepository;
    @Autowired
    private IDemandeRepository demandeRepository;


    /**
     * createOuvrier.
     * service pour ajouter une ouvrier
     * @author abdessamad
     * @param ouvrierDto
     * @return OuvrierDto
     * @throws Exception
     */
    @Override
    public OuvrierDto createOuvrier(OuvrierDto ouvrierDto) throws Exception {
        Ouvrier ouvrier = new Ouvrier();
        ouvrier = ouvrierDto.convertToEntity(ouvrier, ouvrierDto);
        ouvrier = ouvrierRepository.save(ouvrier);
        ouvrierDto.setId(ouvrier.getId());
        return ouvrierDto;
    }


    /**
     * updateOuvrier.
     * service pour mettre à jour une ouvrier
     * @author abdessamad
     * @param ouvrierDto
     * @return OuvrierDto
     * @throws Exception
     */
    @Override
    public OuvrierDto updateOuvrier(Long id, OuvrierDto ouvrierDto) throws Exception {
        Ouvrier ouvrier = ouvrierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Ouvrier.class.getSimpleName(), ouvrierDto.getId().toString() }));
        ouvrierDto.setId(id);
        ouvrier = ouvrierDto.convertToEntity(ouvrier, ouvrierDto);
        ouvrierRepository.save(ouvrier);
        return ouvrierDto;
    }

    /**
     * deleteOuvrier.
     *
     * @param idList
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteOuvrier(List<Long> idList) throws Exception {


        if (idList != null)
            for (Long id : idList) {
                Ouvrier toBeDeleted = ouvrierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Ouvrier.class.getSimpleName(), id.toString() }));
                List<Long> idListServicePropose = serviceProposeRepository.getListServiceProposeIdByOuvrierId(toBeDeleted.getId());
                if (!(idListServicePropose == null || idListServicePropose.isEmpty())){
                    for (Long idServicePropose : idListServicePropose){
                        demandeRepository.deleteByServiceProposeId(idServicePropose);
                    }
                }
                serviceProposeRepository.deleteByOuvrierId(toBeDeleted.getId());

                ouvrierRepository.delete(toBeDeleted);

            }
    }

    /**
     * getOuvriersByCriteria.
     * service pour récuperer une list des ouvriers suivant des critère
     * @author abdessamad
     * @param ouvrierCriteria
     * @return List<OuvrierDto>
     */
    @Override
    public List<OuvrierDto> getOuvriersByCriteria(OuvrierCriteria ouvrierCriteria) {
        Specification<Ouvrier> specification = new OuvrierSpecification(ouvrierCriteria);
        if(ouvrierCriteria.isPeagable()){
            Pageable pageable = PageRequest.of(0,ouvrierCriteria.getMaxResults());
            return ouvrierRepository.findAll(specification,pageable)
                    .stream()
                    .map((ouvrier -> new OuvrierDto(ouvrier))).collect(Collectors.toList());
        }else
            return ouvrierRepository.findAll(specification)
                    .stream()
                    .map((ouvrier -> new OuvrierDto(ouvrier))).toList();
    }

    /**
     * paginatedListHopitals.
     *
     * @param ouvrierCriteria
     * @param page
     * @param pageSize
     * @param order
     * @param sortField
     * @return List<HopitalDto>
     * @throws Exception
     */
    @Override
    public List<OuvrierDto> paginatedListOuvriers(OuvrierCriteria ouvrierCriteria, int page, int pageSize, String order, String sortField) throws Exception {

        Specification<Ouvrier> specification = new OuvrierSpecification(ouvrierCriteria);
        order = order != null && !order.isEmpty() ? order : "desc";
        sortField = sortField != null && !sortField.isEmpty() ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);

        return ouvrierRepository.findAll(specification, pageable)
                .stream()
                .map(ouvrier -> new OuvrierDto(ouvrier))
                .collect(Collectors.toList());
    }

    /**
     * getOuvrierDataSize.
     * service pour calculer le nombre des nuplet pour une requete prédéfinie
     * @author abdessamad
     * @param ouvrierCriteria
     * @return entier
     */
    @Override
    public int getOuvrierDataSize(OuvrierCriteria ouvrierCriteria) {
        Specification<Ouvrier> specification = new OuvrierSpecification(ouvrierCriteria, true);
        return ((Long)  ouvrierRepository.count(specification)).intValue();
    }

    /**
     * getOuvrierById.
     *
     * @param ouvrierId
     * @return OuvrierDto
     * @throws Exception
     */
    public OuvrierDto getOuvrierById(Long ouvrierId) throws Exception {

        Ouvrier ouvrier = ouvrierRepository.findById(ouvrierId).orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Ouvrier.class.getSimpleName(), ouvrierId.toString() }));

        return  new OuvrierDto(ouvrier, true, 0);

    }

}




