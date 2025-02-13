package ma.epicproject.backend.controller;


import ma.epicproject.backend.common.bean.BaseController;
import ma.epicproject.backend.common.bean.PaginatedList;
import ma.epicproject.backend.criteria.DemandeCriteria;
import ma.epicproject.backend.dto.DemandeDto;
import ma.epicproject.backend.service.IDemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/demande")
@CrossOrigin("*")
public class DemandeController extends BaseController {

    @Autowired
    private IDemandeService iDemandeService;

    @GetMapping("/{id}")
    public ResponseEntity<DemandeDto> getDemandeById(@PathVariable("id") Long id, String[] includes, String[] excludes) throws Exception {

        DemandeDto demande = iDemandeService.getDemandeById(id);


        return new ResponseEntity<DemandeDto>(demande, HttpStatus.OK);

    }
    @PostMapping()
    public ResponseEntity<Long> addDemande(@RequestBody DemandeDto demandeDto) throws Exception {
        demandeDto = iDemandeService.createDemande(demandeDto);
        return  new ResponseEntity<Long>(demandeDto.getId(), HttpStatus.CREATED);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteDemande(@RequestBody List<Long> idList) throws Exception {

        if (idList == null || idList.isEmpty())
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);

        iDemandeService.deleteDemande(idList);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DemandeDto> updateDemande(@PathVariable(name = "id") Long id, @RequestBody DemandeDto demandeDto) throws Exception{
        demandeDto = iDemandeService.updateDemande(id, demandeDto);
        return ResponseEntity.ok(demandeDto);
    }


    @PostMapping("/paginatedListByCriteria")
    public @ResponseBody
    ResponseEntity<PaginatedList> paginatedListDemande(@RequestBody DemandeCriteria demandeCriteria) throws Exception {

        List<DemandeDto> list = iDemandeService.paginatedListDemandes(demandeCriteria,demandeCriteria.getPage(),demandeCriteria.getMaxResults(), demandeCriteria.getSortOrder(), demandeCriteria.getSortField());

        PaginatedList paginatedList=new PaginatedList();
        paginatedList.setList(list);
        if (list != null && !list.isEmpty()) {
            int dateSize = iDemandeService.getDemandeDataSize(demandeCriteria);
            paginatedList.setDataSize(dateSize);
        }

        return new ResponseEntity<PaginatedList>(paginatedList, HttpStatus.OK);

    }
}
