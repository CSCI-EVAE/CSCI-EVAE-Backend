package fr.ubo.dosi.projectagile.cscievaebackend.mappers;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QualificatifDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QualificatifMapper {
    QualificatifDTO qualificatifToQualificatifDTO(Qualificatif qualificatif);

    Qualificatif qualificatifDTOToQualificatif(QualificatifDTO qualificatifDTO);
}
