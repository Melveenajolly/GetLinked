package com.springboot.getlinked.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.getlinked.model.HireInfo;
import com.springboot.getlinked.model.Professional;
import com.springboot.getlinked.model.Review;
import com.springboot.getlinked.model.User;
import com.springboot.getlinked.services.EmailService;
import com.springboot.getlinked.services.HireService;
import com.springboot.getlinked.services.ProfessionalService;
import com.springboot.getlinked.services.ReviewService;
import com.springboot.getlinked.services.UserService;
import com.sun.xml.bind.v2.util.QNameMap.Entry;

@Controller
public class SampleController {

	@Autowired
	ProfessionalService professionalService;

	@Autowired
	UserService userService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	HireService hireService;

	@Autowired
	private EmailService emailService;

//	@RequestMapping("/test")
//	String home() {
//		return "app.homepage";
//	}
	
	//home page get
	@RequestMapping("/")
	ModelAndView home(ModelAndView modelAndView) {
		String userType = "";
		modelAndView.setViewName("app.homepage");
		//user from browser
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		username = username.split(":")[0].toString();
		List<Professional> hiredProssionals = new ArrayList<Professional>();

		if (username != "anonymousUser") {
			userType = auth.getName().split(":")[1].toString();
			List<HireInfo> hires = hireService.findByUsername(username);

			for (HireInfo hire : hires) {
				hiredProssionals.add(professionalService.getById(hire.getProfessionalid()));
			}
			modelAndView.getModel().put("hiredProssionals", hiredProssionals);
			//this is not used
			modelAndView.getModel().put("userType", userType);
		}
		// when unverified professional try to access home page
		if (userType.equals("professional")) {
			if (professionalService.getByEmail(username).getVerified() == 0) {
				// professional user is logged in, and is not verified, access denied and send back to login and shows the error essage
				modelAndView.setViewName("app.login");
				//since unverified professional has authority of user, denying access to other pages
				auth.setAuthenticated(false);
				modelAndView.getModel().put("localError", "You can login only after your details are verified."
						+ " We will send you an email once your account is verified.");
				return modelAndView;
			}
		}
		return modelAndView;
	}

	@RequestMapping("/about")
	String about() {
		return "app.about";

	}

	@RequestMapping(value = "/viewAll", method = RequestMethod.GET)
	ModelAndView viewAll(ModelAndView modelAndView, @RequestParam(name = "categoryid") int categoryid) {

		try {
			List<Professional> professionalList = professionalService.getByCategoryIdAndVerified(categoryid);
			List<Integer> avgRatings = new ArrayList<Integer>();
			
			//average rating of each professional
			for (Professional professional : professionalList) {
				List<Review> reviews = new ArrayList<Review>();
				reviews = reviewService.getByProfessionalid(professional.getId());

				// find average rating=> sum of all of professional's ratings / number of
				// ratings
				float sumOfRatings = 0f;
				for (Review rev : reviews) {
					sumOfRatings += rev.getRating();
				}
				//average rating can be distinguishable since it has same order of the professional list 
				avgRatings.add(Math.round(sumOfRatings / reviews.size()));
			}

			modelAndView.getModel().put("avgRatings", avgRatings);
			modelAndView.getModel().put("professionalList", professionalList);
			//to not show km away
			modelAndView.getModel().put("isPost", 0);

			modelAndView.setViewName("app.viewAll");
		} catch (NumberFormatException e) {
			modelAndView.setViewName("app.error");
			modelAndView.getModel().put("message", "Invalid URL, please try again");
			return modelAndView;
		}
		return modelAndView;
	}

