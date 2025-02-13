package ma.epicproject.backend.service;

import ma.epicproject.backend.criteria.OuvrierCriteria;
import ma.epicproject.backend.dto.OuvrierDto;

import java.util.List;

public interface IOuvrierService {

    OuvrierDto createOuvrier(OuvrierDto ouvrierDto) throws Exception;

    OuvrierDto updateOuvrier(Long id, OuvrierDto ouvrierDto) throws Exception;

    /**
     * deleteOuvrier.
     *
     * @param ouvrierList
     * @throws Exception
     */
    void deleteOuvrier(List<Long> idList) throws Exception;


    List<OuvrierDto> getOuvriersByCriteria(OuvrierCriteria ouvrierCriteria);

    /**
     * paginatedListOuvriers.
     *
     * @param ouvrierCriteria
     * @param page
     * @param pageSize
     * @param order
     * @param sortField
     * @return List<OuvrierDto>
     * @throws Exception
     */

    List<OuvrierDto> paginatedListOuvriers(OuvrierCriteria ouvrierCriteria, int page, int pageSize, String order, String sortField) throws Exception;
    int getOuvrierDataSize(OuvrierCriteria ouvrierCriteria);

    /**
     * getOuvrierById.
     *
     * @param ouvrierId
     * @return OuvrierDto
     * @throws Exception
     */

    OuvrierDto getOuvrierById(Long ouvrierId) throws Exception;
}
