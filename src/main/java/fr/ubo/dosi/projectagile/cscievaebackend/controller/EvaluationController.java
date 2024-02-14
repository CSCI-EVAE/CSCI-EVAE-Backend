package fr.ubo.dosi.projectagile.cscievaebackend.controller;


import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.services.Impl.EvaluationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/evaluation")
@CrossOrigin(origins = "http://localhost:3000")
public class EvaluationController {

    @Autowired
    private EvaluationServiceImpl es;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("getAll")
    public List<Evaluation> getAll(){
        return es.getAll();
    }
}