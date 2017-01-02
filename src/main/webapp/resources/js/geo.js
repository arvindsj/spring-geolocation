$(document).ready(function($) {
	renderTable(tableData);
	
	$('[data-toggle="tooltip"]').tooltip();
	$("#geoSubmit").click(function() {
		$('.errorMessages').html('');
		$('#information').html('');
		var latitude = $.trim($('#latitude').val());
		var longitude = $.trim($('#longitude').val());
		$.ajax({
			type : "POST",
			url : "/spring/geolocation",
			data : "latitude="+ latitude + "&longitude=" + longitude,
			datatype : "application/json",
			success : function(response) {
				if (response != '') {
					var returnedData = JSON.parse(response);
					// Check this error condition - incorrect key case
					var errorList = returnedData['errors'];
					if (errorList != null || typeof errorList !== 'undefined') {
						$.each(errorList,function() {
							$('.errorMessages').append('<li>'+ this+ '</li>');
							})
					} else {
						var length = returnedData.length;
						var newAddress = returnedData[length-1];
						var address = newAddress['address'].streetAddress;
						if(address == null || typeof address == 'undefined') {
							$("#information").html('Address Not found for the coordinates['+latitude+', '+longitude+'] provided');
						} else {
							$("#information").html('Address is :: '+address);
							renderTable(returnedData);
						}
						$('#latitude').val('');
						$('#longitude').val('');
					}
				} else {
					$("#information").html('Address Not found for the coordinates['+latitude+', '+longitude+'] provided');
				}
			},
			error : function(e) {
				alert('Server Error. Request Cannot be completed');
				renderTable(tableData);
			}
		});
	});
	
	function renderTable(tableData) {
		if(typeof tableData != 'undefined' && tableData.length > 0) {
			$('#allgeolocations').show();
			var html = '<table class="table table-bordered table-striped table-hover"><thead><tr> <th>GeoLocation</th><th>Address</th><th>Time Searched</th></tr><tbody>';
			for (var i = tableData.length-1; i >= 0; --i) {
			    html += '<tr>';
			    html += '<td>' + tableData[i].latitude +','+tableData[i].longitude+ '</td>';
			    html += '<td>' + tableData[i]['address'].streetAddress + '</td>';
			    html += '<td>' + tableData[i].searchedDateTimestamp + '</td>';
			    html += "</tr>";
			}
			html += '</tbody></table>';
			$('#table-responsive').html(html);
			}
	}
	
});