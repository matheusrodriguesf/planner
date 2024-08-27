package br.com.arcelino.api.service;

import org.springframework.stereotype.Service;

import br.com.arcelino.api.repository.RecordRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

}
