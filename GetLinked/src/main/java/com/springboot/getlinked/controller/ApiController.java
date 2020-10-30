package com.springboot.getlinked.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.mail.Multipart;
import javax.mail.internet.ContentType;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.getlinked.exceptions.ImageTooSmallException;
import com.springboot.getlinked.exceptions.InvalidFileException;
import com.springboot.getlinked.model.HireInfo;
import com.springboot.getlinked.model.Professional;
import com.springboot.getlinked.model.Review;
import com.springboot.getlinked.model.User;
import com.springboot.getlinked.services.FileService;
import com.springboot.getlinked.services.HireService;
import com.springboot.getlinked.services.ProfessionalService;
import com.springboot.getlinked.services.ReviewService;
import com.springboot.getlinked.services.UserService;

@RestController
public class ApiController {

	@Autowired
	private ProfessionalService professionalService;

	@Autowired
	private UserService userService;

	@Autowired
	private HireService hireService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private ReviewService reviewService;
	

	@Value("${photo.upload.directory}")
	private String photoUploadDirectory;
	
	//request for 5 nearby professionals
	@GetMapping("/professionals/{categoryid}")
	public List<Professional> getProfessionals(@PathVariable("categoryid") int categoryid,
			@RequestParam("lat1") String lat1, @RequestParam("lng1") String lng1) {

		HashMap<Double, Professional> map = new HashMap<Double, Professional>();
		HashMap<Double, Professional> sortedMap;
		List<Professional> professionalListSorted = new ArrayList<Professional>();
		List<String> distanceSorted = new ArrayList<String>();

//		// retrieving all the professional of the category id
		List<Professional> professionalList = professionalService.getByCategoryIdAndVerified(categoryid);
//
//		// compare lat and lng of user with each of the professional's lat and lng and

		for (Professional p : professionalList) {
//			// find distance between p's lat and lng and user's lat and lng
			map.put(distance(Double.parseDouble(lat1), Double.parseDouble(p.getLat()), Double.parseDouble(lng1),
					Double.parseDouble(p.getLng()), 0.0, 0.0), p);
		}

//		// sorting the map in the ascending order of distance
		sortedMap = sortByComparator(map);

		// insert 5 professional into sorted list based on distance from user
		int count = 0;
		for (Map.Entry<Double, Professional> entry : sortedMap.entrySet()) {
			professionalListSorted.add(entry.getValue());
			distanceSorted.add(entry.getKey().toString());
			count++;
			if (count == 5) {
				break;
			}
		}

		return professionalListSorted;

	}

