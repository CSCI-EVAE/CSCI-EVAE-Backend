package fr.ubo.dosi.projectagile.cscievaebackend.services;



import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;

import java.util.List;
import java.util.Optional;

public interface QualificatifService {
    public Qualificatif createQualificatif(Qualificatif qualificatif);

    public List<Qualificatif> getAllQualificatifs();

    public Optional<Qualificatif> getQualificatifById(Long id) throws ResourceNotFoundException;

    public Qualificatif updateQualificatif(Long id, Qualificatif qualificatif) throws ResourceNotFoundException;

    public void deleteQualificatif(Long id) throws ResourceNotFoundException;


}
