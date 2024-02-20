package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.UniteEnseignementDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.UniteEnseignementDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.*;
import fr.ubo.dosi.projectagile.cscievaebackend.ResponceHandler.ApiResponse;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.*;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.UniteEnseignementServiceImpl;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/enseignant/ue")
public class UniteEnseignementController {

    private final userService userService;

    @Autowired
    private EnseignantRepository er;

    @Autowired
    private FormationRepository fr;

    @Autowired
    private EvaluationRepository evr;

    @Autowired
    private PromotionRepository pr;

    @Autowired
    private UniteEnseignementRepository uer;

    @Autowired
    private UniteEnseignementServiceImpl usi;

    @Autowired
    public UniteEnseignementController(userService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public ApiResponse<List<UniteEnseignementDTO>> getAll() {
        Authentification authentication = userService.getCurrentUser();
        Enseignant e = er.findByEmailUbo(authentication.getLoginConnection()).get();
        List<UniteEnseignement> lue= uer.findAllByNoEnseignant(e);
        List<UniteEnseignementDTO> ret = new ArrayList<>();
        for (UniteEnseignement u:lue){
            UniteEnseignementDTO tmp = new UniteEnseignementDTO();
            tmp.setCodeUe(u.getId().getCodeUe());
            Formation f = fr.findById(u.getCodeFormation().getCodeFormation()).get();
            tmp.setNomFormation(f.getNomFormation());
            tmp.setAnneeUniversitaire(pr.findByCodeFormation(f.getCodeFormation()).get(0).getId().getAnneeUniversitaire());
            if (!evr.findByCodeUE(u.getId().getCodeUe()).isEmpty()){
                Evaluation eva = evr.findByCodeUE(u.getId().getCodeUe()).get(0);
                tmp.setEvaluationId(eva.getId());
                tmp.setDesignation(eva.getDesignation());
                tmp.setDebutReponse(eva.getDebutReponse());
                tmp.setFinReponse(eva.getFinReponse());
                //if(eva.getElementConstitutif().getId().getCodeEc()!=null)tmp.setCodeEc(eva.getElementConstitutif().getId().getCodeEc());
                tmp.setEtat(evr.findByCodeUE(u.getId().getCodeUe()).get(0).getEtat());
                tmp.setEvaExiste(true);
            }

            ret.add(tmp);

        }
        return ApiResponse.ok(ret);
    }
}
