package br.com.arcelino.api.dto;

import java.time.LocalDateTime;

public record RecordResponse(
        Integer id,
        String service,
        String customer,
        String location,
        LocalDateTime schedulingDate) {

}
