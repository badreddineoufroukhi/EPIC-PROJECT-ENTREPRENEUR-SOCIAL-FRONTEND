package ma.epicproject.backend.service;

import ma.epicproject.backend.criteria.ClientCriteria;
import ma.epicproject.backend.dto.ClientDto;

import java.util.List;

public interface IClientService {

    ClientDto createClient(ClientDto clientDto) throws Exception;

    ClientDto updateClient(Long id, ClientDto clientDto) throws Exception;

    /**
     * deleteClient.
     *
     * @param clientList
     * @throws Exception
     */
    void deleteClient(List<Long> idList) throws Exception;


    List<ClientDto> getClientsByCriteria(ClientCriteria clientCriteria);

    /**
     * paginatedListClients.
     *
     * @param clientCriteria
     * @param page
     * @param pageSize
     * @param order
     * @param sortField
     * @return List<ClientDto>
     * @throws Exception
     */

    List<ClientDto> paginatedListClients(ClientCriteria clientCriteria, int page, int pageSize, String order, String sortField) throws Exception;
    int getClientDataSize(ClientCriteria clientCriteria);

    /**
     * getClientById.
     *
     * @param clientId
     * @return ClientDto
     * @throws Exception
     */

    ClientDto getClientById(Long clientId) throws Exception;
}
