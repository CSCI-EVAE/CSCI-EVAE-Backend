package fr.ubo.dosi.projectagile.cscievaebackend.mappers;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.ElementConstitutifDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.ElementConstitutif;

public interface ElementConstitutifMapper {
    ElementConstitutifDTO elementConstitutifToElementConstitutifDTO(ElementConstitutif elementConstitutif);
}
