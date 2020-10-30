<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<div class="container-fluid" style="padding: 10%; padding-left: 20%">
	<sec:authorize access="isAuthenticated()">
		<form:form method="post" modelAttribute="hireinfo" id="hirepageform"
			action="/hirepage">
			<div class="card" style="width: 75%;">
				<div class="card-body">
					<h5 class="card-title">
						<%-- <c:if test="${param.error != null}">
							<div class="login-error" id="errormessagediv">Incorrect
								username or password.</div>
						</c:if>  --%>
						<%-- <form:errors path="userContact"></form:errors> --%>
						<c:if test="${successMessage != ''}">
							<p>
							<div style="color: green;">${successMessage}</div>
							</p>
						</c:if>
						<b><div id="localError" style="color: red;"></div></b><br>
						 Hire <b
							style="color: black;">${professionalProfile.firstname}
							${professionalProfile.lastname}</b>
						(${professionalProfile.categoryname})
					</h5>
					
					<form:input type="hidden"
						value="${professionalProfile.phonenumber}"
						path="professionalContact" />
					<form:input type="hidden" class="form-control"
						value="${professionalProfile.categoryname}" path="category" />
						
					<form:input type="hidden" value="${username}" path="username" />
					
					<form:input type="hidden" value="${professionalid}"
						path="professionalid" />

					<div class="form-group">
						<label for="exampleFormControlTextarea1">Your address</label>
						<form:input type="text" id="hireaddress" class="form-control"
							style="box-sizing: border-box;" value="${userprofile.address}"
							path="hireaddress" />
					</div>
					<div class="form-group">
						<label for="exampleFormControlTextarea1">Please provide
							your contact number</label>
						<form:input type="number" class="form-control" id="userContact"
							placeholder="+353_ _ _ _ _ _ _ _ _ _" value="123456789"
							style="box-sizing: border-box;" path="userContact" />
					</div>
					<div class="form-group">
						<label for="exampleFormControlTextarea1">Select date and
							time</label>
						<form:input type="datetime-local" id="meeting-time"
							class="form-control" name="meeting-time" value=""
							min="2020-06-04T00:00" max="2020-12-31T00:00" path="hireDate" />
					</div>

					<!-- oninput event triggered when value changed for input box, and calcamount function called passign
					current value to function -->
					<div class="form-group">
						<label for="exampleFormControlTextarea1">Estimate number
							of hours required to complete work</label> <input type="number" min=0 max=10
							id="hours" oninput="calcamount(this.value)" id="hours"
							class="form-control" name="hours" style="width: 20%;" />
					</div>

					<br> <b><p>Complete payment of
						<div id="amount"></div>
						</p></b>

					<div class="form-group">
						<label for="exampleFormControlTextarea1">Credit/Debit card
							number</label>
						<form:input type="text" class="form-control" id="cardnumber"
							style="box-sizing: border-box;" placeholder="xxxx xxxx xxxx xxxx"
							path="cardnumber" value="1234456465654654" />
					</div>
					<div class="form-group">
						<label for="exampleFormControlTextarea1">Date of expiry (in dd/mm/yyyy format)</label>
						<form:input type="text" class="form-control" id="cardexpiry"
							placeholder="dd/mm/yyyy" style="box-sizing: border-box;"
							path="cardexpiry" value="01/01/2020"/>
					</div>
					<div class="form-group">
						<label for="exampleFormControlTextarea1">CVV</label>
						<form:input type="text" class="form-control" id="cvv"
							style="box-sizing: border-box; width:25%;" path="cvv" value="456"/>
					</div>
					<p>
						<button type="button" class="btn btn-primary" onclick="submitFormm();">Hire</button>
					</p>
				</div>
			</div>
		</form:form>
	</sec:authorize>
</div>