	@RequestMapping(value = "/viewAll", method = RequestMethod.POST)
	ModelAndView viewAll(ModelAndView modelAndView, @RequestParam(name = "categoryidFromForm") int categoryidFromForm,
			@RequestParam(name = "lat1") String lat1, @RequestParam(name = "lng1") String lng1, BindingResult result) {

		List<Professional> professionalList = professionalService.getByCategoryIdAndVerified(categoryidFromForm);

		if (lat1.equals("-99")) {
			// sort by rating
			List<Professional> professionalListSorted = new ArrayList<Professional>();
			HashMap<Float, List<Professional>> map = new HashMap<Float, List<Professional>>();
			HashMap<Float, List<Professional>> sortedMap;

			// Find the ratings
			List<Float> avgRatings = new ArrayList<Float>();
			List<Integer> avgRatingsSorted = new ArrayList<Integer>();
			
			float rating = 0f;
			for (Professional professional : professionalList) {
				List<Review> reviews = new ArrayList<Review>();
				reviews = reviewService.getByProfessionalid(professional.getId());

				// find average rating=> sum of all of professional's ratings / number of
				// ratings
				float sumOfRatings = 0f;
				for (Review rev : reviews) {
					sumOfRatings += rev.getRating();
				}
				//average rating of a professional adding to list
				avgRatings.add(sumOfRatings / reviews.size());
			}

			int index = 0;
			/*
			 * Loop for adding avg rating and professional object to hashmap
			 * professional list and avgRatings list are in the same order.
			 * Loop through the professional list
			 * Get the correspondign avg rating
			 * check if that avg rating is already added to hashmap (by checking its keys)
			 * if that avgrating is aklready added, then add the professional to its value, which is a list<professional>
			 * otherwise, add that avg rating as new key, and add that professional object (list<professional>) as its value
			 */
			for (Professional p : professionalList) {
				//checkig if the avg rating is already added to teh hashmap as key 
				if(map.containsKey(avgRatings.get(index)))	{
					List<Professional> newList = map.get(avgRatings.get(index));
					newList.add(p);
					map.put(avgRatings.get(index), newList);
				}	else	{
					List<Professional> newList = new ArrayList<Professional>();
					newList.add(p);
					map.put(avgRatings.get(index), newList);
				}
				
				index++;
			}
			
			sortedMap = sortByComparator(map);

			// insert into sorted list based on rating of professional
			for (Map.Entry<Float, List<Professional>> entry : sortedMap.entrySet()) {
				//going to put the avg rating into avgRatingsSorted list and professional to professionalListSorted list.
				//But, there is more than one profgessional for one single key (avgn rating). So, iterate the value (list<profes..>)
				//and insert each professional to thje sorted profeessionakl list
				List<Professional> l = entry.getValue();
				for(Professional p : l)	{
					professionalListSorted.add(p);
					avgRatingsSorted.add(Math.round(entry.getKey()));
				}
			}
		
			modelAndView.getModel().put("professionalList", professionalListSorted);
			modelAndView.getModel().put("avgRatings", avgRatingsSorted);
			modelAndView.getModel().put("isPost", 0);

			modelAndView.setViewName("app.viewAll");
			return modelAndView;

		} else {
			// sort by distance
			HashMap<Float, List<Professional>> map = new HashMap<Float, List<Professional>>();
			HashMap<Float, List<Professional>> sortedMap;

			List<Professional> professionalListSorted = new ArrayList<Professional>();
			
			List<String> distanceSorted = new ArrayList<String>();

			// compare lat and lng of user with each of the professional's lat and lng and
			// make the list accordingly and send.
			//
			for (Professional p : professionalList) {
				// find distance between p's lat and lng and user's lat and lng
				float dist = distance(Double.parseDouble(lat1), Double.parseDouble(p.getLat()), Double.parseDouble(lng1),
						Double.parseDouble(p.getLng()), 0.0, 0.0);
				if(map.containsKey(dist))	{
					List<Professional> newList = map.get(dist);
					newList.add(p);
					map.put(dist, newList);
				}	else	{
					List<Professional> newList = new ArrayList<Professional>();
					newList.add(p);
					map.put(dist, newList);
				}
			}

			sortedMap = sortByComparator(map);

			// insert into sorted list based on distance from user
			for (Map.Entry<Float, List<Professional>> entry : sortedMap.entrySet()) {
				List<Professional> list = entry.getValue();
				for(Professional p : list) {
					professionalListSorted.add(p);
					distanceSorted.add((Math.round((entry.getKey()/1000) * 100.0) / 100.0) + "");
				}				
			}

			// make new sorted ratings-list based on the new distance-sorted professional list
			//professionalListSorted is the list of professionals sorted in distance. Make the coirrespondinhg avgRatings
			//list based on this already sorted list.
			List<Integer> avgRatings = new ArrayList<Integer>();
			for (Professional professional : professionalListSorted) {
				List<Review> reviews = new ArrayList<Review>();
				reviews = reviewService.getByProfessionalid(professional.getId());

				// find average rating=> sum of all of professional's ratings / number of
				// ratings
				float sumOfRatings = 0f;
				for (Review rev : reviews) {
					sumOfRatings += rev.getRating();
				}
				avgRatings.add(Math.round(sumOfRatings / reviews.size()));
			}


			// return the sorted list
			modelAndView.getModel().put("avgRatings", avgRatings);
			modelAndView.getModel().put("professionalList", professionalListSorted);
			modelAndView.getModel().put("distanceList", distanceSorted);
			modelAndView.getModel().put("isPost", 1);
			modelAndView.setViewName("app.viewAll");
			return modelAndView;
		}

	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	ModelAndView view(ModelAndView modelAndView, @RequestParam(name = "professionalid") Long professionalid,
			@ModelAttribute(value = "hire") HireInfo hireinfo) {
		Professional professionalProfile = professionalService.getById(professionalid);

		List<Review> reviews = new ArrayList<Review>();
		reviews = reviewService.getByProfessionalid(professionalid);

		// find average rating=> sum of all of professional's ratings / number of
		// ratings
		float sumOfRatings = 0f;
		for (Review rev : reviews) {
			sumOfRatings += rev.getRating();
		}
		float avgrating = sumOfRatings / reviews.size();

		modelAndView.getModel().put("professionalProfile", professionalProfile);

		// pass professionalid to the view
		modelAndView.getModel().put("professionalid", professionalid);

		// pass current logged-in user id to view
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		username = username.split(":")[0];

		modelAndView.getModel().put("avgrating", avgrating);
		modelAndView.getModel().put("reviews", reviews);
		modelAndView.getModel().put("username", username);
		modelAndView.getModel().put("review", new Review());
		modelAndView.setViewName("app.view");
		return modelAndView;
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST)
	ModelAndView view(ModelAndView modelAndView, @ModelAttribute(value = "review") @Valid Review review,
			@RequestParam(name = "professionalid") Long professionalid, BindingResult result) {
		reviewService.saveReview(review);
		modelAndView.setViewName("redirect:/view?professionalid=" + professionalid);
		return modelAndView;
	}

	public static float distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		double height = el1 - el2;

		distance = Math.pow(distance, 2) + Math.pow(height, 2);

		return (float) Math.sqrt(distance);
	}

