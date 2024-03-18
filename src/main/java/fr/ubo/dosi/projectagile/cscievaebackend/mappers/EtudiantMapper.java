package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.EtudiantDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Etudiant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EtudiantMapper {

    @Mapping(source = "promotion.id.anneeUniversitaire", target = "anneeUniversitaire")
    @Mapping(source = "promotion.id.codeFormation", target = "CodeFormation")
    EtudiantDTO etudiantToEtudiantDTO(Etudiant etudiant);


    @Mapping(target = "promotion.id.anneeUniversitaire", ignore = true)
    @Mapping(target = "promotion.id.codeFormation", ignore = true)
    @Mapping(target = "reponseEvaluations", ignore = true)
    Etudiant etudiantDTOToEtudiant(EtudiantDTO etudiantDTO);

    @Mapping(target = "noEtudiant", ignore = true)
    void updateEtudiantFromDTO(EtudiantDTO etudiantDTO, @MappingTarget Etudiant etudiant);

}
