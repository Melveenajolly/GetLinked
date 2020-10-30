package com.springboot.getlinked.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.getlinked.model.HireInfo;
import com.springboot.getlinked.model.User;

public interface HireDao extends JpaRepository<HireInfo, Long>{
	
	//hire info by username
	 List<HireInfo> findByUsername(String username);
	//hire info by professional id	
	 List<HireInfo> findByProfessionalid(Long professionalid);
	
	 //update professional
	@Modifying
    @Transactional
	@Query("Update HireInfo hireinfo set hireinfo.isCompleted=?1 where hireinfo.id=?2")
	void setHireinfoisCompleted( int isCompleted,Long id );

}