	// method to sort hashmap
	public static HashMap<Float, List<Professional>> sortByComparator(HashMap<Float, List<Professional>> unsortMap) {

		List<Map.Entry<Float, List<Professional>>> list = new LinkedList<Map.Entry<Float, List<Professional>>>(
				unsortMap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<Float, List<Professional>>>() {
			@Override
			public int compare(java.util.Map.Entry<Float, List<Professional>> o1,
					java.util.Map.Entry<Float, List<Professional>> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});

		HashMap<Float, List<Professional>> sortedMap = new LinkedHashMap<Float, List<Professional>>();
		for (java.util.Map.Entry<Float, List<Professional>> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	@RequestMapping(value = "/hirepage", method = RequestMethod.GET)
	ModelAndView hirepage(ModelAndView modelAndView, @RequestParam(name = "professionalid") Long professionalid,
			@ModelAttribute(value = "hireinfo") HireInfo hireinfo) {
		Professional professionalProfile = professionalService.getById(professionalid);
		modelAndView.getModel().put("professionalProfile", professionalProfile);

		// pass professionalid to the view
		modelAndView.getModel().put("professionalid", professionalid);

		// pass current logged in user id to view
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		username = username.split(":")[0];

		modelAndView.getModel().put("username", username);

		User userprofile = userService.getByEmail(username);
		modelAndView.getModel().put("userprofile", userprofile);
		modelAndView.setViewName("app.hirepage");
		return modelAndView;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/hirepage", method = RequestMethod.POST)
	ModelAndView hirepage(ModelAndView modelAndView, @ModelAttribute(value = "hireinfo") @Valid HireInfo hireinfo,
			BindingResult result) {
		if (!result.hasErrors()) {
//			if(Date.parse(hireinfo.getCardexpiry()) < new Date().getDate())	{
//				String yes = "no";
//			}
			//date of hired
			hireinfo.setDate(new Date());
			//saving hire
			hireService.saveHireInfo(hireinfo);
			//sending email
			Professional pr = professionalService.getById(hireinfo.getProfessionalid());
			emailService.sendConfirmationEmail(pr.getEmail(), hireinfo.getUsername(), hireinfo.getHireaddress(),
					hireinfo.getHireDate(), hireinfo.getUserContact());

			modelAndView.getModel().put("successMessage",
					"Payment received, thank you for using our service. Go back or close this window.");
			modelAndView.setViewName("redirect:/view?professionalid=" + hireinfo.getProfessionalid());
		}
		return modelAndView;
	}

	// myhires
	@RequestMapping(value = "/myhires", method = RequestMethod.GET)
	ModelAndView myhires(ModelAndView modelAndView, BindingResult result) {
		List<HireInfo> hireinfos = new ArrayList<HireInfo>();
		List<Professional> professionalNames = new ArrayList<Professional>();

		// find user email
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		username = username.split(":")[0];

		// Find user object from user email
		User userprofile = userService.getByEmail(username);
		modelAndView.getModel().put("userprofile", userprofile);

		// find all hires by this user
		hireinfos = hireService.findByUsername(username);

		for (HireInfo item : hireinfos) {
			professionalNames.add(professionalService.getById(item.getProfessionalid()));
		}

		modelAndView.getModel().put("professionalNames", professionalNames);
		modelAndView.getModel().put("hireinfos", hireinfos);
		modelAndView.setViewName("app.myhires");
		return modelAndView;
	}

	// mywork
	@RequestMapping(value = "/mywork", method = RequestMethod.GET)
	ModelAndView mywork(ModelAndView modelAndView, BindingResult result,
			@ModelAttribute(value = "hireinfo") @Valid HireInfo hireinfo) {
		List<HireInfo> workinfos = new ArrayList<HireInfo>();
		List<User> userNames = new ArrayList<User>();

		// find user email
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		username = username.split(":")[0];

//		// Find user object from user email
//		User professionalProfile = userService.getByEmail(username);
//		modelAndView.getModel().put("professionalProfile", professionalProfile);

		// get professional from professional's username (email)
		Professional p = professionalService.getByEmail(username);

		// find all works for this professional
		workinfos = hireService.findByprofessionalid(p.getId());

		for (HireInfo item : workinfos) {
			userNames.add(userService.getByEmail(item.getUsername()));
		}

		modelAndView.getModel().put("userNames", userNames);
		modelAndView.getModel().put("workinfos", workinfos);
		modelAndView.setViewName("app.mywork");
		return modelAndView;
	}

	@RequestMapping(value = "/setCompletedHire", method = RequestMethod.POST)
	ModelAndView setCompletedHire(ModelAndView modelAndView, @ModelAttribute(value = "hireinfo") HireInfo hireinfo,
			@RequestParam(name = "completed", required = false, defaultValue = "notchecked") String completed) {

		hireService.setHireinfoisCompleted(1, hireinfo.getId());
		if (completed.equals("notchecked")) {
			hireService.setHireinfoisCompleted(0, hireinfo.getId());
		}
		if (completed.equals("checked") && !completed.isEmpty()) {
			hireService.setHireinfoisCompleted(1, hireinfo.getId());
		}

		modelAndView.setViewName("redirect:/mywork");
		return modelAndView;
	}
}
