package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QuestionService;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Transactional
    @Override
    public Question createQuestion(Question question) {
        List<Question> questions = questionRepository.findAll();
        for (Question q : questions) {
            if (q.getIntitule().equals(question.getIntitule())) {
                return null;
            }
        }
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public Question getQuestionById(Long id) throws ResourceNotFoundException {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found for this id :: " + id));
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws ResourceNotFoundException {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question questionUpdate = optionalQuestion.get();
            questionUpdate.setType(question.getType());
            questionUpdate.setIntitule(question.getIntitule());
            questionUpdate.setIdQualificatif(question.getIdQualificatif());
            questionUpdate.setNoEnseignant(question.getNoEnseignant());
            return questionRepository.save(questionUpdate);
        } else {
            throw new ResourceNotFoundException("Question not found for this id :: " + id);
        }
    }

    @Override
    public void deleteQuestion(Long id) throws ResourceNotFoundException {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            questionRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Question not found for this id :: " + id);
        }
    }


    @Override
    public List<Question> findQuestionsByQualificatifId(Qualificatif idQualificatif) {
        return questionRepository.findByIdQualificatif(idQualificatif);
    }
}