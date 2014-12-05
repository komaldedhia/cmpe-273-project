
function codeAddress() {
	// alert("Insude cideAdress")
	var geocoder;
	var map;
	geocoder = new google.maps.Geocoder();
	var address = document.getElementById('location').value;
	geocoder
			.geocode(
					{
						'address' : address
					},
					function(results, status) {
						if (status == google.maps.GeocoderStatus.OK) {

							var latitude = results[0].geometry.location.lat();
							var longitude = results[0].geometry.location.lng();
							document.getElementById("latitude").value = latitude;
							document.getElementById("Longitude").value = longitude;

							alert("User Address "
									+ results[0].geometry.location)
							document.getElementById("VerifyError").value = "Address Verified";

						} else {
							alert('Geocode was not successful for the following reason: '
									+ status);
							// document.getElementById("VerifyError").innerHTML
							// = "Incorrect Address";
							document.getElementById("VerifyError").value = "Incorrect Address";
							
						}
					});
}
