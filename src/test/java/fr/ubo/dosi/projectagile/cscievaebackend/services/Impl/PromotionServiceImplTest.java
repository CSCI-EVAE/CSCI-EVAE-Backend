package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;

import fr.ubo.dosi.projectagile.cscievaebackend.model.Promotion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.PromotionId;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.PromotionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PromotionServiceImplTest {
    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    @Test
    public void testGetPromotionById() {
        PromotionId promotionId = new PromotionId("exampleCode", "2023");
        Promotion promotion = new Promotion();
        promotion.setId(promotionId);
        when(promotionRepository.findByPromotionId(promotionId.getCodeFormation(), promotionId.getAnneeUniversitaire())).thenReturn(promotion);
        Promotion result = promotionService.getPromotionById(promotionId);
        assertNotNull(result);
        assertEquals(promotion, result);
    }

    @Test
    public void testGetAllPromotionsNotEmpty() {
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion());
        promotions.add(new Promotion());
        when(promotionRepository.findAll()).thenReturn(promotions);
        List<Promotion> result = promotionService.getAllPromotions();
        assertNotNull(result);
        assertEquals(promotions, result);
    }

    @Test
    public void testGetAllPromotionsEmpty() {
        when(promotionRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(IllegalArgumentException.class, () -> promotionService.getAllPromotions());
    }

    @Test
    public void testFindPromotionByAnneeUniversitaireAndCodeFormation() {
        String codeFormation = "exampleCode";
        String anneeUniversitaire = "2023";
        Promotion promotion = new Promotion();
        when(promotionRepository.findById_CodeFormationAndId_AnneeUniversitaire(codeFormation, anneeUniversitaire)).thenReturn(promotion);
        Promotion result = promotionService.findPromotionByAnneeUniversitaireAndCodeFormation(anneeUniversitaire, codeFormation);
        assertNotNull(result);
        assertEquals(promotion, result);
    }
}
