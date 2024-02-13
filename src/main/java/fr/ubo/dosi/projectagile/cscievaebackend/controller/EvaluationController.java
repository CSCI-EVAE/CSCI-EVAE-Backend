package fr.ubo.dosi.projectagile.cscievaebackend.controller;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.EvaluationRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService es;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("getAll")
    public List<Evaluation> getAll(){
        return es.getAll();
}


}