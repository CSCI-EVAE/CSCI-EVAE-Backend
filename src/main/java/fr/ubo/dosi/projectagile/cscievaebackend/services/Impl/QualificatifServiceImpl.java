package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QualificatifDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.LinkedToAnotherResourceException;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Question;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QualificatifRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QuestionRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QualificatifService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QualificatifServiceImpl implements QualificatifService {

    @Autowired
    private QualificatifRepository qualificatifRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Qualificatif createQualificatif(Qualificatif qualificatif) {
        return qualificatifRepository.save(qualificatif);
    }

    @Override
    public List<QualificatifDTO> getAllQualificatifs() {
        List<Qualificatif> qualificatifs = qualificatifRepository.findAll();
        return qualificatifs.stream().map((element) -> modelMapper.map(element, QualificatifDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<Qualificatif> getQualificatifById(Long id) throws ResourceNotFoundException {
        return Optional.ofNullable(qualificatifRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("qualificatif non trouver de cet id: ")));

    }

    @Override
    public Qualificatif updateQualificatif(Long id, Qualificatif qualificatifModifie) throws ResourceNotFoundException {
        Qualificatif qualificatif = qualificatifRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le qualificatif n'existe pas avec cet id : " + id));
        qualificatif.setMinimal(qualificatifModifie.getMinimal());
        qualificatif.setMaximal(qualificatifModifie.getMaximal());
        return qualificatifRepository.save(qualificatif);
    }

    @Override
    public void deleteQualificatif(Long id) throws ResourceNotFoundException, LinkedToAnotherResourceException {
        Optional<Qualificatif> qualificatifExistant = qualificatifRepository.findById(id);

        if (qualificatifExistant.isPresent()) {
            try {
                checkAndDeleteLinkedRecords(qualificatifExistant.get());

                qualificatifRepository.deleteById(id);
            } catch (DataAccessException e) {
                throw new ResourceNotFoundException("Le qualificatif ne peut pas être supprimé pour des raisons techniques.");
            }
        } else {
            throw new ResourceNotFoundException("Le qualificatif n'existe pas avec cet id : " + id);
        }
    }


    private void checkAndDeleteLinkedRecords(Qualificatif qualificatif) throws LinkedToAnotherResourceException {

        List<Question> linkedQuestions = questionRepository.findByIdQualificatif(qualificatif);
        if (!linkedQuestions.isEmpty()) {
            throw new LinkedToAnotherResourceException("Le qualificatif est lié à une ou plusieurs questions.");
        }
    }
}