package br.com.arcelino.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arcelino.api.dto.RecordForm;
import br.com.arcelino.api.dto.RecordResponse;
import br.com.arcelino.api.service.RecordService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<RecordResponse> create(@RequestBody RecordForm recordForm) {
        return ResponseEntity.ok(recordService.create(recordForm));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecordResponse> edit(@PathVariable Integer id, @RequestBody RecordForm recordForm) {
        return ResponseEntity.ok(recordService.edit(id, recordForm));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        recordService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordResponse> get(@PathVariable Integer id) {
        return ResponseEntity.ok(recordService.get(id));
    }

    @GetMapping
    public Page<RecordResponse> findAll(Pageable pageable) {
        return recordService.findAll(pageable);
    }

}
