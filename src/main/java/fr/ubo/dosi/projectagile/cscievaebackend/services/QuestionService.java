package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import java.util.List;

public interface QuestionService {
    public Question createQuestion(Question question);
    public List<Question> getAllQuestions();
    public Question getQuestionById(Integer id)throws ResourceNotFoundException;
    public Question updateQuestion(Integer id, Question question) throws ResourceNotFoundException;
    public void deleteQuestion(Integer id) throws ResourceNotFoundException ;
    public List<Question> findQuestionsByQualificatifId(Qualificatif idQualificatif);

    }
