<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<style>
/* a:hover	{
	color:yellow !important;
} */
</style>

<div class="container-fluid"
	style="padding-top: 10%; padding-left: 10%; padding-right: 10%;">

	<form:form method="post" id="viewallForm">
		<input type="hidden" id="categoryidFromForm" name="categoryidFromForm"
			value="0" />
		<input type="hidden" id="lat1" name="lat1" value="-99" />
		<input type="hidden" id="lng1" name="lng1" value="-99" />
	</form:form>

	<table>
		<tr>
			<td>
				<button class="btn btn-info" onclick="loc();">Sort by
					distance</button>
			</td>
			<td>
				<button class="btn btn-info" onclick="sortByRating();">Sort
					by rating</button>
			</td>
			<td><a class="btn btn-dark"
				href="/">Back</a>
			</td>
		</tr>
	</table>
	<br>




	<c:if test="${professionalList.size() == 0}">
		<p>
		<div class="alert alert-danger" role="alert">There are no
			professionals registered for this category.</div>
		</p>
	</c:if>

	
	

		<c:if test="${professionalList.size() > 0 }">
	<div class="row">
			<c:forEach items="${professionalList}" var="professional"
				varStatus="loop">
			
								
				<a href="/view?professionalid=${professional.id}"
					style="position: aboslute;">
					<div class="card"
						style="border: none; border-radius: 0; width: 90%; margin-bottom: 50px; background: #FAF0EB;">

						<div class="card-body">
							<img class="card-img-top" style="padding-bottom: 10%;"
								src="/ProfessionalImages/${professional.email}.${professional.photoExtension}"
								alt="Card image cap">
							<table style="width: 100%;">
								<tr>
									<td align=center><h5 style="text-align: center;">${professional.firstname}
											${professional.lastname}</h5></td>
								</tr>
								<tr>
									<td align=center><h5 style="text-align: center;">${professional.city},
											${professional.county}</h5></td>
								</tr>
								<tr>
									<td align=center>
										<div class="rate">
											<c:if test="${avgRatings[loop.index] == 1}">
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="display: inline">&#9734;</div>
												<div style="display: inline">&#9734;</div>
												<div style="display: inline">&#9734;</div>
												<div style="display: inline">&#9734;</div>
											</c:if>

											<c:if test="${avgRatings[loop.index] == 2}">
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="display: inline">&#9734;</div>
												<div style="display: inline">&#9734;</div>
												<div style="display: inline">&#9734;</div>

											</c:if>

											<c:if test="${avgRatings[loop.index] == 3}">
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="display: inline">&#9734;</div>
												<div style="display: inline">&#9734;</div>
											</c:if>

											<c:if test="${avgRatings[loop.index] == 4}">
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="display: inline">&#9734;</div>
											</c:if>

											<c:if test="${avgRatings[loop.index] == 5}">
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="color: #c59b08; display: inline">&#9734;</div>
												<div style="color: #c59b08; display: inline">&#9734;</div>
											</c:if>

										</div>
									</td>
								</tr>
							</table>
							<c:if test="${isPost == 1}">
								<p align="center">${distanceList[loop.index] } km away</p>
							</c:if>
						</div>
					</div>
				</a>
				
				<input type="hidden" id="categoryIdFromProfessionals"
					value="${professional.categoryid}" />
					
			</c:forEach>
		</div>
		</c:if>
	</div>


</div>

<script>

function loc() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition);
  } else {
    alert("Geolocation is not supported by this browser.");
  }
}

function showPosition(position) {
	var l1 = position.coords.latitude;
	var l2 = position.coords.longitude;

  <!-- send categoryid, latof user, lng of user to server -->
 	$('input[name=categoryidFromForm]').val(document.getElementById('categoryIdFromProfessionals').value);
 	$('input[name=lat1]').val(position.coords.latitude);
 	$('input[name=lng1]').val(position.coords.longitude);
  $('#viewallForm').submit();
}

function sortByRating()	{
	$('input[name=categoryidFromForm]').val(document.getElementById('categoryIdFromProfessionals').value);
	$('#viewallForm').submit();
}


</script>










