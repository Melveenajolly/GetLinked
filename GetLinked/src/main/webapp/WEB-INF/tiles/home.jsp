<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>

<div>
	<div id="myCarousel" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
			<li data-target="#myCarousel" data-slide-to="1"></li>
			<li data-target="#myCarousel" data-slide-to="2"></li>
		</ol>

		<!-- Wrapper for slides -->
		<div class="carousel-inner" role="listbox"
			style="width: 100%; height: 560px">
			<div class="carousel-item active">
				<img src="/img/bg.jpg" width="1400" height="auto"
					alt="getlinkedbgimage">
				<div class="carousel-caption">
					<h3 style="font-family: 'Anton';font-size: 36px; letter-spacing: 3.5px;">BEST PROFESSIONALS IN YOUR CITY</h3>
					<p  style="font-family: 'Alatsi'; font-size: 15px; color: white; letter-spacing: 2px">Dedicated and Devoted Professionals <br>To Serve You at Your Doorstep</p>
				</div>
			</div>

			<div class="carousel-item">
				<img src="/img/bg2.jpg" alt="Chicago" width="1400%" height="auto">
				<div class="carousel-caption">
					<h3  style="font-family: 'Anton';font-size: 36px; letter-spacing: 3.5px;">HOME IMPROVEMENTS JUST GOT EASIER</h3>
					<p style="font-family: 'Alatsi'; font-size: 15px; color: white; letter-spacing: 2px">Services You Can Trust</p>
				</div>
			</div>

			<div class="carousel-item">
				<img src="/img/bg3.jpg" alt="Los Angeles" width="1400" height="auto">
				<div class="carousel-caption">
					<h3 style="font-family: 'Anton';font-size: 36px; letter-spacing: 3.5px;">MOST TALENTED TOP-LEVEL BEAUTY SPECIALISTS</h3>
					<p style="font-family: 'Alatsi'; font-size: 15px; color: white; letter-spacing: 2px">On-Demand Beauty Service to your Door</p>
				</div>
			</div>
		</div>

		<!-- Left and right controls -->
		<a class="left carousel-control" href="#myCarousel" role="button"
			data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
			aria-hidden="true"></span> <span class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#myCarousel" role="button"
			data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>


	<!-- jumbotron for displaying the recent hires -->
	<sec:authorize access="isAuthenticated()">
	<c:if test="${hiredProssionals.size() > 0}">
	
	<div class="container-fluid"
		style="padding-left: 1%; padding-right: 1%; padding-top: 6%; width: 90%; height: auto;">

		<div class="jumbotron   text-white" style="background-color: #B0C4DE; width:100%;height:auto; opacity: 80%;">
			<div class="row">
				<div class="col-sm-4" style="padding-left: 8%;">
					<p>
						<b style=" font-family: 'Alatsi'; font-size: 17px; color: #00008B;  letter-spacing: 2px"> Recommendations inspired by your past hires</b>
					</p>
				</div>


				<div class="col-sm-8">
					<div class="container-fluid" style="width: 90%; height: 5%">
						<div id="carouselExampleIndicators" class="carousel slide"
							data-ride="carousel">
							<%-- <ol class="carousel-indicators">
								<li data-target="#carouselExampleIndicators" data-slide-to="${ loop.index }"
									class="<c:if test="${loop.index  == 0}"> active </c:if>"></li>
								
							</ol> --%>
							
							<div class="carousel-inner">
							<c:forEach items="${hiredProssionals}" var="hiredProssional" varStatus="loop">
								<div class="carousel-item <c:if test="${loop.index  == 0}"> active </c:if>" id="${ loop.index }">
									<div class="card text-center">
										
										<div class="card-body">
											<b class="card-title" style=" font-family: 'Alatsi'; font-size: 20px; color: #8B0000;  letter-spacing: 1.5px">${hiredProssional.firstname} ${hiredProssional.lastname}</b>
											<p class="card-text" style=" font-family: 'Alatsi'; font-size: 15px; color: #8B0000;  letter-spacing: 1.5px" >${hiredProssional.categoryname}</p>
											<a href="view?professionalid=${hiredProssional.id}" class="btn btn-primary">View Profile</a>
										</div>
										
									</div>
								</div>
								</c:forEach>
								</div>
								
								
							</div>
							<a class="carousel-control-prev"
								href="#carouselExampleIndicators" role="button"
								data-slide="prev"> <span class="carousel-control-prev-icon"
								aria-hidden="true"></span> <span class="sr-only">Previous</span>
							</a> <a class="carousel-control-next"
								href="#carouselExampleIndicators" role="button"
								data-slide="next"> <span class="carousel-control-next-icon"
								aria-hidden="true"></span> <span class="sr-only">Next</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	</sec:authorize>
	

	<div class="services container-fluid" style="padding-top: 55px;">
		<div class="text-center">
			<h3
				style="font-family: 'Coda Caption'; font-size: 20px; color: black;">services
			</h3>
			<h1
				style="font-family: 'Coda Caption'; font-size: 50px; color: black;">WHAT
				WE DO</h1>
		</div>
	</div>

	<div class="container-fluid"
		style="padding-top: 100px; padding-left: 10%; padding-bottom: 10%; background-image:/img/services background.jpg; 10%; ">
		<div class="row">

			<div class="col">
				<img src="/img/makeup-icon.png" width="30%" height="auto">
				<p style="font-family: 'Alatsi';font-size: 25px; color: black; padding-top: 10%">
					<a href="/viewAll?categoryid=1" style="color: black; text-decoration: none;"><strong>SALON AT HOME</strong></a>
				</p>
				<hr style="border: 5px solid #D2691E;border-radius: 5px; width:30%; margin-left: 0">
			</div>
			
			<div class="col">
				<img src="/img/hair.png" width="30%" height="auto">
				<p style="font-family: 'Alatsi';font-size: 25px; color: black; padding-top: 10%">
					<a href="/viewAll?categoryid=2" style="color: black; text-decoration: none;"><strong>HAIR STYLIST</strong></a>
				</p>
				<hr style="border: 5px solid #D2691E;border-radius: 5px; width:30%; margin-left: 0">
			</div>
			<div class="col">
				<img src="/img/electrician-icon.png" width="30%" height="auto">
				<p style="font-family: 'Alatsi';font-size: 25px; color: black; padding-top: 10%">
					<a href="/viewAll?categoryid=3" style="color: black; text-decoration: none;"><strong>ELECTRICIAN</strong></a>
				</p>
				<hr style="border: 5px solid #D2691E;border-radius: 5px; width:30%; margin-left: 0">
			</div>
			<div class="col">
				<img src="/img/plumber.png" width="30%" height="auto">
				<p style="font-family: 'Alatsi';font-size: 25px; color: black; padding-top: 10%">
					<a href="/viewAll?categoryid=4" style="color: black; text-decoration: none;"><strong>PLUMBER</strong></a>
				</p>
				<hr style="border: 5px solid #D2691E;border-radius: 5px; width:30%; margin-left: 0">
			</div>
		</div>
		<div class="row" style="padding-top: 50px;">
			<div class="col">
				<img src="/img/painter.png" width="30%" height="auto">
				<p style="font-family: 'Alatsi';font-size: 25px; color: black; padding-top: 10%">
					<a href="/viewAll?categoryid=5" style="color: black; text-decoration: none;"><strong>PAINTER</strong></a>
				</p>
				<hr style="border: 5px solid #D2691E;border-radius: 5px; width:30%; margin-left: 0">
			</div>
			<div class="col">
				<img src="/img/gym.png" width="30%" height="auto">
				<p style="font-family: 'Alatsi';font-size: 25px; color: black; padding-top: 10%">
					<a href="/viewAll?categoryid=6" style="color: black; text-decoration: none;"><strong>FITNESS TRAINER</strong></a>
				</p>
				<hr style="border: 5px solid #D2691E;border-radius: 5px; width:30%; margin-left: 0">
				
			</div>
			<div class="col">
				<img src="/img/dog-walking.png" width="30%" height="auto">
				<p style="font-family: 'Alatsi';font-size: 25px; color: black; padding-top: 10%">
					<a href="/viewAll?categoryid=7" style="color: black; text-decoration: none;"><strong>DOG WALKER</strong></a>
				</p>
				<hr style="border: 5px solid #D2691E;border-radius: 5px; width:30%; margin-left: 0">
			</div>

			<div class="col">
				<img src="/img/carpentry.png" width="30%" height="auto">
				<p style="font-family: 'Alatsi';font-size: 25px; color: black; padding-top: 17%">
					<a href="/viewAll?categoryid=8" style="color: black; text-decoration: none;"><strong>CARPENTER</strong></a>
				</p>
				<hr style="border: 5px solid #D2691E;border-radius: 5px; width:30%; margin-left: 0">
			</div>
		</div>

	</div>