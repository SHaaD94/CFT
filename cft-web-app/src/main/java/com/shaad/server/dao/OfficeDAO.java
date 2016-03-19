package com.shaad.server.dao;

import com.shaad.server.entities.Office;

import java.util.List;

public interface OfficeDAO {
	Office getByOffice_ID(int Office_ID);
	
	List<Office> getAllOffice();
	
	int save(Office Office);
	
	void update(Office Office);
	
	void view(Office Office);
	
	void delete(int Office_ID);
}
