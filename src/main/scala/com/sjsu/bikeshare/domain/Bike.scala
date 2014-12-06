package com.sjsu.bikeshare.domain

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util._
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import org.springframework.boot.SpringApplication
import scala.beans._
import org.hibernate.validator.constraints.NotEmpty

import javax.validation.constraints.NotNull

@Entity
class Bike {
 
val dateFormat:DateFormat   = new SimpleDateFormat("yyyy-MM-dd")
//val dateFormat:DateFormat   = new SimpleDateFormat("MM-dd-yyyy");
//dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"))

@BeanProperty
var bikeId: String =_

@BeanProperty
var searchRange: String =_

@BeanProperty
var userEmail: String =_

@BeanProperty
var requesterEmail: String =_

//chnaged-shwetha
@BeanProperty
 @NotNull
var address: String = _

@BeanProperty
var accessories: String = _


@BeanProperty
//@NotEmpty
var latitude: String = _

//@Column(nullable = true)
@BeanProperty
//@NotEmpty
var longitude: String = _

@BeanProperty
var description: String = _

@BeanProperty
//@NotEmpty
var bikeType: String = _

@BeanProperty
var image: String = _

@BeanProperty
var bikeCode: String = _

@BeanProperty
//@NotEmpty
var fromDate: String = _//dateFormat.format(new Date)

@BeanProperty
//@NotEmpty
var toDate: String = _//dateFormat.format(new Date)

@BeanProperty
var bikeReviews: ArrayList[Review]  = _
//var bikeReviews: String = _

//TODOadded for bike Range
//@BeanProperty
//val bikeRange = _


}
