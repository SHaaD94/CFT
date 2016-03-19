package com.shaad.models;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface BankDao extends CrudRepository<Bank, Long> {

}
