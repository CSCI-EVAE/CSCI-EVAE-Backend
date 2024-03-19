package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.DTO.QualificatifDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.LinkedToAnotherResourceException;
import fr.ubo.dosi.projectagile.cscievaebackend.exception.ResourceNotFoundException;
import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QualificatifRepository;
import fr.ubo.dosi.projectagile.cscievaebackend.services.QualificatifService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QualificatifServiceImpl implements QualificatifService {

    @Autowired
    private QualificatifRepository qualificatifRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Qualificatif createQualificatif(Qualificatif qualificatif) {
        if (Objects.isNull(qualificatif.getMinimal()) || Objects.isNull(qualificatif.getMaximal())) {
            throw new IllegalArgumentException("Les valeurs minimal et maximal sont obligatoires");
        }
        if (qualificatifRepository.existsByMinimalAndMaximalIgnoreCase(qualificatif.getMinimal(), qualificatif.getMaximal())) {
            throw new IllegalArgumentException("Le qualificatif existe déjà avec ces valeurs minimal et maximal");
        }
        return qualificatifRepository.save(qualificatif);
    }

    @Override
    public List<QualificatifDTO> getAllQualificatifs() {
        Sort sortByMinimal = Sort.by(Sort.Direction.DESC, "minimal");
        List<Qualificatif> qualificatifs = qualificatifRepository.findAll(sortByMinimal);
        return qualificatifs.stream().map((element) -> modelMapper.map(element, QualificatifDTO.class)).collect(Collectors.toList());
    }

    @Override
    public Optional<Qualificatif> getQualificatifById(Long id) throws ResourceNotFoundException {
        return Optional.ofNullable(qualificatifRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("qualificatif non trouver de cet id: ")));

    }

    @Override
    public Qualificatif updateQualificatif(Long id, Qualificatif qualificatifModifie) {
        if (Objects.isNull(qualificatifModifie.getMinimal()) || Objects.isNull(qualificatifModifie.getMaximal())) {
            throw new IllegalArgumentException("Les valeurs minimal et maximal sont obligatoires");
        }
        Qualificatif qualificatif = qualificatifRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Le qualificatif n'existe pas avec cet id : " + id));
        if (qualificatifRepository.existsByMinimalAndMaximalIgnoreCase(qualificatifModifie.getMinimal(), qualificatifModifie.getMaximal())) {
            throw new IllegalArgumentException("Le qualificatif existe déjà avec ces valeurs minimal et maximal");
        }
        qualificatif.setMinimal(qualificatifModifie.getMinimal());
        qualificatif.setMaximal(qualificatifModifie.getMaximal());
        return qualificatifRepository.save(qualificatif);
    }

    @Override
    public void deleteQualificatif(Long id) {
        if (qualificatifRepository.existsByQualificatifId(id.intValue())) {
            try {
                qualificatifRepository.deleteById(id);
            } catch (Exception e){
                throw new LinkedToAnotherResourceException("Le qualificatif est lié à une question et ne peut pas être supprimé.");
            }
        } else {
            throw new IllegalArgumentException("Le qualificatif n'existe pas avec cet id : " + id);
        }
    }

}