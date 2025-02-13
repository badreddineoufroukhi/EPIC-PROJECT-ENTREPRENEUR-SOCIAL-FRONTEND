package ma.epicproject.backend.controller;


import ma.epicproject.backend.common.bean.BaseController;
import ma.epicproject.backend.common.bean.PaginatedList;
import ma.epicproject.backend.criteria.UtilisateurCriteria;
import ma.epicproject.backend.dto.UtilisateurDto;
import ma.epicproject.backend.service.IUtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/utilisateur")
@CrossOrigin("*")
public class UtilisateurController extends BaseController {

    @Autowired
    private IUtilisateurService iUtilisateurService;

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDto> getUtilisateurById(@PathVariable("id") Long id, String[] includes, String[] excludes) throws Exception {

        UtilisateurDto utilisateur = iUtilisateurService.getUtilisateurById(id);


        return new ResponseEntity<UtilisateurDto>(utilisateur, HttpStatus.OK);

    }
    @PostMapping()
    public ResponseEntity<Long> addUtilisateur(@RequestBody UtilisateurDto utilisateurDto) throws Exception {
        utilisateurDto = iUtilisateurService.createUtilisateur(utilisateurDto);
        return  new ResponseEntity<Long>(utilisateurDto.getId(), HttpStatus.CREATED);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUtilisateur(@RequestBody List<Long> idList) throws Exception {

        if (idList == null || idList.isEmpty())
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);

        iUtilisateurService.deleteUtilisateur(idList);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDto> updateUtilisateur(@PathVariable(name = "id") Long id, @RequestBody UtilisateurDto utilisateurDto) throws Exception{
            utilisateurDto = iUtilisateurService.updateUtilisateur(id, utilisateurDto);
            return ResponseEntity.ok(utilisateurDto);
    }


    @PostMapping("/paginatedListByCriteria")
    public @ResponseBody
    ResponseEntity<PaginatedList> paginatedListUtilisateur(@RequestBody UtilisateurCriteria utilisateurCriteria) throws Exception {

        List<UtilisateurDto> list = iUtilisateurService.paginatedListUtilisateurs(utilisateurCriteria,utilisateurCriteria.getPage(),utilisateurCriteria.getMaxResults(), utilisateurCriteria.getSortOrder(), utilisateurCriteria.getSortField());

        PaginatedList paginatedList=new PaginatedList();
        paginatedList.setList(list);
        if (list != null && !list.isEmpty()) {
            int dateSize = iUtilisateurService.getUtilisateurDataSize(utilisateurCriteria);
            paginatedList.setDataSize(dateSize);
        }

        return new ResponseEntity<PaginatedList>(paginatedList, HttpStatus.OK);

    }
}
