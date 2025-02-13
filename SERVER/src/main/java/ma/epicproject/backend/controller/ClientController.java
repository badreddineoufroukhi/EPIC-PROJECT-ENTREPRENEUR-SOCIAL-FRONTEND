package ma.epicproject.backend.controller;


import ma.epicproject.backend.common.bean.BaseController;
import ma.epicproject.backend.common.bean.PaginatedList;
import ma.epicproject.backend.criteria.ClientCriteria;
import ma.epicproject.backend.dto.ClientDto;
import ma.epicproject.backend.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin("*")
public class ClientController extends BaseController {

    @Autowired
    private IClientService iClientService;

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getClientById(@PathVariable("id") Long id, String[] includes, String[] excludes) throws Exception {

        ClientDto client = iClientService.getClientById(id);


        return new ResponseEntity<ClientDto>(client, HttpStatus.OK);

    }
    @PostMapping()
    public ResponseEntity<Long> addClient(@RequestBody ClientDto clientDto) throws Exception {
        clientDto = iClientService.createClient(clientDto);
        return  new ResponseEntity<Long>(clientDto.getId(), HttpStatus.CREATED);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteClient(@RequestBody List<Long> idList) throws Exception {

        if (idList == null || idList.isEmpty())
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);

        iClientService.deleteClient(idList);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable(name = "id") Long id, @RequestBody ClientDto clientDto) throws Exception{
        clientDto = iClientService.updateClient(id, clientDto);
        return ResponseEntity.ok(clientDto);
    }


    @PostMapping("/paginatedListByCriteria")
    public @ResponseBody
    ResponseEntity<PaginatedList> paginatedListClient(@RequestBody ClientCriteria clientCriteria) throws Exception {

        List<ClientDto> list = iClientService.paginatedListClients(clientCriteria,clientCriteria.getPage(),clientCriteria.getMaxResults(), clientCriteria.getSortOrder(), clientCriteria.getSortField());

        PaginatedList paginatedList=new PaginatedList();
        paginatedList.setList(list);
        if (list != null && !list.isEmpty()) {
            int dateSize = iClientService.getClientDataSize(clientCriteria);
            paginatedList.setDataSize(dateSize);
        }

        return new ResponseEntity<PaginatedList>(paginatedList, HttpStatus.OK);

    }
}