<script>

	
	(function() {
	});

	function calcamount(hours) {
		var amt = 10 * Number(hours);//string to number
		document.getElementById("amount").innerHTML = "£ " + amt
				+ " for the selected time";
	}

	function submitFormm() {
		var error = 0, pass=1;
		var hireaddress = document.getElementById("hireaddress").value.trim();
		var userContact = document.getElementById("userContact").value.trim();
		//checking whether strts from 089
		if (userContact.substring(0, 1) == "0") {
			//then checking length
			if (userContact.length != 10) {
				error = 1;
				pass=0;
			}
			//checking starts from +353
		} else if (userContact.substring(0, 4) == "+353") {
			if (userContact.length != 13) {
				error = 1;
				pass=0;
			}
			//staets from 353
		}	else if(userContact.substring(0, 3) == "353")	{
			if(userContact.length != 12)	{
				error = 1;
				pass=0;
			}
			//total legth check
		} 	else if(userContact.length > 10 || userContact.length < 6)	{
			error = 1;
			pass=0;
		}
		
		//trimming cardexpiry, meeting time, hours, cardnumber,cvv
		var cardexpiry = document.getElementById("cardexpiry").value.trim();
		var meetingtime = document.getElementById("meeting-time").value.trim();
		var hours = document.getElementById("hours").value.trim();
		var cardnumber = document.getElementById("cardnumber").value.trim();
		
		//hire date splits
		//hiredateday is the hiredate day
		//hiredatemonth is the hiredate month
		//hiredateyear is the year of hire
		var hiredateday = (meetingtime.split("-")[2] + "").split("T")[0],
		hiredatemonth = meetingtime.split("-")[1],
		hiredateyear = meetingtime.split("-")[0];
		
		var cvv = document.getElementById("cvv").value.trim();
		//checking white spsces after trimming
		if (hireaddress.length == 0 || userContact.length == 0
				|| meetingtime.length == 0 || hours.length == 0
				|| cardnumber.length == 0 || cardexpiry.length == 0
				|| cvv.length == 0) {
			pass=0;
			document.getElementById("localError").innerHTML = "Please input all fields";
			
			//card number lngth
		}	else if(cardnumber.length != 16)	{
			pass=0;
			document.getElementById("localError").innerHTML = "Cardnumber should be 16 digits long";
		}
		
		//phone number error message
		else if(error == 1)	{
			pass=0;
			document.getElementById("localError").innerHTML = "Phone number is not valid";
			
			//doubt
		} 	else if(Number(hiredateday) > 31 ||  
				Number(hiredateday) < 1 ||
				Number(hiredatemonth) < 1 ||
				Number(hiredatemonth) > 12)	{
			pass=0;
			document.getElementById("localError").innerHTML = "Hiredate is not in correct format / incorrect";
		}	else	{
			//2020-09-01T22:42
			document.getElementById("localError").innerHTML = "Validation pass";
		
			//card expiry splits
			var cardexpiryday = cardexpiry.split("/")[0], cardexpirymonth = cardexpiry.split("/")[1], 
			cardexpiryyear = cardexpiry.split("/")[2]; 
			
			//hire date splits
			var hiredateday = (meetingtime.split("-")[2] + "").split("T")[0],
			hiredatemonth = meetingtime.split("-")[1],
			hiredateyear = meetingtime.split("-")[0];
			
			//today's date
			var date = new Date();
			//todays date in year -month-date-hour:min form
			var today = new Date(date.getFullYear(), date.getMonth(),  date.getDate(), date.getHours(), date.getMinutes());
			
			//date whicih is one hour ahead of now- to check for hiredate- hiredate should be 1 hour ahead of now atleast.
			//makng date object whiwhc 
			var todayPlusOneHour = 
				new Date(date.getFullYear(), date.getMonth(), date.getDate(), Number(date.getHours()) + 1, date.getMinutes());
			
			//makeing date oject for corresponsding hiredate that user selectred
			var hiredate_new = new Date(hiredateyear, Number(hiredatemonth)-1, hiredateday, (meetingtime.split("T")[1] + "").split(":")[0],
					(meetingtime.split("T")[1] + "").split(":")[1]);
			
			
			//mkng corresopndign datre object for card expiry date
			var cardexpiry_new = new Date(cardexpiryyear, cardexpirymonth-1 , cardexpiryday, date.getHours(), date.getMinutes());
			
			//making a date object that is 60 days ahead from now (for checking max range for hiredate- one can hire only 
					//upto a date which is within 60 days from today)
			var after60daysfromtoday = new Date();
			after60daysfromtoday.setDate(date.getDate() + 60);
				
			//cardexpiry check
			if(Number(cardexpiry.split("/").length) != 3)	{
				pass=0;
				document.getElementById("localError").innerHTML = "Card expiry date is not in correct format";
			}	else if(Number(cardexpiryday) > 31 || 
					Number(cardexpiryday) < 1 ||
							Number(cardexpirymonth) < 1 ||
									Number(cardexpirymonth) > 12)	{
				pass=0;
				document.getElementById("localError").innerHTML = "Card expiry date is not in correct format / incorrect";
			}	else if(hiredate_new < todayPlusOneHour)	{
				pass=0;
				document.getElementById("localError").innerHTML = "Hiredate cannot be in past";
			}	else if(cardexpiry_new < today)	{
				pass=0;
				document.getElementById("localError").innerHTML = "Card expiry date cannot be in past";
			}	else if(hiredate_new > after60daysfromtoday)	{
				pass=0;
				document.getElementById("localError").innerHTML = "You cannot hire a person for a date beyond 60 days from today. Please select a closer date";
			}
		}

		if(pass == 1)	{
			$("#hirepageform").submit();	
		}
	}
</script>
