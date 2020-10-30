package com.springboot.getlinked.controller;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.getlinked.exceptions.ImageTooSmallException;
import com.springboot.getlinked.exceptions.InvalidFileException;
import com.springboot.getlinked.model.Professional;
import com.springboot.getlinked.model.User;
import com.springboot.getlinked.services.EmailService;
import com.springboot.getlinked.services.FileService;
import com.springboot.getlinked.services.HireService;
import com.springboot.getlinked.services.ProfessionalService;
import com.springboot.getlinked.services.UserService;

@Controller
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	ProfessionalService professionalService;

	@Autowired
	HireService hireService;

	@Autowired
	private EmailService emailService;

	@Value("${photo.upload.directory}")
	private String photoUploadDirectory;

	@Autowired
	FileService fileService;

	@RequestMapping("/login")
	String admin() {
		return "app.login";
	}

	@RequestMapping("/verifyemail")
	String verifyEmail() {
		return "app.verifyemail";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	ModelAndView register(ModelAndView modelAndView) {

		Professional professional = new Professional();
		modelAndView.getModel().put("professional", professional);
		modelAndView.setViewName("app.register");
		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	ModelAndView register(ModelAndView modelAndView,
			@ModelAttribute(value = "professional")  @Valid Professional professional,
			@RequestParam("file") MultipartFile file, @RequestParam("file2") MultipartFile file2,
			BindingResult result)  {
		modelAndView.setViewName("app.register");

		try {
			
			if(file != null	) {
				modelAndView.getModel().put("filesubmittedmessage1", "Sucessfully uploaded");
			}else {
//				modelAndView.getModel().put("filesubmittedmessage1", "n");
				throw new NullPointerException();
			}
			
			if(file2 != null	) {
				modelAndView.getModel().put("filesubmittedmessage2", "Sucessfully uploaded"); //this not used
			}	else	{
//				modelAndView.getModel().put("filesubmittedmessage2", "n");
				throw new NullPointerException();
			}
				
			// profile image extension check
			String fileextension = fileService.getFileExtension(file.getOriginalFilename());
			boolean extensionValid = fileService.isImageExtension(fileextension);

			// cert image extension check
			String fileextension2 = fileService.getFileExtension(file2.getOriginalFilename());
			boolean extensionValid2 = fileService.isImageExtension(fileextension2);

			if(fileextension.equals("jpg") || fileextension.equals("jpeg") || fileextension.equals("png"))	{
			}	else	{
				throw new NullPointerException();
			}
			
			if(fileextension2.equals("jpg") || fileextension2.equals("jpeg") || fileextension2.equals("png"))	{
			}	else	{
				throw new NullPointerException();
			}
			
			if (extensionValid && extensionValid2) {
				fileService.saveImageFile(file, photoUploadDirectory, "photos", "profile", professional.getEmail(), 1,
						160, 160);
				fileService.saveImageFile(file2, photoUploadDirectory, "photos", "profile", professional.getEmail(), 0,
						200, 200);
				professional.setPhotoExtension(fileextension);
				professional.setCertExtension(fileextension2);
			} else {
				throw new InvalidFileException("");
			}

		} catch (InvalidFileException e) {
			e.printStackTrace();
			return modelAndView;
		} catch (IOException e) {
		} catch (ImageTooSmallException e) {	//handled in client side validation
			e.printStackTrace();
		} catch(NullPointerException e4) {
			modelAndView.getModel().put("errormessage", "Please select valid images for your profile pic and certificate");
			return modelAndView;
		}

		try {
			if (!result.hasErrors()) {

				professionalService.registerProfessional(professional);
				// send email to this user emailService.sendVerificationEmail(user.getEmail());
				modelAndView.setViewName("redirect:/login");
			}
		} catch (Exception e) {
			modelAndView.getModel().put("errormessage", "User email already registered");
			return modelAndView;
		}
		
		modelAndView.getModel().put("errormessage", "");
		return modelAndView;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	ModelAndView signup(ModelAndView modelAndView) {

		User user = new User();
		modelAndView.getModel().put("user", user);
		modelAndView.setViewName("app.signup");
		return modelAndView;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	ModelAndView signup(ModelAndView modelAndView, @ModelAttribute(value = "user") @Valid User user,
			BindingResult result) throws ServletException {

		try {
		
			modelAndView.setViewName("app.signup");
			
			if(user.getAddress().trim().isEmpty())	{
				modelAndView.getModel().put("errormessage", "Please enter your address");
				return modelAndView;
			}
			
			if (!result.hasErrors()) {
				user.setAddress(user.getAddress().trim());
				userService.register(user);
				// send email to this user emailService.sendVerificationEmail(user.getEmail());
				modelAndView.setViewName("redirect:/login");
			}
		} catch (Exception e) {
			modelAndView.getModel().put("errormessage", "User email already registered");
			return modelAndView;
		}
		
		return modelAndView;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	ModelAndView adminPage(ModelAndView modelAndView,
			@ModelAttribute(value = "professional") @Valid Professional professional, BindingResult result) {

		List<Professional> unverifiedProfessionals = professionalService.getByVerified(0);
		modelAndView.getModel().put("unverifiedProfessionals", unverifiedProfessionals);
		modelAndView.setViewName("app.admin");
		return modelAndView;
	}

	@RequestMapping(value = "/admin", method = RequestMethod.POST)
	ModelAndView adminPage(ModelAndView modelAndView, BindingResult result,
			@RequestParam(name = "professionalid") Long professionalid) {

		modelAndView.setViewName("app.admin");

		if (!result.hasErrors()) {

			professionalService.setProfessinalVerified(1, professionalid);
			
			Professional myProfessional = professionalService.getById(professionalid);
			//send email to professional telling he is verified. he/she can now login to GetLinked.
			emailService.sendYouareVerifiedEmail(myProfessional.getEmail());
			
			modelAndView.getModel().put("professional", new Professional());

			modelAndView.setViewName("redirect:/admin");
		}
		List<Professional> unverifiedProfessionals = professionalService.getByVerified(0);
		modelAndView.getModel().put("unverifiedProfessionals", unverifiedProfessionals);
		return modelAndView;
	}

}
