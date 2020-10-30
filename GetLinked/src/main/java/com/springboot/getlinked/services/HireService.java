package com.springboot.getlinked.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.getlinked.model.HireInfo;
import com.springboot.getlinked.repository.HireDao;

@Service
public class HireService {

	@Autowired
	private HireDao hireDao;
	//saving hireinfo
	public void saveHireInfo(HireInfo hireInfo) {
		hireDao.save(hireInfo);
	}
	//hire info of a specific user
	public List<HireInfo> findByUsername(String username) {
		return hireDao.findByUsername(username);
	}
	//hire info about a professional
	public List<HireInfo> findByprofessionalid(Long professionalid) {
		return hireDao.findByProfessionalid(professionalid);
	}
	//updating the work as completed in myworks
	public void setHireinfoisCompleted(int isCompleted,Long id) {
		hireDao.setHireinfoisCompleted(isCompleted,id);
		
	}
}
