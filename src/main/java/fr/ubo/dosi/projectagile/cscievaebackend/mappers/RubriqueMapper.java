package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Rubrique;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RubriqueMapper
{
    RubriqueDTO rubriqueToRubriqueDTO(Rubrique rubrique);

}
