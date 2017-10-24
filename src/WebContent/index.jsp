<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

  <!-- Theme Made By www.w3schools.com - No Copyright -->
  <title>MarkupProject</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link href="./css/style.css" rel="stylesheet" type="text/css">
  <script type="text/javascript" src="./javascript/script.js"></script></head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	  <div class="container-fluid">
	    <div class="navbar-header">
	        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>
	          <span class="icon-bar"></span>                        
	      </button>
	      <a class="navbar-brand" href="#">HTML Markup</a>
	    </div>
	    <div>
	      <div class="collapse navbar-collapse" id="myNavbar">
	        <ul class="nav navbar-nav">
	          <li><a href="#section1">Test your file!</a></li>
	          <li><a href="#section2">See how other's compare</a></li>
	          <li><a href="#section3">Explore other scores</a></li>
	        </ul>
	      </div>
	    </div>
	  </div>
	</nav>    
	
	<div id="section1" class="container-fluid">
		<div class="row">
		  <div class="col-sm-6">
		  <br><br>
			  <h1 class="title">HTML Markup Parser</h1> 
			  <p>Grade your HTML file based on the tags you use!</p> 
			  <p class="warning">*Please note, all files uploaded are saved to the system.
			  <br>
			  	 Anyone can access your file once it has been test run.
			  <br>
			     Thanks for understanding!</p>
			  <div id="uploadForm">
				  <form action="MarkupServlet" method="post" enctype="multipart/form-data">
				    <input class="upload" type="file" name="file" >
				    <input onclick="removeErrorMsg()" class="upload" type="submit" name= "uploadFile" id="uploadFile" value="Test it!">
				  </form>
				
				<div id="errorDiv"><br></div>
				  
				  <c:if test="${not empty errorMsg}">
				  	<script>
						addErrorMsg("${errorMsg}");
					</script>
				  </c:if>
					
			   </div>
			 </div>
			 
			 <div class="col-sm-6">
			 <br><br><br>
			 	<c:if test="${not empty finalscore}">
			 		<h2> CONGRATS! Your score is:</h2>
			 		<h1> ${finalscore} points!</h1>
			 	</c:if>
			 </div>
		</div>
	</div>
	
	<div id="section2" class="container-fluid">
		<h1>See how everyone is doing!</h1>
		<div class="row">
		  <div class="col-sm-6">
		  	 <h3 class="highlow">Highest Scoring File!</h3>
		  	 <p>
		  	 	File name: ${high_score.fileName}
		  	 	<br>
		  	 	File Score: ${high_score.score}
		  	 </p>
		  </div>
		  <div class="col-sm-6">
		  	<h3 class="highlow">Lowest Scoring File...</h3>
		  	 <p>
		  	 	File name: ${low_score.fileName}
		  	 	<br>
		  	 	File Score: ${low_score.score}
		  	 </p>
		  </div>
		</div>
		
		<div class="table-responsive">
			  <h3>All Files Average</h3>
			  <table class="table table-bordered">
			    <thead>
			      <tr>
			        <th>File Name</th>
			        <th>Score</th>
			      </tr>
			    </thead>
			    <tbody>
			      <c:forEach items="${files}" var="file">
				      <tr>
				      <td>${file.id}</td>
				      <td>${file.score}</td>
					  </tr>
				  </c:forEach>
			   </tbody>
			</table>
		</div>
	</div>
		    
	<div id="section3" class="container-fluid" style="width: 100%; display: table;">
		<h1>Want to know some specifics? Explore other file scores below!</h1>
		<div class="row">
			<div class="col-sm-6">
			
				<form id="myForm"  action="MarkupServlet" method="post" >
					<br>
					Begining Date: <input type="date" name="date1">
					<br>
					Ending Date: <input type="date" name="date2">
					<br>
					<input type="submit" name= "dateSelector" value="Explore!">			
				</form>
				<p id="dates">
					<c:if test="${not empty fileRange}">
						<div class="table-responsive">
						  <h4>Files between ${date1} and ${date2}</h4>
						  <table class="table table-bordered">
						    <thead>
						      <tr>
						        <th>File Name</th>
						        <th>Score</th>
						      </tr>
						    </thead>
						    <tbody>
						      <c:forEach items="${fileRange}" var="file">
							      <tr>
							        <td>${file.fileName}</td>
							        <td>${file.score}</td>
							      </tr>
						      </c:forEach>
						   	</tbody>
						   </table>
						</div>
					</c:if>
				</p>
			</div>
			
			<div class="col-sm-6">
				<br>
				<form action="MarkupServlet" method="post" >
					  <input class="fileNames" list="fileNames" name="fileNames">
					  <datalist id="fileNames">
					    <c:forEach items="${fileNames}" var="f">
					    	<option name="fileOption" value="${f}">
					    </c:forEach>
					  </datalist>
					  <input type="submit" class="fileNames" name="fileToDisplay" value="Check It!">
				</form>
				<br><br>
				<div class="col-sm-6">
					<c:if test="${not empty fileOptionName}">
						<div class="table-responsive">
						  <table class="table table-bordered">
						    <thead>
						      <tr>
						        <th>Scores for ${fileOptionName}</th>
						       </tr>
							 </thead>
							   <tbody>
							     <c:forEach items="${fileScores}" var="scores">
								      <tr>
								        <td>${scores}</td>
								      </tr>
							      </c:forEach>
						   	</tbody>
			 		  	 </table>
						</div>						
					</c:if>
				</div>
			</div>
		</div>
		
	</div>
		   
	<script>
		$(document).ready(function(){
			  // Add scrollspy to <body>
			  $('body').scrollspy({target: ".navbar", offset: 50});   
	
			  // Add smooth scrolling on all links inside the navbar
			  $("#myNavbar a").on('click', function(event) {
			    // Make sure this.hash has a value before overriding default behavior
			    if (this.hash !== "") {
			      // Prevent default anchor click behavior
			      event.preventDefault();
	
			      // Store hash
			      var hash = this.hash;
	
			      // Using jQuery's animate() method to add smooth page scroll
			      // The optional number (800) specifies the number of milliseconds it takes to scroll to the specified area
			      $('html, body').animate({
			        scrollTop: $(hash).offset().top
			      }, 800, function(){
			   
			        // Add hash (#) to URL when done scrolling (default click behavior)
			        window.location.hash = hash;
			      });
			    }  // End if
			  });
			});

		$(window).scroll(function() {
			  sessionStorage.scrollTop = $(this).scrollTop();
			});

			$(document).ready(function() {
			  if (sessionStorage.scrollTop != "undefined") {
			    $(window).scrollTop(sessionStorage.scrollTop);
			  }
			});
	</script>
</body>
</html>