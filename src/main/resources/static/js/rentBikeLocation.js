/*<![CDATA[*/
var geocoder;
 var map;
var bikes;
var bikeDesc=[];
function bike(BikeId,Address,Accessories,BikeCode,Description,Owner,Latitude, Longitude) {
	  this.bikeId= BikeId;
	  this.addr = Address;
	  this.accessories = Accessories;
	  this.bikeCode=BikeCode;
	  this.description=Description;
	  this.owner=Owner;
	  this.lat = Latitude;
	  this.lng = Longitude;
	   };

	  	   
function initialize() {
	  geocoder = new google.maps.Geocoder();
	  infowindow = new google.maps.InfoWindow();
	  var bikeList= document.getElementById('bikeList').value;
	  var userLat= document.getElementById('userLatitude').value;
	  var userLong= document.getElementById('userLongitude').value;
	  alert ("bikeList "+bikeList);
	  alert ("userLat "+userLat);
	  alert ("userLong "+userLong);
	  alert("hiddenFomDate "+document.getElementById('userFromDate').value);
	  alert("hiddenToDate "+document.getElementById('userToDate').value);
	// alert("Length "+bikeLoc.length)
	 var json = JSON.parse(bikeList);
	 
	
	for(var i=0; i < json.length; i++)
	{
	    var bikeId = json[i].bikeId;
	    var bikeAddress = json[i].address;
	    var accessories = json[i].accesories;
	    var bikeCode = json[i].bikeCode;
	    var description = json[i].description;
	    var owner = json[i].userEmail;
	    var latitude=json[i].loc.coordinates[1];
	    var longitude=json[i].loc.coordinates[0];
	    alert("bikeId "+bikeId+" bikeAddress "+bikeAddress+" accessories "+accessories+" bikeCode "+bikeCode);
	    alert(" description "+description+" owner "+owner+" latitude "+latitude+" longitude "+longitude);
	    bikeDesc[i]=new bike(bikeId,bikeAddress,accessories,bikeCode,description,owner,latitude,longitude);
	}
	var userLatLng = new google.maps.LatLng(userLat, userLong);
	var mapOptions = {
	    zoom: 12,
	    center: userLatLng
	  };
	 map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
	 
	 for (var i = 0; i < bikeDesc.length; i++) {	
		 createMarker(bikeDesc[i]);}
}

function createMarker(staion) {
    
		//var image = '/images/bicycle_parking1.png';
		//alert (place.lat+" "+place.lng)
		var placeLoc = new google.maps.LatLng(staion.lat,staion.lng);
		var marker = new google.maps.Marker({
	    map: map,
	    position: placeLoc,
		title :"Bike Location"
	  });
	  
	 google.maps.event.addListener(marker, 'mouseover', function (i) {
	    infowindow.setContent(staion.description+'\n'+staion.addr);
	    infowindow.open(map, this);
	  });
	 
	 google.maps.event.addListener(marker, 'click', function (i) {
		 document.getElementById("address").value = staion.addr;
	    	document.getElementById("bikeId").value = staion.bikeId;
	    	 	document.getElementById("dateRange").value = 
	      document.getElementById('userFromDate').value+" to "+document.getElementById('userToDate').value;
		  });
}

google.maps.event.addDomListener(window, 'load', initialize);
/*]]>*/

