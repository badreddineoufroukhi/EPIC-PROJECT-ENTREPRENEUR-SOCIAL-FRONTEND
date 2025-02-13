package ma.epicproject.backend.controller;


import ma.epicproject.backend.common.bean.BaseController;
import ma.epicproject.backend.common.bean.PaginatedList;
import ma.epicproject.backend.criteria.ServiceProposeCriteria;
import ma.epicproject.backend.dto.ServiceProposeDto;
import ma.epicproject.backend.service.IServiceProposeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/servicePropose")
@CrossOrigin("*")
public class ServiceProposeController extends BaseController {

    @Autowired
    private IServiceProposeService iServiceProposeService;

    @GetMapping("/{id}")
    public ResponseEntity<ServiceProposeDto> getServiceProposeById(@PathVariable("id") Long id, String[] includes, String[] excludes) throws Exception {

        ServiceProposeDto servicePropose = iServiceProposeService.getServiceProposeById(id);


        return new ResponseEntity<ServiceProposeDto>(servicePropose, HttpStatus.OK);

    }
    @PostMapping()
    public ResponseEntity<Long> addServicePropose(@RequestBody ServiceProposeDto serviceProposeDto) throws Exception {
        serviceProposeDto = iServiceProposeService.createServicePropose(serviceProposeDto);
        return  new ResponseEntity<Long>(serviceProposeDto.getId(), HttpStatus.CREATED);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteServicePropose(@RequestBody List<Long> idList) throws Exception {

        if (idList == null || idList.isEmpty())
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);

        iServiceProposeService.deleteServicePropose(idList);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceProposeDto> updateServicePropose(@PathVariable(name = "id") Long id, @RequestBody ServiceProposeDto serviceProposeDto) throws Exception{
        serviceProposeDto = iServiceProposeService.updateServicePropose(id, serviceProposeDto);
        return ResponseEntity.ok(serviceProposeDto);
    }


    @PostMapping("/paginatedListByCriteria")
    public @ResponseBody
    ResponseEntity<PaginatedList> paginatedListServicePropose(@RequestBody ServiceProposeCriteria serviceProposeCriteria) throws Exception {

        List<ServiceProposeDto> list = iServiceProposeService.paginatedListServiceProposes(serviceProposeCriteria,serviceProposeCriteria.getPage(),serviceProposeCriteria.getMaxResults(), serviceProposeCriteria.getSortOrder(), serviceProposeCriteria.getSortField());

        PaginatedList paginatedList=new PaginatedList();
        paginatedList.setList(list);
        if (list != null && !list.isEmpty()) {
            int dateSize = iServiceProposeService.getServiceProposeDataSize(serviceProposeCriteria);
            paginatedList.setDataSize(dateSize);
        }

        return new ResponseEntity<PaginatedList>(paginatedList, HttpStatus.OK);

    }
}
