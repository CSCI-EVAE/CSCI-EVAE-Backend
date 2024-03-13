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
        Optional<Rubrique> rubriqueOptional = rubriqueRepository.findById(dto.getIdRubrique());

        if (!rubriqueOptional.isPresent()) {
            throw new IllegalArgumentException("Rubrique with ID " + dto.getIdRubrique() + " not found.");
        }

        Rubrique rubrique = rubriqueOptional.get();
        Set<Long> dtoQuestionIds = dto.getQuestionIds().keySet();

        rubrique.getRubriqueQuestions().forEach(rubriqueQuestion -> {
            Long questionId = rubriqueQuestion.getIdQuestion().getId().longValue();
            if (dtoQuestionIds.contains(questionId)) {
                rubriqueQuestion.setOrdre(dto.getQuestionIds().get(questionId));
                resultMessage.append(String.format("Updated question order: Rubrique: %d, Question: %d\n", dto.getIdRubrique(), questionId));
            } else {
                rubriqueQuestionRepository.delete(rubriqueQuestion);
                resultMessage.append(String.format("Deleted question: Rubrique: %d, Question: %d\n", dto.getIdRubrique(), questionId));
            }
        });

        dto.getQuestionIds().forEach((questionId, ordre) -> {
            if (rubrique.getRubriqueQuestions().stream().noneMatch(q -> q.getIdQuestion().getId().equals(questionId.intValue()))) {
                Question question = questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Question with ID " + questionId + " not found."));
                RubriqueQuestion rubriqueQuestion = new RubriqueQuestion(new RubriqueQuestionId(rubrique.getId(), question.getId()), rubrique, question, ordre);
                rubriqueQuestionRepository.save(rubriqueQuestion);
                resultMessage.append(String.format("Added new question: Rubrique: %d, Question: %d\n", dto.getIdRubrique(), questionId));
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