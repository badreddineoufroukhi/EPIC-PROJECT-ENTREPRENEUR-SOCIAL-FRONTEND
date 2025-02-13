package ma.epicproject.backend.service.impl;


import ma.epicproject.backend.common.exception.EntityNotFoundException;
import ma.epicproject.backend.criteria.UtilisateurCriteria;
import ma.epicproject.backend.dto.UtilisateurDto;
import ma.epicproject.backend.service.IUtilisateurService;
import ma.epicproject.backend.specification.UtilisateurSpecification;
import ma.epicproject.backend.entity.Utilisateur;
import ma.epicproject.backend.repository.IUtilisateurRepository;
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
public class UtilisateurService implements IUtilisateurService {

    @Autowired
    private IUtilisateurRepository utilisateurRepository;


    /**
     * createUtilisateur.
     * service pour ajouter une utilisateur
     * @author abdessamad
     * @param utilisateurDto
     * @return UtilisateurDto
     * @throws Exception
     */
    @Override
    public UtilisateurDto createUtilisateur(UtilisateurDto utilisateurDto) throws Exception {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur = utilisateurDto.convertToEntity(utilisateur, utilisateurDto);
        utilisateur = utilisateurRepository.save(utilisateur);
        utilisateurDto.setId(utilisateur.getId());
        return utilisateurDto;
    }


    /**
     * updateUtilisateur.
     * service pour mettre à jour une utilisateur
     * @author abdessamad
     * @param utilisateurDto
     * @return UtilisateurDto
     * @throws Exception
     */
    @Override
    public UtilisateurDto updateUtilisateur(Long id, UtilisateurDto utilisateurDto) throws Exception {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Utilisateur.class.getSimpleName(), utilisateurDto.getId().toString() }));
        utilisateurDto.setId(id);
        utilisateur = utilisateurDto.convertToEntity(utilisateur, utilisateurDto);
        utilisateurRepository.save(utilisateur);
        return utilisateurDto;
    }

    /**
     * deleteUtilisateur.
     *
     * @param idList
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteUtilisateur(List<Long> idList) throws Exception {


        if (idList != null)
            for (Long id : idList) {
                Utilisateur toBeDeleted = utilisateurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Utilisateur.class.getSimpleName(), id.toString() }));

                utilisateurRepository.delete(toBeDeleted);

            }
    }

    /**
     * getUtilisateursByCriteria.
     * service pour récuperer une list des utilisateurs suivant des critère
     * @author abdessamad
     * @param utilisateurCriteria
     * @return List<UtilisateurDto>
     */
    @Override
    public List<UtilisateurDto> getUtilisateursByCriteria(UtilisateurCriteria utilisateurCriteria) {
        Specification<Utilisateur> specification = new UtilisateurSpecification(utilisateurCriteria);
        if(utilisateurCriteria.isPeagable()){
            Pageable pageable = PageRequest.of(0,utilisateurCriteria.getMaxResults());
            return utilisateurRepository.findAll(specification,pageable)
                    .stream()
                    .map((utilisateur -> new UtilisateurDto(utilisateur))).collect(Collectors.toList());
        }else
            return utilisateurRepository.findAll(specification)
                    .stream()
                    .map((utilisateur -> new UtilisateurDto(utilisateur))).toList();
    }

    /**
     * paginatedListHopitals.
     *
     * @param utilisateurCriteria
     * @param page
     * @param pageSize
     * @param order
     * @param sortField
     * @return List<HopitalDto>
     * @throws Exception
     */
    @Override
    public List<UtilisateurDto> paginatedListUtilisateurs(UtilisateurCriteria utilisateurCriteria, int page, int pageSize, String order, String sortField) throws Exception {

        Specification<Utilisateur> specification = new UtilisateurSpecification(utilisateurCriteria);
        order = order != null && !order.isEmpty() ? order : "desc";
        sortField = sortField != null && !sortField.isEmpty() ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);

        return utilisateurRepository.findAll(specification, pageable)
                .stream()
                .map(utilisateur -> new UtilisateurDto(utilisateur))
                .collect(Collectors.toList());
    }

    /**
     * getUtilisateurDataSize.
     * service pour calculer le nombre des nuplet pour une requete prédéfinie
     * @author abdessamad
     * @param utilisateurCriteria
     * @return entier
     */
    @Override
    public int getUtilisateurDataSize(UtilisateurCriteria utilisateurCriteria) {
        Specification<Utilisateur> specification = new UtilisateurSpecification(utilisateurCriteria, true);
        return ((Long)  utilisateurRepository.count(specification)).intValue();
    }

    /**
     * getUtilisateurById.
     *
     * @param utilisateurId
     * @return UtilisateurDto
     * @throws Exception
     */
    public UtilisateurDto getUtilisateurById(Long utilisateurId) throws Exception {

        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Utilisateur.class.getSimpleName(), utilisateurId.toString() }));

        return  new UtilisateurDto(utilisateur, true, 0);

    }

}




