package com.apap.tutorial7.service;

import java.util.Optional;

import com.apap.tutorial7.model.DealerModel;

import java.util.List;

public interface DealerService {
	Optional<DealerModel> getDealerDetailById(long id);
	
	DealerModel addDealer(DealerModel dealer);
	void deleteDealer(DealerModel dealer);
	List<DealerModel> allDealer();

}
