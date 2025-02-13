package ma.epicproject.backend.service.impl;


import ma.epicproject.backend.common.exception.EntityNotFoundException;
import ma.epicproject.backend.criteria.ClientCriteria;
import ma.epicproject.backend.dto.ClientDto;
import ma.epicproject.backend.service.IClientService;
import ma.epicproject.backend.specification.ClientSpecification;
import ma.epicproject.backend.entity.Client;
import ma.epicproject.backend.repository.IClientRepository;
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
public class ClientService implements IClientService {

    @Autowired
    private IClientRepository clientRepository;


    /**
     * createClient.
     * service pour ajouter une client
     * @author abdessamad
     * @param clientDto
     * @return ClientDto
     * @throws Exception
     */
    @Override
    public ClientDto createClient(ClientDto clientDto) throws Exception {
        Client client = new Client();
        client = clientDto.convertToEntity(client, clientDto);
        client = clientRepository.save(client);
        clientDto.setId(client.getId());
        return clientDto;
    }


    /**
     * updateClient.
     * service pour mettre à jour une client
     * @author abdessamad
     * @param clientDto
     * @return ClientDto
     * @throws Exception
     */
    @Override
    public ClientDto updateClient(Long id, ClientDto clientDto) throws Exception {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Client.class.getSimpleName(), clientDto.getId().toString() }));
        clientDto.setId(id);
        client = clientDto.convertToEntity(client, clientDto);
        clientRepository.save(client);
        return clientDto;
    }

    /**
     * deleteClient.
     *
     * @param idList
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)
    public void deleteClient(List<Long> idList) throws Exception {


        if (idList != null)
            for (Long id : idList) {
                Client toBeDeleted = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Client.class.getSimpleName(), id.toString() }));

                clientRepository.delete(toBeDeleted);

            }
    }

    /**
     * getClientsByCriteria.
     * service pour récuperer une list des clients suivant des critère
     * @author abdessamad
     * @param clientCriteria
     * @return List<ClientDto>
     */
    @Override
    public List<ClientDto> getClientsByCriteria(ClientCriteria clientCriteria) {
        Specification<Client> specification = new ClientSpecification(clientCriteria);
        if(clientCriteria.isPeagable()){
            Pageable pageable = PageRequest.of(0,clientCriteria.getMaxResults());
            return clientRepository.findAll(specification,pageable)
                    .stream()
                    .map((client -> new ClientDto(client))).collect(Collectors.toList());
        }else
            return clientRepository.findAll(specification)
                    .stream()
                    .map((client -> new ClientDto(client))).toList();
    }

    /**
     * paginatedListHopitals.
     *
     * @param clientCriteria
     * @param page
     * @param pageSize
     * @param order
     * @param sortField
     * @return List<HopitalDto>
     * @throws Exception
     */
    @Override
    public List<ClientDto> paginatedListClients(ClientCriteria clientCriteria, int page, int pageSize, String order, String sortField) throws Exception {

        Specification<Client> specification = new ClientSpecification(clientCriteria);
        order = order != null && !order.isEmpty() ? order : "desc";
        sortField = sortField != null && !sortField.isEmpty() ? sortField : "id";
        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.fromString(order), sortField);

        return clientRepository.findAll(specification, pageable)
                .stream()
                .map(client -> new ClientDto(client))
                .collect(Collectors.toList());
    }

    /**
     * getClientDataSize.
     * service pour calculer le nombre des nuplet pour une requete prédéfinie
     * @author abdessamad
     * @param clientCriteria
     * @return entier
     */
    @Override
    public int getClientDataSize(ClientCriteria clientCriteria) {
        Specification<Client> specification = new ClientSpecification(clientCriteria, true);
        return ((Long)  clientRepository.count(specification)).intValue();
    }

    /**
     * getClientById.
     *
     * @param clientId
     * @return ClientDto
     * @throws Exception
     */
    public ClientDto getClientById(Long clientId) throws Exception {

        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("errors.notFound", new String[] { Client.class.getSimpleName(), clientId.toString() }));

        return  new ClientDto(client, true, 0);

    }

}




