package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.IncomingRubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Evaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueEvaluation;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RubriqueEvaluationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class RubriqueEvaluationServiceImplTest {
    @InjectMocks
    private RubriqueEvaluationServiceImpl rubriqueEvaluationService;

    @Mock
    private RubriqueEvaluationRepository rubriqueEvaluationRepository;



}