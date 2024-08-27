package br.com.arcelino.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.arcelino.api.entity.Record;

public interface RecordRepository extends JpaRepository<Record, Integer> {

}
