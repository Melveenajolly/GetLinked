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
	Welcome, here you can see all your work details..<br> <br>

	<sec:authorize access="isAuthenticated()">
		<table class="table table-striped table-dark">
			<thead>
				<tr>
					<th scope="col">#</th>
					<th scope="col">Client</th>
					<th scope="col">Work start date and time</th>
					<th scope="col">You got hired on</th>
					<th scope="col">Hire Address</th>
					<th scope="col">Status</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${workinfos}" var="workinfo" varStatus="loop">
					<tr>
						<th>${loop.count}</th>
						<%-- <td>${userNames[loop.count].email}</td> --%>
						<td>${workinfo.username}</td>
						<td>${workinfo.hireDate}</td>
						<td>${workinfo.date}</td>
						<td>${workinfo.hireaddress}</td>
						<td><form:form method="POST" modelAttribute="hireinfo"
								action="/setCompletedHire">
								<form:hidden value="${workinfo.id}" path="id" />
								<c:if test="${workinfo.isCompleted == 0}">
									<input type="checkbox" name="completed"
										onchange="this.form.submit()" value="checked" />
								</c:if>
								<c:if test="${workinfo.isCompleted == 1}">
									<input type="checkbox" name="completed"
										onchange="this.form.submit()" value="checked" checked />
								</c:if>

							</form:form></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${workinfos.size() == 0}">
			<p>
			<div class="alert alert-info" role="alert">No work to show. If
				someone hires you, then details will be shown here..</div>
			</p>
		</c:if>
	</sec:authorize>
</div>


<script>
	/* function setOrUnsetChkbx(id) {
		alert(id);
		alert();
	} */
</script>
