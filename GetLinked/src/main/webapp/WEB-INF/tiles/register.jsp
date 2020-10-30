<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url var="loginUrl" value="/login" />

<div class="container-fluid d-flex flex-column "
	style="padding-top: 0%; width: 100%; height: auto; min-height: 100%; margin: 0; padding-bottom: 0%; background: url('/img/loginBackground.jpg'); background-position: center center; background-size: cover; padding-left: 0%; padding-right: 0;">
	<div class=" container-fluid  d-flex flex-column"
		style="background-color: rgb(0, 0, 0, .55); min-height: 100%; top: 0; min-width: 100%; height: auto; width: 100%; padding-right: 0; margin: 0;">
		<div class="container-fluid"
			style="padding-left: 10%; padding-top: 10%; width: 100%; padding-right: 10%">
			<div class="jumbotron" style="background-color: #FFFFFF;">
				<div class="container-fluid"
					style="margin: 0; padding-top: 0; padding-left: 0;">
					<form:form method="post" modelAttribute="professional"
						enctype="multipart/form-data" action="/register" id="registerForm">

						<c:if test="${ errormessage != null}">
							<b><div style="color: red;">${errormessage }</div></b>
						</c:if>
						<b><div id="localError" style="color: red;"></div></b>
						<div class="row">
							<div class="col">
								<div class="form-group">
									<label for="exampleInputEmail1"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">First
										name</label>
									<form:input type="text" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" id="firstnameid" aria-describedby=""
										path="firstname" placeholder="First name" />
								</div>
							</div>
							<div class="col">
								<div class="form-group">
									<label for="exampleInputEmail1"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">Last
										name</label>
									<form:input type="text" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" id="lastnameid" aria-describedby=""
										path="lastname" placeholder="Last name" />
								</div>
							</div>
							<div class="col">
								<div class="form-group">
									<label for="exampleInputEmail1"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">Phone
										number</label>
									<form:input type="number" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" id="phonenumberid" aria-describedby=""
										path="phonenumber" placeholder="Phone number" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<div class="form-group">
									<label for="exampleInputEmail1"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">Email
										address</label>
									<form:input type="email" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" id="exampleInputEmail1"
										aria-describedby="emailHelp" path="email"
										placeholder="Enter email" />
									<small id="emailHelp" class="form-text text-muted"
										style="font-family: 'Alatsi'; color: black;">We'll
										never share your email with anyone else.</small>
								</div>
							</div>
							<div class="col">
								<div class="form-group">
									<label for="exampleInputPassword1"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">Password</label>
									<form:input type="password" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" id="exampleInputPassword1"
										placeholder="Password" path="plainPassword" />
								</div>
							</div>
							<div class="col">
								<div class="form-group">
									<label for="exampleInputPassword2"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">Repeat
										password</label>
									<form:input type="password" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" id="exampleInputPassword2"
										placeholder="Repeat password" path="repeatPassword" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<div class="form-group">
									<label for="exampleInputPassword1"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">House
										number</label>
									<form:input type="text" id="houseNumber" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" placeholder="House number"
										path="housenumber" />
								</div>
							</div>
							<div class="col">
								<div class="form-group">
									<label for="exampleInputPassword1"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">Street
										name</label>
									<form:input type="text" id="streetName" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" placeholder="Street name"
										path="streetname" />
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col">
								<div class="form-group">
									<label for="exampleInputPassword1"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">City</label>
									<form:input type="text" id="city" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" placeholder="City" path="city" />
								</div>
							</div>
							<div class="col">
								<div class="form-group">
									<label for="exampleInputPassword1"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">County</label>
									<form:input type="text" id="county" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" placeholder="County" path="county" />
								</div>
							</div>
							<div class="col">
								<div class="form-group">
									<label for="exampleInputPassword1"
										style="font-family: 'Alatsi'; font-size: 20px; color: black;">Postcode</label>
									<form:input type="text" id="postcode" required="required"
										style="border-radius: 0; font-family: 'Alatsi'; color: black;"
										class="form-control" placeholder="Postcode" path="postcode" />
								</div>
							</div>
						</div>

						<div>
							<form:select
								style="border-radius: 0; font-family: 'Alatsi'; color: black;"
								class="custom-select" id="inputGroupSelect01" path="categoryid">
								<option selected>Select a category</option>
								<option value="1">Beautian</option>
								<option value="2">Hair stylist</option>
								<option value="3">Electrician</option>
								<option value="4">Plumber</option>
								<option value="5">Painter</option>
								<option value="6">Fitness trainer</option>
								<option value="7">Dog walker</option>
								<option value="8">Carpenter</option>
							</form:select>
						</div>
						<br>
						<div class="custom-file">
							<input type="file"  onchange="file1onchange();" class="custom-file-input" required="required"
								id="inputGroupFile01" style="border-radius: 0;" accept="image/*"
								name="file"> <div id="label1"
								class="custom-file-label"
								style="border-radius: 0; font-family: 'Alatsi'; font-size: 20px; color: black;"
								> Upload a profile picture </div>
						</div>
						<br>
						<br>
						<div class="custom-file">
							<input type="file" onchange="file2onchange();" class="custom-file-input" required="required"
								id="inputGroupFile02" style="border-radius: 0;" accept="image/*"
								name="file2">
							<div id="label2" class="custom-file-label"
								style="border-radius: 0; font-family: 'Alatsi'; font-size: 20px; color: black;">
								Upload a supporting document for this skill/profession</div>
						</div>

						<br><br><b><div id="localError2" style="color: red;"></div></b>

						<form:input type="hidden" id="categoryname" path="categoryname"
							value="" />

						<form:input type="hidden" id="latId" path="lat" value="0" />
						<form:input type="hidden" id="lngId" path="lng" value="0" />
						<br>
						<br>
						<div style="padding-left: 33%;">
							<button type="button" onclick="doSubmitRegister();"
								style="width: 50%; background-color: black; color: white; font-family: 'Alatsi'; font-size: 20px;"
								class="btn">Register</button>
						</div>
					</form:form>

					<%--  <form method="post" enctype="multipart/form-data" action="${uploadPhotoLink}">
						Select photo: <input type="file" accept="image/*" name="file"/>
						
						<input type="submit" value="upload"/>
						
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>  --%>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	(function() {
		document.getElementById("localError").innerHTML = "";
	});
    
	
	function file1onchange()	{
		//this is teh file change event
		
		var lastDot = document.getElementById("inputGroupFile01").files[0].name.lastIndexOf('.');
		var fileextension = document.getElementById("inputGroupFile01").files[0].name.substring(lastDot+1);
		
		//checking the file extension from client side for image1
		if(fileextension == "jpg" || fileextension == "jpeg" || fileextension == 'png')	{
			if(document.getElementById("inputGroupFile01").files[0].size > 1000000)	{
				document.getElementById("localError2").innerHTML = "File size is more than 1 MB, Please choose an image with lower size.";
				document.getElementById("label1").innerHTML = "Upload a profile picture";
			}	else	{
				document.getElementById("localError2").innerHTML = "";
				document.getElementById("label1").innerHTML = "Photo selected success";
			}
		}	else	{
			document.getElementById("localError2").innerHTML = "The file should be an image";
			document.getElementById("label1").innerHTML = "Upload a profile picture";	
		}
	}
	
	function file2onchange()	{
		//this is teh file change event
		
		var lastDot = document.getElementById("inputGroupFile02").files[0].name.lastIndexOf('.');
		var fileextension = document.getElementById("inputGroupFile02").files[0].name.substring(lastDot+1);
		
		//checking the file extension from client side for image2
		if(fileextension == "jpg" || fileextension == "jpeg" || fileextension == 'png')	{
			if(document.getElementById("inputGroupFile02").files[0].size > 1000000)	{
				document.getElementById("localError2").innerHTML = "File size is more than 1 MB, Please choose an image with lower size.";
				document.getElementById("label2").innerHTML = "Upload a supporting document for this skill/profession";
			}	else	{
				document.getElementById("localError2").innerHTML = "";
				document.getElementById("label2").innerHTML = "Certificate selected success";	
			}
		}	else	{
			document.getElementById("localError2").innerHTML = "The file should be an image";
			document.getElementById("label1").innerHTML = "Upload a profile picture";	
		}
	}
	
	function doSubmitRegister() {
		
		/*image check is done only in server side*/ 
		
		
		var success = 0;
		//trimming all imput values
		var _firstname = document.getElementById("firstnameid").value.trim();
		var _lastname = document.getElementById("lastnameid").value.trim();
		var _phonenumber = document.getElementById("phonenumberid").value
				.trim();
		var _exampleInputEmail = document.getElementById("exampleInputEmail1").value
				.trim();
		var _houseNumber = document.getElementById("houseNumber").value.trim();
		var _streetName = document.getElementById("streetName").value.trim();
		var _city = document.getElementById("city").value.trim();
		var _county = document.getElementById("county").value.trim();
		var _postcode = document.getElementById("postcode").value.trim();
		
		//checking no white spaces
		if (_firstname.length > 0 && _lastname.length > 0
				&& _phonenumber.length > 0 && _exampleInputEmail.length > 0
				&& _houseNumber.length > 0 && _streetName.length > 0
				&& _city.length > 0 && _county.length > 0
				&& _postcode.length > 0) {
			success = 1;
			
			//assigning the trimmed value to imput fields
			document.getElementById("firstnameid").value = document
					.getElementById("firstnameid").value.trim();
			document.getElementById("lastnameid").value = document
					.getElementById("lastnameid").value.trim();
			document.getElementById("phonenumberid").value = document
					.getElementById("phonenumberid").value.trim();
			document.getElementById("exampleInputEmail1").value = document
					.getElementById("exampleInputEmail1").value.trim();
			document.getElementById("houseNumber").value = document
					.getElementById("houseNumber").value.trim();
			document.getElementById("streetName").value = document
					.getElementById("streetName").value.trim();
			document.getElementById("city").value = document
					.getElementById("city").value.trim();
			document.getElementById("county").value = document
					.getElementById("county").value.trim();
			document.getElementById("postcode").value = document
					.getElementById("postcode").value.trim();

			//Password empty white space
			if(document.getElementById("exampleInputPassword1").value.trim().length == 0 || 
					document.getElementById("exampleInputPassword2").value.trim().length == 0)	{
				document.getElementById("localError").innerHTML = "Password cannot be empty/whitespace";
			}
			//password match
			else if (document.getElementById("exampleInputPassword1").value != document
					.getElementById("exampleInputPassword2").value) {
				document.getElementById("localError").innerHTML = "Passwords do not match";
			//password length
			} else if (document.getElementById("exampleInputPassword1").value.length >= 5
					&& document.getElementById("exampleInputPassword1").value.length <= 15) {
				//check for password length
				$("#categoryname").val(
						$("#inputGroupSelect01 option:selected").text());
				if (document.getElementById("categoryname").value == "Select a category") {
					//select category name
					document.getElementById("localError").innerHTML = "Please select a category";
				} else {

					//check for valid email
					var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
					if (!(document.getElementById("exampleInputEmail1").value
							.match(mailformat))) {
						//match email user entered with the regular expression for vaild email format
						document.getElementById("localError").innerHTML = "Email is in incorrect format. Please check and try again";
					} else {
						var address = document.getElementById("houseNumber").value
								+ ","
								+ document.getElementById("streetName").value
								+ ","
								+ document.getElementById("city").value
								+ ","
								+ document.getElementById("county").value
								+ ",Ireland";
						//address html encoding to convert to url form
						address = escape(address);

						//need to delete the following two lines and uncomment teh followign lines
						/* document.getElementById("latId").value = '44';
						document.getElementById("lngId").value = '61'; */

						$
								.ajax({
									url : "https://maps.googleapis.com/maps/api/geocode/json?address='"
											+ address
											+ "'&key=AIzaSyBz5UcW0U8KGSa3tAa039I7vtCy_hld14Y",
									method : "GET",
									//ajax call parameter
									success : function(response) {
										var res = "";
										res = response.status;
										if (res.localeCompare("OK") == 0) {
											document.getElementById("latId").value = response.results[0].geometry.location.lat
													+ '';
											document.getElementById("lngId").value = response.results[0].geometry.location.lng
													+ '';

											//finally, submit the form
											$("#registerForm").submit();
										}
									},
									failure : function(error) {
										alert("Please try again");
									}
								});
					}
				}

			} else {
				document.getElementById("localError").innerHTML = "Password should be 5 - 15 chars in length";
			}
		} else {
			document.getElementById("localError").innerHTML = "Please enter value for all fields";
		}
	}
</script>
