/*<![CDATA[*/
$(document).ready(function(){
	document.getElementById("enableButton").disabled=true;
});
var geocoder;
var map;
var bikes;
var bikeDesc = [];
function bike(BikeId, Address, Accessories, BikeCode, Description, Owner,
		Latitude, Longitude) {
	this.bikeId = BikeId;
	this.addr = Address;
	this.accessories = Accessories;
	this.bikeCode = BikeCode;
	this.description = Description;
	this.owner = Owner;
	this.lat = Latitude;
	this.lng = Longitude;
};

function initialize() {
	geocoder = new google.maps.Geocoder();
	infowindow = new google.maps.InfoWindow();
	var bikeList = document.getElementById('bikeList').value;
	var userLat = document.getElementById('userLatitude').value;
	var userLong = document.getElementById('userLongitude').value;
	//alert("bikeList " + bikeList);
	//alert("userLat " + userLat);
	//alert("userLong " + userLong);
	//alert("hiddenFomDate " + document.getElementById('userFromDate').value);
	//alert("hiddenToDate " + document.getElementById('userToDate').value);
	// alert("Length "+bikeLoc.length)
	var json = JSON.parse(bikeList);

	for (var i = 0; i < json.length; i++) {
		var bikeId = json[i].bikeId;
		var bikeAddress = json[i].address;
		var accessories = json[i].accessories;
		var bikeCode = json[i].bikeCode;
		var description = json[i].description;
		var owner = json[i].userEmail;
		var latitude = json[i].loc.coordinates[1];
		var longitude = json[i].loc.coordinates[0];

		//alert("bikeId " + bikeId + " bikeAddress " + bikeAddress+ " accessories " + accessories + " bikeCode " + bikeCode);
		//alert(" description " + description + " owner " + owner + " latitude "+ latitude + " longitude " + longitude);

		bikeDesc[i] = new bike(bikeId, bikeAddress, accessories, bikeCode,
				description, owner, latitude, longitude);

	}
	var userLatLng = new google.maps.LatLng(userLat, userLong);
	var mapOptions = {
		zoom : 12,
		center : userLatLng
	};
	
	map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	
	var userMarker = new google.maps.Marker({
		map : map,
		position : userLatLng,
		title : "You Are Here"
		});
	
	google.maps.event.addListener(userMarker, 'mouseover', function(i) {
		infowindow.setContent( "You Are Here");
		infowindow.open(map, this);
	});

	for (var i = 0; i < bikeDesc.length; i++) {

		createMarker(bikeDesc[i]);
	}
}

function createMarker(staion) {

	//var image ="http://www.google.com/mapfiles/ms/micons/cycling.png";
	var image ="https://spinlister-belvedere-assets.spinlister.com/spinlister/map/map-marker-bike-0b12dab4d6debbdb5507f9fe5a6afe0a.png"
	//image.src ="http://www.google.com/mapfiles/ms/micons/cycling.png"

	var placeLoc = new google.maps.LatLng(staion.lat, staion.lng);
	var marker = new google.maps.Marker({
		map : map,
		position : placeLoc,
		title : "Bike Location",
		icon: image
	});

	google.maps.event.addListener(marker, 'mouseover', function(i) {
		infowindow.setContent(staion.description + ' Located at ' + staion.addr);
		infowindow.open(map, this);
	});

	google.maps.event.addListener(marker, 'click', function(i) {

		document.getElementById("address").innerHTML = staion.addr;
		
		document.getElementById("bikeCode").innerHTML = staion.bikeCode;
		document.getElementById("description").innerHTML = staion.description;
		document.getElementById("bikeId").value = staion.bikeId;
		document.getElementById("userEmail").value = staion.owner;
		document.getElementById("dateRange").innerHTML = document.getElementById('userFromDate').value
				+ " to " + document.getElementById('userToDate').value;
		document.getElementById("accessories").innerHTML = staion.accessories;
		document.getElementById("enableButton").disabled=false;
	});
}

google.maps.event.addDomListener(window, 'load', initialize);
/*]]>*/

