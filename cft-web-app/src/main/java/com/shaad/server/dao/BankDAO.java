package com.shaad.server.dao;



import com.shaad.server.entities.Bank;

import java.util.List;

public interface BankDAO {
	Bank getByBank_ID(int Bank_ID);

	Bank getByBank_Name(String name);

	List getAllBank();

	List<Bank> getBanksByCity(String city);

	int save(Bank Bank);

	void update(Bank Bank);

	void view(Bank Bank);

	void delete(int Bank_ID);
}
