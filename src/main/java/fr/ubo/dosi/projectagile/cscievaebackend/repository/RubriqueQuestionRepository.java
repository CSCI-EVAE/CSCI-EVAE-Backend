package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RubriqueQuestionRepository extends JpaRepository<RubriqueQuestion, RubriqueQuestionId> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RubriqueQuestion r WHERE r.idRubrique.id = ?1 AND r.idQuestion.id = ?2")
    Boolean existsByIdRubriqueAndIdQuestion(Integer id, Integer id1);

    @Modifying
    @Query("DELETE FROM RubriqueQuestion r WHERE r.idRubrique.id = ?1 AND r.idQuestion.id = ?2")
    void deleteByIdRubriqueAndIdQuestion(Long idRubrique, Long questionId);

    @Query("SELECT r FROM RubriqueQuestion r WHERE r.idRubrique.id = ?1")
    List<RubriqueQuestion> findByRubriqueId(Long idRubrique);
}