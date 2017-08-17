package by.kalilaska.ktattoo.service;

import java.util.List;

import by.kalilaska.ktattoo.bean.SeanceBean;

public interface SeanceService {
	List<SeanceBean> findAllSeancesByClientId(int id);
	List<SeanceBean> findAllSeancesByMasterId(int id);
}
