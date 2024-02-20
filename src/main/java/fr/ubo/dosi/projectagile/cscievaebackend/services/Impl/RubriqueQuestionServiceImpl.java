package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QuestionOrdreDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionsDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.QuestionMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.mappers.RubriqueMapper;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RubriqueQuestionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.RubriqueQuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RubriqueQuestionServiceImpl implements RubriqueQuestionService {

    @Autowired
    private RubriqueQuestionRepository rubriqueQuestionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RubriqueMapper rubriqueMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public RubriqueQuestionDTO addRubriqueQuestion(RubriqueQuestionDTO rubriqueQuestionAddDTO) {
        RubriqueQuestion rubriqueQuestion = convertToEntity(rubriqueQuestionAddDTO);
        rubriqueQuestion = rubriqueQuestionRepository.save(rubriqueQuestion);
        return convertToDto(rubriqueQuestion);
    }
    private RubriqueQuestion convertToEntity(RubriqueQuestionDTO rubriqueQuestionAddDTO) {
        return modelMapper.map(rubriqueQuestionAddDTO, RubriqueQuestion.class);
    }

    @Override
    public RubriqueQuestion saveRubriqueQuestion(RubriqueQuestion rubriqueQuestion) {
        return rubriqueQuestionRepository.save(rubriqueQuestion);
    }

    @Override
    public List<RubriqueQuestionsDTO> findAllQuestionsForRubriques() {
        List<RubriqueQuestion> rubriqueQuestions = rubriqueQuestionRepository.findAll();
        Map<Rubrique, List<RubriqueQuestion>> rubriqueToQuestionsMap = new HashMap<>();

        for (RubriqueQuestion rq : rubriqueQuestions) {
            rubriqueToQuestionsMap.computeIfAbsent(rq.getIdRubrique(), k -> new ArrayList<>()).add(rq);
        }
        List<RubriqueQuestionsDTO> rubriqueQuestionsDTOList = new ArrayList<>();
        for (Map.Entry<Rubrique, List<RubriqueQuestion>> entry : rubriqueToQuestionsMap.entrySet()) {
            RubriqueQuestionsDTO rubriqueQuestionsDTO = new RubriqueQuestionsDTO();
            rubriqueQuestionsDTO.setIdRubrique( rubriqueMapper.rubriqueToRubriqueDTO(entry.getKey()));
            rubriqueQuestionsDTO.setQuestionsOrdre(entry.getValue().stream().map(rubriqueQuestion -> {
                QuestionOrdreDTO questionOrdreDTO = new QuestionOrdreDTO();
                QuestionDTO questionDTO = questionMapper.questionToQuestionDTO(rubriqueQuestion.getIdQuestion());
                questionOrdreDTO.setIdQuestion(questionDTO);
                questionOrdreDTO.setOrdre(rubriqueQuestion.getOrdre());
                return questionOrdreDTO;
            }).collect(Collectors.toList()));

            rubriqueQuestionsDTOList.add(rubriqueQuestionsDTO);
        }
        return rubriqueQuestionsDTOList;
    }

    @Override
    public void deleteRubriqueQuestion(RubriqueQuestion rubriqueQuestion) {
            rubriqueQuestionRepository.delete(rubriqueQuestion);
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
    public RubriqueQuestionDTO getRubriqueQuestionById(RubriqueQuestionId rubriqueQuestionId) {
         Optional<RubriqueQuestion> rubriqueQuestionOptional = rubriqueQuestionRepository.findById(rubriqueQuestionId);
        return rubriqueQuestionOptional.map(this::convertToDto).orElse(null);
    }

}