	//login request
	@GetMapping("/login2")
	public int validateLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		UserDetails user = userService.loadUserByUsername(username);
		if (user != null) {
			if (passwordEncoder.matches(password, user.getPassword())) {
				return 1;
			}
		}
		return 0;
	}
	
	//hireinfo saving
	@PostMapping(path = "/hireInfo")
	public int hireApi(HireInfo hireInfo) {
		hireInfo.setUsername(hireInfo.getUsername().split(":")[0]);
		hireService.saveHireInfo(hireInfo);
		return 1;
	}

	@PostMapping(path = "/signup2")
	public int signupUser(User user) {
//		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userService.register(user);
		return 1;
	}

	//register request
	@PostMapping(path = "/professional")
	public int register1(Professional professional) {

		professional.setPassword(new BCryptPasswordEncoder().encode(professional.getPassword()));
		//passing as fields from android
		professional.setPhotoExtension(professional.getPhotoExtension());
		professional.setCertExtension(professional.getCertExtension());

		professionalService.registerProfessional(professional);
		return 1;
	}

	
	//image uploading
	@PostMapping(path = "/postImage2", consumes = { "text/plain", "application/json" })
	public int register2(@RequestBody String images, @RequestParam String professionalEmail) {
		try {
			//input- json string ciontaining base64 encoded images, professional email
			//convert json string to obj-parse obj-decode the base64 encoded images-save to file
			JSONObject mainObject = new JSONObject(images);
			JSONObject secondObj = (JSONObject) mainObject.get("nameValuePairs");
			String imageEncodedString = secondObj.getString("image1");
			String certEncodedString = secondObj.getString("image2");

			byte[] decodedImg1 = Base64.getMimeDecoder().decode(imageEncodedString.getBytes(StandardCharsets.US_ASCII));
			byte[] decodedImg2 = Base64.getMimeDecoder().decode(certEncodedString.getBytes(StandardCharsets.US_ASCII));

			MultipartFile destinationFile1 = new MockMultipartFile("file", "name.jpg", "text/plain", decodedImg1);
			MultipartFile destinationFile2 = new MockMultipartFile("file", "name.jpg", "text/plain", decodedImg2);

			//save profile picture
			fileService.saveImageFile(destinationFile1, photoUploadDirectory, "photos", "profile", professionalEmail, 1,
					161, 160);
			
			//save certificate file
			fileService.saveImageFile(destinationFile2, photoUploadDirectory, "photos", "profile", professionalEmail, 0,
					161, 160);

		} catch (InvalidFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ImageTooSmallException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@GetMapping(path = "/hireinfo/{professionalEmail}")
	public List<HireInfo> getmyworks(@PathVariable("professionalEmail") String professionalEmail) {
		// find professionalid from this professional email
		Long professionalid = professionalService.getByEmail(professionalEmail).getId();

		// find all works for this professional
		List<HireInfo> workinfos = hireService.findByprofessionalid(professionalid);
		return workinfos;
	}
	
	// API to set work done from android in hire info table
	@PostMapping(path = "/setworkdone")
	public int setWorkCompleted(@RequestParam("hireinfoId") Long hireinfoId) {
		hireService.setHireinfoisCompleted(1, hireinfoId);
		return 1;
	}
	
	@GetMapping(path = "/getmyhires")
	public List<Professional> getmyhires(@RequestParam("username") String username) {
				// find all hires by this user
		List<HireInfo> hireinfos = hireService.findByUsername(username);
		List<Professional> professionals = new ArrayList<Professional>();
		Professional pr = null;
		for (HireInfo item : hireinfos) {
			//get professional 
			pr = professionalService.getById(item.getProfessionalid());
//			String temp =pr.getHousenumber() + ", " + pr.getStreetname() + ", " + pr.getCity();
//			System.out.println("Citt is set as: " + temp);
//			pr.setCity(temp);	//update professional's 
//			//city to be housenumber+streetname+city
			pr.setStreetname(item.getHireaddress());	//set hire address as professional's street address
			System.out.println("Citt is set as: " + item.getHireaddress());
			pr.setPhotoName(item.getHireDate());
			
			//creaate a NEW professional object and set all its properties as our current professional in current iteration
			Professional newp = new Professional();
			newp.setCategoryid(pr.getCategoryid());
			newp.setCategoryname(pr.getCategoryname());
			newp.setCertExtension(pr.getCertExtension());
			newp.setCity(pr.getCity());
			newp.setCounty(pr.getCounty());
			newp.setEmail(pr.getEmail());
			newp.setPhotoName(pr.getPhotoName());
			newp.setStreetname(pr.getStreetname());
			newp.setFirstname(pr.getFirstname());
			newp.setLastname(pr.getLastname());
			newp.setPhonenumber(pr.getPhonenumber());
			
			//add this new professional to the list. Cant add pr to this list because any change/mopdification done on this
			//obnject will occur to every object already inserted, which are contained in the list.
			professionals.add(newp);
			pr=null;
		}
		return professionals;
	}
	
	

	@GetMapping(path = "/getreview")
	public List<Review> GetReviews(@RequestParam("professionalEmail") String professionalEmail) {
		// find professionalid from this professional email
		Long professionalid = professionalService.getByEmail(professionalEmail).getId();

		// find all works for this professional
		List<Review> professioanlReviews = reviewService.getByProfessionalid(professionalid);
		return professioanlReviews;
	}
	
	@PostMapping(path = "/setreview")
	public Integer SetReviews(
								@RequestParam("professionalEmail") String professionalEmail,
								@RequestParam("rating") float rating,
								@RequestParam("comment") String comment,
								@RequestParam("username") String username) {
		// find professionalid from this professional email
		Long professionalid = professionalService.getByEmail(professionalEmail).getId();

		Review myreview = new Review();
		myreview.setComment(comment);
		myreview.setProfessionalid(professionalid);
		myreview.setRating((int)rating);
		myreview.setUsername(username);
		reviewService.saveReview(myreview);
		return 1;
	}
	
	@GetMapping(path = "/getimage")
	public String getImage1(@RequestParam("professionalEmail") String professionalEmail) {
		String encodedString = null, jsonString = "";
		// find the image with name professionalEmail
		BufferedImage bImage = null;
		try {
			
			//the image is fetched from disk corresponding to the email
			//cnvert to buffered image (byte[])
			//
			bImage = ImageIO.read(new File(
					"C:\\Users\\Melveena\\Documents\\MelveenaJolly_Project\\GetLinked\\src\\main\\webapp\\ProfessionalImages\\"
							+ professionalEmail + ".jpg"));
			if(bImage != null)	{
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bImage, "jpg", bos);
				encodedString = Base64.getEncoder().encodeToString(bos.toByteArray());
			}

			if (bImage == null) {
				bImage = ImageIO.read(new File(
						"C:\\Users\\Melveena\\Documents\\MelveenaJolly_Project\\GetLinked\\src\\main\\webapp\\ProfessionalImages\\"
								+ professionalEmail + ".png"));
				if(bImage != null)	{
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ImageIO.write(bImage, "png", bos);
					encodedString = Base64.getEncoder().encodeToString(bos.toByteArray());
				}
			}

			if (bImage == null) {
				bImage = ImageIO.read(new File(
						"C:\\Users\\Melveena\\Documents\\MelveenaJolly_Project\\GetLinked\\src\\main\\webapp\\ProfessionalImages\\"
								+ professionalEmail + "jpeg"));
				if(bImage != null)	{
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					ImageIO.write(bImage, "jpeg", bos);
					encodedString = Base64.getEncoder().encodeToString(bos.toByteArray());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			jsonString = new JSONObject().put("value", encodedString).toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonString;
	}

	// method to calculate the distance between two locations
	public static double distance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		double height = el1 - el2;

		distance = Math.pow(distance, 2) + Math.pow(height, 2);

		return Math.sqrt(distance);
	}

	// method to sort
	public static HashMap<Double, Professional> sortByComparator(HashMap<Double, Professional> unsortMap) {

		List<Map.Entry<Double, Professional>> list = new LinkedList<Map.Entry<Double, Professional>>(
				unsortMap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<Double, Professional>>() {
			@Override
			public int compare(java.util.Map.Entry<Double, Professional> o1,
					java.util.Map.Entry<Double, Professional> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});

		HashMap<Double, Professional> sortedMap = new LinkedHashMap<Double, Professional>();
		for (java.util.Map.Entry<Double, Professional> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

}
