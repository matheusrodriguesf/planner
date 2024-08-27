package br.com.arcelino.api.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record RecordForm(
                String service,
                String customer,
                String location,
                LocalDateTime schedulingDate) {
}
