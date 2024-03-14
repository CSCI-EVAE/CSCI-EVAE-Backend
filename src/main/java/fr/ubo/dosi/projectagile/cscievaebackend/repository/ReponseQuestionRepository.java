package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ReponseQuestionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReponseQuestionRepository extends JpaRepository<ReponseQuestion, ReponseQuestionId> {
    List<ReponseQuestion> findByIdQuestionEvaluationId(Integer id);
}