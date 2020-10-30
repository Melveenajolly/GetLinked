package com.springboot.getlinked.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.getlinked.model.Professional;
import com.springboot.getlinked.repository.ProfessionalDao;

@Service
public class ProfessionalService implements UserDetailsService {

	@Autowired
	private ProfessionalDao professionalDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void registerProfessional(Professional professional) {
		professional.setRole("ROLE_USER");
		professionalDao.save(professional);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Professional professional = professionalDao.findByEmail(email);

		if (professional == null) {
			return null;
		}

		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(professional.getRole());

		String password = professional.getPassword();

		return new org.springframework.security.core.userdetails.User(email, password, auth);
	}
	//retriving professional list for vieAll
	public List<Professional> getByCategoryIdAndVerified(int id) {
		return professionalDao.findByCategoryidAndVerified(id, 1);
	}
	//retrieving professional by id for........
	public Professional getById(Long id) {
		return professionalDao.findById(id).get();
	}
	//
	public List<Professional> getByVerified(int verified)	{
		return professionalDao.findByVerified(verified);
	}
	//update professional as verified by admin
    public void setProfessinalVerified(int verified, Long professionalid) {
    	professionalDao.setProfessinalVerified(verified,professionalid);
    	
		
	}
    //
    public List<Professional> getAllProfessional()	{
		return professionalDao.findAll();
	}
    //
    public Professional getByEmail(String email)	{
    	return professionalDao.findByEmail(email);
    }
}
