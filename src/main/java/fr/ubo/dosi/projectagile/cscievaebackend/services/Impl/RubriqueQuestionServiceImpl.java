package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.*;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.QuestionMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.RubriqueMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QuestionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RubriqueQuestionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RubriqueRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueQuestionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RubriqueQuestionServiceImpl implements RubriqueQuestionService {
    private final RubriqueQuestionRepository rubriqueQuestionRepository;
    private final RubriqueRepository rubriqueRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RubriqueQuestionServiceImpl(RubriqueQuestionRepository rubriqueQuestionRepository, RubriqueRepository rubriqueRepository, QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.rubriqueQuestionRepository = rubriqueQuestionRepository;
        this.rubriqueRepository = rubriqueRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Rubrique> findAllQuestionsForRubriques() {
        return rubriqueQuestionRepository.findAllRubriques();
    }


    public List<RubriqueQuestionDTO> getAllRubriqueQuestions() {
        List<RubriqueQuestion> rubriqueQuestions = rubriqueQuestionRepository.findAll();
        return rubriqueQuestions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    private RubriqueQuestionDTO convertToDto(RubriqueQuestion rubriqueQuestion) {
        return modelMapper.map(rubriqueQuestion, RubriqueQuestionDTO.class);
    }

    @Override
    public String AjouterRubriqueQuestion(IncomingRubriqueQuestionDTO dto) {
        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append(updateRubriqueQuestions(dto));

        if (resultMessage.isEmpty()) {
            return "All data processed successfully.";
        } else {
            return resultMessage.toString();
        }
    }

    public String updateRubriqueQuestions(IncomingRubriqueQuestionDTO dto) {
        StringBuilder resultMessage = new StringBuilder();
        Optional<Rubrique> rubriqueOptional = rubriqueRepository.findById(dto.getIdRubrique());

        if (rubriqueOptional.isEmpty()) {
            throw new IllegalArgumentException("Rubrique avec ID " + dto.getIdRubrique() + " non trouvée.");
        }

        Rubrique rubrique = rubriqueOptional.get();
        Set<Long> dtoQuestionIds = dto.getQuestionIds().keySet();
        if (dtoQuestionIds.isEmpty()) {
            return "Impossible d'ajouter une rubrique sans questions";
        }

        rubrique.getRubriqueQuestions().forEach(rubriqueQuestion -> {
            Long questionId = rubriqueQuestion.getIdQuestion().getId().longValue();
            if (dtoQuestionIds.contains(questionId)) {
                rubriqueQuestion.setOrdre(dto.getQuestionIds().get(questionId));
            } else {
                rubriqueQuestionRepository.delete(rubriqueQuestion);
            }
        });

        dto.getQuestionIds().forEach((questionId, ordre) -> {
            if (rubrique.getRubriqueQuestions().stream().noneMatch(q -> q.getIdQuestion().getId().equals(questionId.intValue()))) {
                Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Question with ID " + questionId + " not found."));
                RubriqueQuestion rubriqueQuestion = new RubriqueQuestion(new RubriqueQuestionId(rubrique.getId(), question.getId()), rubrique, question, ordre);
                rubriqueQuestionRepository.save(rubriqueQuestion);
            }
        });
        resultMessage.append("La rubrique composée de questions a été mise à jour avec succès");
        return resultMessage.toString();
    }


    @Transactional
    @Override
    public String deleteAllRubriqueQuestion(Long id) {
        rubriqueQuestionRepository.deleteAllByRubriqueId(id.intValue());
        return "La suppression a été effectuée avec succès";
    }

}