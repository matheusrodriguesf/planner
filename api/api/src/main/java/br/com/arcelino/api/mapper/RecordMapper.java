package br.com.arcelino.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.arcelino.api.dto.RecordForm;
import br.com.arcelino.api.dto.RecordResponse;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    @Mapping(target = "id", ignore = true)
    br.com.arcelino.api.entity.Record toRecord(RecordForm recordForm);

    RecordResponse toRecordResult(br.com.arcelino.api.entity.Record recordEntity);
}
