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
    private final RubriqueMapper rubriqueMapper;
    private final QuestionMapper questionMapper;

    @Autowired
    public RubriqueQuestionServiceImpl(RubriqueQuestionRepository rubriqueQuestionRepository, RubriqueRepository rubriqueRepository, QuestionRepository questionRepository, ModelMapper modelMapper, RubriqueMapper rubriqueMapper, QuestionMapper questionMapper) {
        this.rubriqueQuestionRepository = rubriqueQuestionRepository;
        this.rubriqueRepository = rubriqueRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
        this.rubriqueMapper = rubriqueMapper;
        this.questionMapper = questionMapper;
    }

    Logger logger = Logger.getLogger(RubriqueQuestionServiceImpl.class.getName());


   /* @Override
    public RubriqueQuestionDTO addRubriqueQuestion(RubriqueQuestionDTO rubriqueQuestionAddDTO) {
        RubriqueQuestion rubriqueQuestion = convertToEntity(rubriqueQuestionAddDTO);
        rubriqueQuestion = rubriqueQuestionRepository.save(rubriqueQuestion);
        return convertToDto(rubriqueQuestion);
    }*/

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
            rubriqueQuestionsDTO.setIdRubrique(rubriqueMapper.rubriqueToRubriqueDTO(entry.getKey()));
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

    @Override
    @Transactional
    public String processAndStore(List<IncomingRubriqueQuestionDTO> incomingData) {
        StringBuilder resultMessage = new StringBuilder();

        for (IncomingRubriqueQuestionDTO dto : incomingData) {
            AddRubriqueQuestions(dto, resultMessage);
        }

        if (resultMessage.isEmpty()) {
            return "All data processed successfully.";
        } else {
            return resultMessage.toString();
        }
    }

    @Override
    public String AjouterRubriqueQuestion(IncomingRubriqueQuestionDTO dto) {
        StringBuilder resultMessage = new StringBuilder();
        AddRubriqueQuestions(dto, resultMessage);

        if (resultMessage.isEmpty()) {
            return "All data processed successfully.";
        } else {
            return resultMessage.toString();
        }
    }

    @Override
    @Transactional
    public String updateRubriqueQuestions(IncomingRubriqueQuestionDTO dto) {
        StringBuilder resultMessage = new StringBuilder();

        List<RubriqueQuestion> existingQuestions = rubriqueQuestionRepository.findByRubriqueId(dto.getIdRubrique());
        logger.info("Existing RubriqueQuestions: " + existingQuestions);
        Set<Long> existingQuestionIds = existingQuestions.stream()
                .map(rubriqueQuestion -> rubriqueQuestion.getIdQuestion().getId().longValue())
                .collect(Collectors.toSet());
        logger.info("Existing QuestionIds: " + existingQuestionIds);
        Set<Long> incomingQuestionIds = new HashSet<>(dto.getQuestionIds());

        // Determine questions to add
        Set<Long> toAdd = new HashSet<>(incomingQuestionIds);
        toAdd.removeAll(existingQuestionIds);
        logger.info("Questions to add: " + toAdd);
        // Determine questions to remove
        Set<Long> toRemove = new HashSet<>(existingQuestionIds);
        toRemove.removeAll(incomingQuestionIds);
        logger.info("Questions to remove: " + toRemove);
        // Remove questions
        toRemove.forEach(questionId -> {
            rubriqueQuestionRepository.deleteByIdRubriqueAndIdQuestion(dto.getIdRubrique(), questionId);
            resultMessage.append("On a supprimé la question: Rubrique: ").append(dto.getIdRubrique()).append(", Question: ").append(questionId).append("\n");
        });
        logger.info("Removed RubriqueQuestions: " + toRemove);

        toAdd.forEach(questionId -> {
            try {
                Question question = questionRepository.findById(questionId)
                        .orElseThrow(() -> new EntityNotFoundException("Question not found: " + questionId));
                RubriqueQuestion rubriqueQuestion = new RubriqueQuestion(new RubriqueQuestionId(dto.getIdRubrique().intValue(), questionId.intValue()), rubriqueRepository.findById(dto.getIdRubrique()).get(), question, dto.getOrdre());
                rubriqueQuestionRepository.save(rubriqueQuestion);
                resultMessage.append("Added RubriqueQuestion: Rubrique: ").append(dto.getIdRubrique()).append(", Question: ").append(questionId).append("\n");
            } catch (EntityNotFoundException e) {
                resultMessage.append(e.getMessage()).append("\n");
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


    private void AddRubriqueQuestions(IncomingRubriqueQuestionDTO dto, StringBuilder resultMessage) {
        try {
            Rubrique rubrique = rubriqueRepository.findById(dto.getIdRubrique())
                    .orElseThrow(() -> new EntityNotFoundException("Rubrique not found: " + dto.getIdRubrique()));

            for (Long questionId : dto.getQuestionIds()) {
                Question question = questionRepository.findById(questionId)
                        .orElseThrow(() -> new EntityNotFoundException("Question not found: " + questionId));
                logger.info("RubriqueQuestion added: Rubrique: " + rubrique + ", Question: " + question);
                if (!rubriqueQuestionRepository.existsByIdRubriqueAndIdQuestion(rubrique.getId(), question.getId())) {
                    logger.info("RubriqueQuestion added: Rubrique: " + rubrique + ", Question: " + question);
                    RubriqueQuestion rubriqueQuestion = new RubriqueQuestion();
                    rubriqueQuestion.setId(new RubriqueQuestionId(rubrique.getId(), question.getId()));
                    rubriqueQuestion.setIdRubrique(rubrique);
                    logger.info("RubriqueQuestion added: Rubrique: " + rubriqueQuestion.getIdRubrique());
                    rubriqueQuestion.setIdQuestion(question);
                    logger.info("RubriqueQuestion added: Question: " + rubriqueQuestion.getIdQuestion());
                    rubriqueQuestion.setOrdre(dto.getOrdre());
                    logger.info("RubriqueQuestion added: Ordre: " + rubriqueQuestion.getOrdre());
                    rubriqueQuestionRepository.save(rubriqueQuestion);
                    resultMessage.append("RubriqueQuestion added: ")
                            .append("Rubrique: ").append(rubrique.getId())
                            .append(", Question: ").append(question.getId())
                            .append("\n");
                } else {
                    resultMessage.append("RubriqueQuestion already exists: ")
                            .append("Rubrique: ").append(rubrique.getId())
                            .append(", Question: ").append(question.getId())
                            .append("\n");
                }
            }
        } catch (EntityNotFoundException e) {
            resultMessage.append(e.getMessage()).append("\n");
        }
    }


}