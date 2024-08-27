package br.com.arcelino.api.service;

import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.arcelino.api.dto.RecordForm;
import br.com.arcelino.api.dto.RecordResponse;
import br.com.arcelino.api.mapper.RecordMapper;
import br.com.arcelino.api.repository.RecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;

    /**
     * Cria um novo registro
     * 
     * @param recordFormDto
     * @return
     */
    @Transactional
    public RecordResponse create(RecordForm recordFormDto) {
        var recordEntity = recordMapper.toRecord(recordFormDto);
        return saveAndMapToResponse(recordEntity);
    }

    /**
     * Edita um registro pelo id
     * 
     * @param id
     * @param recordFormDto
     * @return
     */
    @Transactional
    public RecordResponse edit(Integer id, RecordForm recordFormDto) {
        var existingRecord = findRecordById(id);
        var updatedRecord = recordMapper.toRecord(recordFormDto);
        updatedRecord.setId(existingRecord.getId());
        return saveAndMapToResponse(updatedRecord);
    }

    /**
     * Busca um registro pelo id
     * 
     * @param id
     * @return
     */
    public RecordResponse get(Integer id) {
        var recordEntity = findRecordById(id);
        return recordMapper.toRecordResult(recordEntity);
    }

    /**
     * Deleta um registro pelo id
     * 
     * @param id
     */
    public void delete(Integer id) {
        recordRepository.deleteById(id);
    }

    /**
     * Busca todos os registros
     * 
     * @param pageable
     * @return
     */
    public Page<RecordResponse> findAll(Pageable pageable) {
        return recordRepository.findAll(pageable).map(recordMapper::toRecordResult);
    }

    /**
     * Salva um registro e mapeia para o RecordResponse
     * 
     * @param recordEntity
     * @return
     */
    private RecordResponse saveAndMapToResponse(br.com.arcelino.api.entity.Record recordEntity) {
        var savedRecord = recordRepository.save(recordEntity);
        return recordMapper.toRecordResult(savedRecord);
    }

    /**
     * Busca um registro pelo id
     * 
     * @param id
     * @return
     */
    private br.com.arcelino.api.entity.Record findRecordById(Integer id) {
        return recordRepository.findById(id).orElseThrow(() -> new ServiceException("Nenhum registro encontrado"));
    }
}
