package ma.epicproject.backend.controller;


import ma.epicproject.backend.common.bean.BaseController;
import ma.epicproject.backend.common.bean.PaginatedList;
import ma.epicproject.backend.criteria.OuvrierCriteria;
import ma.epicproject.backend.dto.OuvrierDto;
import ma.epicproject.backend.service.IOuvrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/ouvrier")
@CrossOrigin("*")
public class OuvrierController extends BaseController {

    @Autowired
    private IOuvrierService iOuvrierService;

    @GetMapping("/{id}")
    public ResponseEntity<OuvrierDto> getOuvrierById(@PathVariable("id") Long id, String[] includes, String[] excludes) throws Exception {

        OuvrierDto ouvrier = iOuvrierService.getOuvrierById(id);


        return new ResponseEntity<OuvrierDto>(ouvrier, HttpStatus.OK);

    }
    @PostMapping()
    public ResponseEntity<Long> addOuvrier(@RequestBody OuvrierDto ouvrierDto) throws Exception {
        ouvrierDto = iOuvrierService.createOuvrier(ouvrierDto);
        return  new ResponseEntity<Long>(ouvrierDto.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteOuvrier(@RequestBody List<Long> idList) throws Exception {

        if (idList == null || idList.isEmpty())
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);

        iOuvrierService.deleteOuvrier(idList);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OuvrierDto> updateOuvrier(@PathVariable(name = "id") Long id, @RequestBody OuvrierDto ouvrierDto) throws Exception{
        ouvrierDto = iOuvrierService.updateOuvrier(id, ouvrierDto);
        return ResponseEntity.ok(ouvrierDto);
    }


    @PostMapping("/paginatedListByCriteria")
    public @ResponseBody
    ResponseEntity<PaginatedList> paginatedListOuvrier(@RequestBody OuvrierCriteria ouvrierCriteria) throws Exception {

        List<OuvrierDto> list = iOuvrierService.paginatedListOuvriers(ouvrierCriteria,ouvrierCriteria.getPage(),ouvrierCriteria.getMaxResults(), ouvrierCriteria.getSortOrder(), ouvrierCriteria.getSortField());

        PaginatedList paginatedList=new PaginatedList();
        paginatedList.setList(list);
        if (list != null && !list.isEmpty()) {
            int dateSize = iOuvrierService.getOuvrierDataSize(ouvrierCriteria);
            paginatedList.setDataSize(dateSize);
        }

        return new ResponseEntity<PaginatedList>(paginatedList, HttpStatus.OK);

    }
}
