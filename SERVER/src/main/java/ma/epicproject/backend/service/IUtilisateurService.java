package ma.epicproject.backend.service;

import ma.epicproject.backend.criteria.UtilisateurCriteria;
import ma.epicproject.backend.dto.UtilisateurDto;

import java.util.List;

public interface IUtilisateurService {

    UtilisateurDto createUtilisateur(UtilisateurDto utilisateurDto) throws Exception;

    UtilisateurDto updateUtilisateur(Long id, UtilisateurDto utilisateurDto) throws Exception;

    /**
     * deleteUtilisateur.
     *
     * @param utilisateurList
     * @throws Exception
     */
    void deleteUtilisateur(List<Long> idList) throws Exception;


    List<UtilisateurDto> getUtilisateursByCriteria(UtilisateurCriteria utilisateurCriteria);

    /**
     * paginatedListUtilisateurs.
     *
     * @param utilisateurCriteria
     * @param page
     * @param pageSize
     * @param order
     * @param sortField
     * @return List<UtilisateurDto>
     * @throws Exception
     */

    List<UtilisateurDto> paginatedListUtilisateurs(UtilisateurCriteria utilisateurCriteria, int page, int pageSize, String order, String sortField) throws Exception;
    int getUtilisateurDataSize(UtilisateurCriteria utilisateurCriteria);

    /**
     * getUtilisateurById.
     *
     * @param utilisateurId
     * @return UtilisateurDto
     * @throws Exception
     */

    UtilisateurDto getUtilisateurById(Long utilisateurId) throws Exception;
}
