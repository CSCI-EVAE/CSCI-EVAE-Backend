package fr.ubo.dosi.projectagile.cscievaebackend.Repository;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Qualificatif;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.QualificatifRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class QualificatifRepositoryTest {

    @Autowired
    private QualificatifRepository qualificatifRepository;

    @Test
    public void testSaveQualificatif() {

    }
}
