package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import java.util.List;

public interface QuestionService {
    public Question createQuestion(Question question);
    public List<Question> getAllQuestions();
    public Question getQuestionById(Long id);
    public Question updateQuestion(Long id, Question question) ;
    public void deleteQuestion(Long id) throws ResourceNotFoundException ;

    }
