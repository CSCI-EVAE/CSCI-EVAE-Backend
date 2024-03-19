package fr.ubo.dosi.projectagile.cscievaebackend.services;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;

import java.util.List;

public interface QuestionService {
    Question createQuestion(Question question);

    List<Question> getAllQuestions();

    Question getQuestionById(Long id);

    Question updateQuestion(Long id, Question question);

    void deleteQuestion(Long id) throws ResourceNotFoundException;

}
