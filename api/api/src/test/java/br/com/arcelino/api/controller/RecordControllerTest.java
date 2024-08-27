package br.com.arcelino.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import br.com.arcelino.api.dto.RecordForm;
import br.com.arcelino.api.dto.RecordResponse;
import br.com.arcelino.api.service.RecordService;

class RecordControllerTest {

    @Mock
    private RecordService recordService;

    @InjectMocks
    private RecordController recordController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRecord() {
        var currentTime = LocalDateTime.now();
        var recordForm = new RecordForm("service", "customer", "location", currentTime);
        var expectedResponse = new RecordResponse(1, "service", "customer", "location", currentTime);
        when(recordService.create(recordForm)).thenReturn(expectedResponse);

        var response = recordController.create(recordForm);

        verify(recordService, times(1)).create(recordForm);
        assertSame(expectedResponse, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testEditRecord() {
        var id = 1;
        var currentTime = LocalDateTime.now();
        var recordForm = new RecordForm("service", "customer", "location", currentTime);
        var expectedResponse = new RecordResponse(id, "service", "customer", "location", currentTime);

        when(recordService.edit(id, recordForm)).thenReturn(expectedResponse);
        var response = recordController.edit(id, recordForm);

        verify(recordService, times(1)).edit(id, recordForm);
        assertSame(expectedResponse, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteRecord() {
        var id = 1;
        var response = recordController.delete(id);
        verify(recordService, times(1)).delete(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetRecord() {
        var id = 1;
        var expectedResponse = new RecordResponse(id, "service", "customer", "location",
                LocalDateTime.now());
        when(recordService.get(id)).thenReturn(expectedResponse);

        var response = recordController.get(id);

        verify(recordService, times(1)).get(id);
        assertSame(expectedResponse, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testFindAllRecords() {
        var pageable = mock(Pageable.class);
        var expectedResponse = mock(Page.class);
        when(recordService.findAll(pageable)).thenReturn(expectedResponse);

        var response = recordController.findAll(pageable);

        verify(recordService, times(1)).findAll(pageable);
        assertSame(expectedResponse, response);
    }

}
