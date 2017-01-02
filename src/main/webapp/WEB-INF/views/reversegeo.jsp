<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Find Address</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<spring:url value="/resources/js/jquery.js" var="jqueryJs" />
<spring:url value="/resources/js/geo.js" var="mainJs" />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 <script src="${jqueryJs}"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${mainJs}"></script>
<script type="text/javascript">
var tableData = "";
<c:if test="${not empty localcache}">
 tableData= ${localcache}
</c:if>
</script>
<style type="text/css">
.errorMessages {color: #ff0000;}
#allgeolocations{}
</style>
</head>
<body>
	<springForm:form method="POST" commandName="geoLocation" class="form-inline">
	<div class="container">
	  <h1>Find Address</h1>
	  <ul class="errorMessages"></ul>
	 		<div class="form-group">
				<label for="latitude">Latitude:</label>
				<springForm:input data-toggle="tooltip" data-placement="bottom" id="latitude" path="latitude" title="Latitude" /> 
			</div>
			<div class="form-group">
				<label for="longitude">Longitude:</label>
				<springForm:input data-toggle="tooltip" data-placement="bottom" id="longitude" path="longitude" title="Longitude" /> 
			</div>
			<input type="button" id="geoSubmit"	class="btn btn-primary" value="Find Address"></input>
			<div id="information" class="success"></div>
			<div class="table-responsive" id="table-responsive"></div>
  			<div id="allgeolocations" style="display:none">
  				<a href="/spring/allgeolocations">Searched Locations URI</a>
  			</div>
	</div>
	</springForm:form>
</body>
</html>