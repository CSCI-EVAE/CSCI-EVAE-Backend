package fr.ubo.dosi.projectagile.cscievaebackend.repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RubriqueQuestionRepository extends JpaRepository<RubriqueQuestion, RubriqueQuestionId> {
}