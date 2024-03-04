package fr.ubo.dosi.projectagile.cscievaebackend.services;


import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QualificatifDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.LinkedToAnotherResourceException;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface QualificatifService {
    Qualificatif createQualificatif(Qualificatif qualificatif);

    List<QualificatifDTO> getAllQualificatifs();

    Optional<Qualificatif> getQualificatifById(Long id) throws ResourceNotFoundException;

    Qualificatif updateQualificatif(Long id, Qualificatif qualificatif);

    void deleteQualificatif(Long id) ;


}
