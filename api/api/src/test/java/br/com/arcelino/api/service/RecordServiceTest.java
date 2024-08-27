package br.com.arcelino.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.arcelino.api.dto.RecordForm;
import br.com.arcelino.api.dto.RecordResponse;
import br.com.arcelino.api.entity.Record;
import br.com.arcelino.api.mapper.RecordMapper;
import br.com.arcelino.api.repository.RecordRepository;

class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @Mock
    private RecordMapper recordMapper;

    @InjectMocks
    private RecordService recordService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        var currentTime = LocalDateTime.now();
        var recordForm = new RecordForm("service", "customer", "location", currentTime);
        var recordEntity = Record.builder().build();
        var recordResponse = new RecordResponse(1, "service", "customer", "location", currentTime);

        when(recordMapper.toRecord(recordForm)).thenReturn(recordEntity);
        when(recordRepository.save(recordEntity)).thenReturn(recordEntity);
        when(recordMapper.toRecordResult(recordEntity)).thenReturn(recordResponse);

        var result = recordService.create(recordForm);

        verify(recordMapper).toRecord(recordForm);
        verify(recordRepository).save(recordEntity);
        verify(recordMapper).toRecordResult(recordEntity);
        assertEquals(recordResponse, result);
    }

    @Test
    void testDelete() {
        var id = 1;
        recordService.delete(id);
        verify(recordRepository).deleteById(id);
    }

    @Test
    void testGet() {
        var id = 1;
        var recordEntity = Record.builder().build();
        var recordResponse = new RecordResponse(id, "service", "customer", "location", LocalDateTime.now());

        when(recordRepository.findById(id)).thenReturn(Optional.of(recordEntity));
        when(recordMapper.toRecordResult(any(Record.class))).thenReturn(recordResponse);

        var result = recordService.get(id);

        assertNotNull(result);
        verify(recordRepository).findById(id);
        verify(recordMapper).toRecordResult(recordEntity);
    }

    @Test
    void testFindAll() {
        var pageable = mock(Pageable.class);
        var recordEntity = Record.builder().build();
        var recordResponse = new RecordResponse(1, "service", "customer", "location", LocalDateTime.now());
        var recordPage = new PageImpl<>(Collections.singletonList(recordEntity));

        when(recordRepository.findAll(pageable)).thenReturn(recordPage);
        when(recordMapper.toRecordResult(any(Record.class))).thenReturn(recordResponse);

        var result = recordService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(recordRepository).findAll(pageable);
        verify(recordMapper).toRecordResult(recordEntity);
    }

    @Test
    void testFindRecordByIdNotFound() {
        var id = 1;

        when(recordRepository.findById(id)).thenReturn(Optional.empty());

        var exception = assertThrows(ServiceException.class, () -> {
            recordService.get(id);
        });

        var expectedMessage = "Nenhum registro encontrado";
        assertEquals(expectedMessage, exception.getMessage());
        verify(recordRepository).findById(id);
    }

}
