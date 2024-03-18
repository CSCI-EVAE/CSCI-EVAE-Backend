package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QualificatifRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QuestionService;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QualificatifRepository qualificatifRepository;

    @Transactional
    @Override
    public Question createQuestion(Question question) {
        if (questionRepository.existsByIntitule(question.getIntitule())) {
            throw new IllegalArgumentException("La question existe déjà");
        }
        if (qualificatifRepository.existsByQualificatifId(question.getIdQualificatif().getId())) {
            throw new IllegalArgumentException("Le qualificatif n'existe pas !!");
        }
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        if (questionRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("la liste des questions est vide");
        }else {
            Sort sortByIntitule = Sort.by(Sort.Direction.DESC, "intitule");
            return questionRepository.findAll(sortByIntitule);
        }
    }

    @Override
    public Question getQuestionById(Long id)  {
        if (questionRepository.findById(id).isPresent()) {
            return questionRepository.findById(id).get();
        } else {
            throw new IllegalArgumentException("La question n'a pas été trouvée pour cet id : " + id);
        }
    }

    @Override
    public Question updateQuestion(Long id, Question question)  {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question questionUpdate = optionalQuestion.get();
            if (!questionUpdate.getIntitule().equals(question.getIntitule()) && questionRepository.existsByIntitule(question.getIntitule())) {
                throw new IllegalArgumentException("La question existe déjà");
            }
            questionUpdate.setType(question.getType());
            questionUpdate.setIntitule(question.getIntitule());
            questionUpdate.setIdQualificatif(question.getIdQualificatif());
            questionUpdate.setNoEnseignant(question.getNoEnseignant());
            return questionRepository.save(questionUpdate);
        } else {
            throw new IllegalArgumentException("La question n'a pas été trouvée pour cet id : " + id);
        }
    }

    @Override
    public void deleteQuestion(Long id) throws ResourceNotFoundException {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            try {
                questionRepository.deleteById(id);
            } catch (Exception e) {
                throw new IllegalArgumentException("La question est liée à un autre objet");
            }
        } else {
            throw new ResourceNotFoundException("La question n'a pas été trouvée pour cet id :: " + id);
        }
    }

}