<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="container-fluid"
	style="padding-top: 10%; padding-left: 10%; padding-right: 10%;">

	<div class="card-columns">
		<c:forEach items="${unverifiedProfessionals}"
			var="unverifiedProfessional" varStatus="loop">
			<c:set var="professionalFormId" value="${loop.index}" />

			<div class="card"
				style="border: none; border-radius: 0; width: 90%; margin-bottom: 50px;">

				<div class="card-body">
					<table>
						<tr>
							<td align="center"><img class="card-img-top" width=50 height=200
								style="min-width: 50px; min-height: 50px; padding-bottom: 10%;"
								src="/ProfessionalImages/${unverifiedProfessional.email}.${unverifiedProfessional.photoExtension}"
								alt="Card image cap"></td>
						</tr>

					</table>


					<table>
						<tr>
							<td align=center><h5>${unverifiedProfessional.firstname}
									${unverifiedProfessional.lastname}</h5></td>
						</tr>
						<tr>
							<td align=center><h5 style="text-align: center;">Address:
									${unverifiedProfessional.city},
									${unverifiedProfessional.county}</h5></td>
						</tr>
						<tr>
							<td align="center"><a
								href="/ProfessionalImages/${unverifiedProfessional.email}_C.${unverifiedProfessional.certExtension}"
								alt="Cerificate image" target="_blank">Open certificate</a></td>
						</tr>
						<tr>
							<td align="center"><a
								href="/view?professionalid=${unverifiedProfessional.id}">View
									details</a></td>
							
						</tr>
						<td align=center><form:form id="${professionalFormId}"
									method="POST" action="/admin">

									<input type="hidden" id="professionalid" name="professionalid"
										value="${unverifiedProfessional.id}" />
									<button class="btn btn-link" " type="submit">Verify
										and confirm</button>
								</form:form></td>
					</table>
				</div>
			</div>
		</c:forEach>
	</div>

</div>