package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.exception.LinkedToAnotherResourceException;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QualificatifRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QualificatifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class QualificatifServiceImpl implements QualificatifService {

    @Autowired
    private QualificatifRepository qualificatifRepository;

    @Override
    public Qualificatif createQualificatif(Qualificatif qualificatif) {
        return qualificatifRepository.save(qualificatif);
    }

    @Override
    public List<Qualificatif> getAllQualificatifs() {
        return qualificatifRepository.findAll();
    }

    @Override
    public Optional<Qualificatif> getQualificatifById(Long id) throws ResourceNotFoundException {
        return qualificatifRepository.findById(id);
    }

    @Override
    public Qualificatif updateQualificatif(Long id, Qualificatif qualificatifModifie) throws ResourceNotFoundException {
        Optional<Qualificatif> qualificatifExistant = qualificatifRepository.findById(id);
        if (qualificatifExistant.isPresent()) {
            Qualificatif qualificatif = qualificatifExistant.get();
            qualificatif.setMinimal(qualificatifModifie.getMinimal());
            qualificatif.setMaximal(qualificatifModifie.getMaximal());
            return qualificatifRepository.save(qualificatif);
        } else {
            throw new ResourceNotFoundException("Le qualificatif n'existe pas avec cet id : " + id);
        }
    }

    @Override
    public void deleteQualificatif(Long id) throws ResourceNotFoundException, LinkedToAnotherResourceException {
        Optional<Qualificatif> qualificatifExistant = qualificatifRepository.findById(id);
        System.out.println(qualificatifExistant.get());
        try {
            qualificatifRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Le qualificatif n'existe pas avec cet id : " + id);
        } catch (DataIntegrityViolationException e) {
            throw new LinkedToAnotherResourceException("Le qualificatif est lié à une autre ressource et ne peut pas être supprimé.");
        }
    }
}
