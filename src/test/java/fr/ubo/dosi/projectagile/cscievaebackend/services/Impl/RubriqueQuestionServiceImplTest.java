package fr.ubo.dosi.projectagile.cscievaebackend.services.Impl;
import fr.ubo.dosi.projectagile.cscievaebackend.DTO.RubriqueQuestionDTO;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestion;
import fr.ubo.dosi.projectagile.cscievaebackend.model.RubriqueQuestionId;
import fr.ubo.dosi.projectagile.cscievaebackend.repository.RubriqueQuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RubriqueQuestionServiceImplTest {

    @InjectMocks
    private RubriqueQuestionServiceImpl rubriqueQuestionService;

    @Mock
    private RubriqueQuestionRepository rubriqueQuestionRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testAddRubriqueQuestion() {
        RubriqueQuestionDTO rubriqueQuestionAddDTO = new RubriqueQuestionDTO();
        RubriqueQuestion rubriqueQuestionEntity = new RubriqueQuestion();
        when(modelMapper.map(any(RubriqueQuestionDTO.class), eq(RubriqueQuestion.class))).thenReturn(rubriqueQuestionEntity);
        when(rubriqueQuestionRepository.save(any(RubriqueQuestion.class))).thenReturn(rubriqueQuestionEntity);
        RubriqueQuestionDTO expectedDTO = new RubriqueQuestionDTO();
        when(modelMapper.map(any(RubriqueQuestion.class), eq(RubriqueQuestionDTO.class))).thenReturn(expectedDTO);
        RubriqueQuestionDTO result = rubriqueQuestionService.addRubriqueQuestion(rubriqueQuestionAddDTO);
        assertEquals(expectedDTO, result);
        verify(modelMapper, times(1)).map(any(RubriqueQuestionDTO.class), eq(RubriqueQuestion.class));
        verify(rubriqueQuestionRepository, times(1)).save(any(RubriqueQuestion.class));
        verify(modelMapper, times(1)).map(any(RubriqueQuestion.class), eq(RubriqueQuestionDTO.class));
    }

    @Test
    public void testSaveRubriqueQuestion() {
        RubriqueQuestion rubriqueQuestion = new RubriqueQuestion();
        when(rubriqueQuestionRepository.save(any(RubriqueQuestion.class))).thenReturn(rubriqueQuestion);
        RubriqueQuestion result = rubriqueQuestionService.saveRubriqueQuestion(rubriqueQuestion);
        assertEquals(rubriqueQuestion, result);
        verify(rubriqueQuestionRepository, times(1)).save(any(RubriqueQuestion.class));
    }

    @Test
    public void testDeleteRubriqueQuestion() {
        RubriqueQuestion rubriqueQuestion = new RubriqueQuestion();
        rubriqueQuestionService.deleteRubriqueQuestion(rubriqueQuestion);
        verify(rubriqueQuestionRepository, times(1)).delete(any(RubriqueQuestion.class));
    }

    @Test
    public void testGetAllRubriqueQuestions() {
        List<RubriqueQuestion> rubriqueQuestionList = new ArrayList<>();
        when(rubriqueQuestionRepository.findAll()).thenReturn(rubriqueQuestionList);
        List<RubriqueQuestionDTO> expectedDTOList = new ArrayList<>();
        lenient().when(modelMapper.map(any(RubriqueQuestion.class), eq(RubriqueQuestionDTO.class))).thenReturn(new RubriqueQuestionDTO());
        List<RubriqueQuestionDTO> result = rubriqueQuestionService.getAllRubriqueQuestions();
        assertEquals(expectedDTOList.size(), result.size());
        verify(rubriqueQuestionRepository, times(1)).findAll();
        verify(modelMapper, times(rubriqueQuestionList.size())).map(any(RubriqueQuestion.class), eq(RubriqueQuestionDTO.class));
    }
    @Test
    public void testGetRubriqueQuestionById() {
        RubriqueQuestionId rubriqueQuestionId = new RubriqueQuestionId();
        Optional<RubriqueQuestion> optionalRubriqueQuestion = Optional.of(new RubriqueQuestion());
        when(rubriqueQuestionRepository.findById(any(RubriqueQuestionId.class))).thenReturn(optionalRubriqueQuestion);
        RubriqueQuestionDTO expectedDTO = new RubriqueQuestionDTO();
        when(modelMapper.map(any(RubriqueQuestion.class), eq(RubriqueQuestionDTO.class))).thenReturn(expectedDTO);
        RubriqueQuestionDTO result = rubriqueQuestionService.getRubriqueQuestionById(rubriqueQuestionId);
        assertEquals(expectedDTO, result);
        verify(rubriqueQuestionRepository, times(1)).findById(any(RubriqueQuestionId.class));
        verify(modelMapper, times(1)).map(any(RubriqueQuestion.class), eq(RubriqueQuestionDTO.class));
    }
}
