package ma.epicproject.backend.service.impl;


import ma.epicproject.backend.common.exception.EntityNotFoundException;
import ma.epicproject.backend.criteria.DemandeCriteria;
import ma.epicproject.backend.dto.DemandeDto;
import ma.epicproject.backend.service.IDemandeService;
import ma.epicproject.backend.specification.DemandeSpecification;
import ma.epicproject.backend.entity.Demande;
import ma.epicproject.backend.repository.IDemandeRepository;
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
public class DemandeService implements IDemandeService {

    @Autowired
    private IDemandeRepository demandeRepository;


    /**
     * createDemande.
     * service pour ajouter une demande
     * @author abdessamad
     * @param demandeDto
     * @return DemandeDto
     * @throws Exception
     */
    @Override
    public DemandeDto createDemande(DemandeDto demandeDto) throws Exception {
        Demande demande = new Demande();
        demande = demandeDto.convertToEntity(demande, demandeDto);
        demande = demandeRepository.save(demande);
        demandeDto.setId(demande.getId());
        return demandeDto;
    }


    /**
     * updateDemande.
     * service pour mettre à jour une demande
     * @author abdessamad
     * @param demandeDto
     * @return DemandeDto
     * @throws Exception
     */
    @Override
    public DemandeDto updateDemande(Long id, DemandeDto demandeDto) throws Exception {
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Demande.class.getSimpleName(), demandeDto.getId().toString() }));
        demandeDto.setId(id);
        demande = demandeDto.convertToEntity(demande, demandeDto);
        demandeRepository.save(demande);
        return demandeDto;
    }

    /**
     * deleteDemande.
     *
     * @param idList
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteDemande(List<Long> idList) throws Exception {


        if (idList != null)
            for (Long id : idList) {
                Demande toBeDeleted = demandeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Demande.class.getSimpleName(), id.toString() }));

                demandeRepository.delete(toBeDeleted);

            }
    }

    /**
     * getDemandesByCriteria.
     * service pour récuperer une list des demandes suivant des critère
     * @author abdessamad
     * @param demandeCriteria
     * @return List<DemandeDto>
     */
    @Override
    public List<DemandeDto> getDemandesByCriteria(DemandeCriteria demandeCriteria) {
        Specification<Demande> specification = new DemandeSpecification(demandeCriteria);
        if(demandeCriteria.isPeagable()){
            Pageable pageable = PageRequest.of(0,demandeCriteria.getMaxResults());
            return demandeRepository.findAll(specification,pageable)
                    .stream()
                    .map((demande -> new DemandeDto(demande))).collect(Collectors.toList());
        }else
            return demandeRepository.findAll(specification)
                    .stream()
                    .map((demande -> new DemandeDto(demande))).toList();
    }

    /**
     * paginatedListHopitals.
     *
     * @param demandeCriteria
     * @param page
     * @param pageSize
     * @param order
     * @param sortField
     * @return List<HopitalDto>
     * @throws Exception
     */
    @Override
    public List<DemandeDto> paginatedListDemandes(DemandeCriteria demandeCriteria, int page, int pageSize, String order, String sortField) throws Exception {

        Specification<Demande> specification = new DemandeSpecification(demandeCriteria);
        order = order != null && !order.isEmpty() ? order : "desc";
        sortField = sortField != null && !sortField.isEmpty() ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);

        return demandeRepository.findAll(specification, pageable)
                .stream()
                .map(demande -> new DemandeDto(demande))
                .collect(Collectors.toList());
    }

    /**
     * getDemandeDataSize.
     * service pour calculer le nombre des nuplet pour une requete prédéfinie
     * @author abdessamad
     * @param demandeCriteria
     * @return entier
     */
    @Override
    public int getDemandeDataSize(DemandeCriteria demandeCriteria) {
        Specification<Demande> specification = new DemandeSpecification(demandeCriteria, true);
        return ((Long)  demandeRepository.count(specification)).intValue();
    }

    /**
     * getDemandeById.
     *
     * @param demandeId
     * @return DemandeDto
     * @throws Exception
     */
    public DemandeDto getDemandeById(Long demandeId) throws Exception {

        Demande demande = demandeRepository.findById(demandeId).orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Demande.class.getSimpleName(), demandeId.toString() }));

        return  new DemandeDto(demande, true, 0);

    }

}




