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
	<div class="card-group">
		<div class="card" style="width: 50%; background-color: coral;">
			<div class="card-body">
				<img class="card-img-top"
					style="padding-bottom: 10%; width =10%; align: center;"
					src="/ProfessionalImages/${professionalProfile.email}.${professionalProfile.photoExtension}"
					alt="Card image cap">
					<h6 style="color:white;">Rating     : ${avgrating }</h6>
					<h6 style="color:white;">Hourly rate: 10</h6>
			</div>
		</div>

		<sec:authorize access="isAuthenticated()">
			<!-- Comments -->
			<div class="card" style="width: 75%;">
				<div class="card-body">
					<h5 class="card-title">
						<b style="color: black;">${professionalProfile.firstname}
							${professionalProfile.lastname}</b>
					</h5>
					<h6 class="card-subtitle mb-2 text-muted">
						<b style="color: black;">${professionalProfile.categoryname}</b>
					</h6>
					<p>Address: ${professionalProfile.housenumber},
						${professionalProfile.streetname}, ${professionalProfile.city},
						${professionalProfile.county}</p>


					<b><div id="localError" style="color: red;"></div></b> <b><div
							id="localMessage" style="color: green;"></div></b>

					<div class="input-group mb-3">
						<form:form method="post" modelAttribute="review" id="reviewForm">
							<form:input type="hidden" path="username" value="${username}" />
							<form:input type="hidden" path="professionalid"
								value="${professionalid}" />
							<form:input type="hidden" id="ratingField" path="rating" />

							<div class="rate">
								<input type="radio" id="star5" name="ratet" value="5" /> <label
									for="star5" title="text"> &#9734;</label> <input type="radio"
									id="star4" name="ratet" value="4" /> <label for="star4"
									title="text"> &#9734;</label> <input type="radio" id="star3"
									name="ratet" value="3" /> <label for="star3" title="text">
									&#9734;</label> <input type="radio" id="star2" name="ratet" value="2" />
								<label for="star2" title="text"> &#9734;</label> <input
									type="radio" id="star1" name="ratet" value="1" /> <label
									for="star1" title="text"> &#9734;</label>
							</div>
							<form:input type="text" id="commentField" class="form-control"
								path="comment" placeholder="Comment" />
							<br>
							<table>
								<tr>
									<td><a class="btn btn-dark"
										href="/hirepage?professionalid=${professionalProfile.id}">Hire</a>
									</td>
									<td>
										<div class="input-group-append">
											<button type="button" class="btn btn-info"
												onclick="doSubmitAddComment();">Add review</button>
										</div>
									</td>
									<td><a class="btn btn-dark"
										href="/viewAll?categoryid=${professionalProfile.getCategoryid()}">Back to list</a>
									</td>
								</tr>
							</table>


						</form:form>
					</div>
				</div>
			</div>
	</div>
	<div class="card" style="width: 100%;">
		<div class="card-body">
			<h6>
				<b>Previous comments</b>
			</h6>
			<c:forEach items="${reviews}" var="review" varStatus="loop">
				<c:if test="${review.comment != ''}">
					<div class="alert alert-primary" role="alert">${review.comment}</div>
				</c:if>
			</c:forEach>

		</div>
	</div>
	</sec:authorize>





</div>

<script type="text/javascript">
	function doSubmitAddComment() {
		document.getElementById("commentField").value = document
				.getElementById("commentField").value.trim();
		if (document.getElementById("commentField").value.length == 0) {
			document.getElementById("localError").innerHTML = "Please provide any comment";
		} else {
			var ratingByUser = $("input[name=ratet]:checked").val();
			if (ratingByUser == null) {
				document.getElementById("localError").innerHTML = "Please provide rating";
			} else {
				document.getElementById("localMessage").innerHTML = "Review added";
				$("#ratingField").val(ratingByUser);
				$("#reviewForm").submit();
				document.getElementById("commentField").value = '';
			}
		}
	}
</script>
