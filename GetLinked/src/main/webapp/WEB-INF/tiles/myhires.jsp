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

<div class="container-fluid" style="padding: 10%; padding-left: 10%">
	<sec:authorize access="isAuthenticated()">
		<table class="table table-striped table-dark">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Professional name</th>
					<th scope="col">Category</th>
					<th scope="col">Hire date and time</th>
					<th scope="col">Hire Address</th>
					<th scope="col">Contact- Professional</th>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${hireinfos}" var="hireinfo" varStatus="loop">
					<tr>
						<th>${loop.count}</th>
						<td>${professionalNames[loop.index].firstname}
							${professionalNames[loop.index].lastname}</td>
						<!-- This is list of professionals- names to be fetched -->
						<td>${hireinfo.category}</td>
						<td>${hireinfo.hireDate}</td>
						<td>${hireinfo.hireaddress}</td>
						<td>${hireinfo.professionalContact}</td>
					</tr>
				</c:forEach>
			</tbody>

		</table>
		<c:if test="${hireinfos.size() == 0}">
			<p>
			<div class="alert alert-info" role="alert">No hires to show.
				Hire someone to show details here..</div>
			</p>
		</c:if>
	</sec:authorize>
</div>

