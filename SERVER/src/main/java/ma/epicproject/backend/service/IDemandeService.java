package ma.epicproject.backend.service;

import ma.epicproject.backend.criteria.DemandeCriteria;
import ma.epicproject.backend.dto.DemandeDto;

import java.util.List;

public interface IDemandeService {

    DemandeDto createDemande(DemandeDto demandeDto) throws Exception;

    DemandeDto updateDemande(Long id, DemandeDto demandeDto) throws Exception;

    /**
     * deleteDemande.
     *
     * @param demandeList
     * @throws Exception
     */
    void deleteDemande(List<Long> idList) throws Exception;


    List<DemandeDto> getDemandesByCriteria(DemandeCriteria demandeCriteria);

    /**
     * paginatedListDemandes.
     *
     * @param demandeCriteria
     * @param page
     * @param pageSize
     * @param order
     * @param sortField
     * @return List<DemandeDto>
     * @throws Exception
     */

    List<DemandeDto> paginatedListDemandes(DemandeCriteria demandeCriteria, int page, int pageSize, String order, String sortField) throws Exception;
    int getDemandeDataSize(DemandeCriteria demandeCriteria);

    /**
     * getDemandeById.
     *
     * @param demandeId
     * @return DemandeDto
     * @throws Exception
     */

    DemandeDto getDemandeById(Long demandeId) throws Exception;
}
