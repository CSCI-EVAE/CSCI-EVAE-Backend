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
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class RubriqueQuestionServiceImpl implements RubriqueQuestionService {
    private final RubriqueQuestionRepository rubriqueQuestionRepository;
    private final RubriqueRepository rubriqueRepository;
    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RubriqueQuestionServiceImpl(RubriqueQuestionRepository rubriqueQuestionRepository, RubriqueRepository rubriqueRepository, QuestionRepository questionRepository, ModelMapper modelMapper, RubriqueMapper rubriqueMapper, QuestionMapper questionMapper) {
        this.rubriqueQuestionRepository = rubriqueQuestionRepository;
        this.rubriqueRepository = rubriqueRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    private RubriqueQuestion convertToEntity(RubriqueQuestionDTO rubriqueQuestionAddDTO) {
        return modelMapper.map(rubriqueQuestionAddDTO, RubriqueQuestion.class);
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
        Rubrique rubrique = rubriqueRepository.findById(dto.getIdRubrique()).get();
        Set<RubriqueQuestion> rubriqueQuestions = rubrique.getRubriqueQuestions().stream().peek(q -> {
            if (dto.getQuestionIds().containsKey(q.getIdQuestion().getId().longValue())) {
                q.setOrdre(dto.getQuestionIds().get(q.getIdQuestion().getId().longValue()));
                resultMessage.append("On a mis à jour l'ordre de la question: Rubrique: ").append(dto.getIdRubrique()).append(", Question: ").append(q.getIdQuestion().getId()).append("\n");
                rubriqueQuestionRepository.save(q);
            } else {
                rubriqueQuestionRepository.delete(q);
            }
        }).collect(Collectors.toSet());
        dto.getQuestionIds().forEach((k, v) -> {
            if (rubriqueQuestions.stream().noneMatch(q -> q.getIdQuestion().getId().equals(k.intValue()))) {
                Question question = questionRepository.findById(k).get();
                RubriqueQuestion rubriqueQuestion = new RubriqueQuestion(new RubriqueQuestionId(rubrique.getId(), question.getId()), rubrique, question, v);
                resultMessage.append("On a ajouté la question: Rubrique: ").append(dto.getIdRubrique()).append(", Question: ").append(question.getId()).append("\n");
                rubriqueQuestionRepository.save(rubriqueQuestion);
            }
        });
        return resultMessage.toString();
    }

    @Transactional
    @Override
    public String deleteAllRubriqueQuestion(Long id) {
        int result = rubriqueQuestionRepository.deleteAllByRubriqueId(id.intValue());
        return "On a supprimé " + result + " RubriqueQuestions";
    }

}