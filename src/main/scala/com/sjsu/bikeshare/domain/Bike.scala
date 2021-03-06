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

@Entity
class Bike {
 
val dateFormat:DateFormat   = new SimpleDateFormat("yyyy-MM-dd");
//dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"))

@BeanProperty
var bikeId: String =_

@BeanProperty
var userEmail: String =_

@BeanProperty
var requesterEmail: String =_

@BeanProperty
@NotEmpty
var address: String = _

@BeanProperty
var accessories: String = _


@BeanProperty
@NotEmpty
var latitude: String = _

@BeanProperty
@NotEmpty
var longitude: String = _

@BeanProperty
@NotEmpty
var description: String = _

@BeanProperty
@NotEmpty
var bikeType: String = _

@BeanProperty
var image: String = _

@BeanProperty
@NotEmpty
var bikeCode: String = _

@BeanProperty
@NotEmpty
var fromDate: String = _//dateFormat.format(new Date)

@BeanProperty
@NotEmpty
var toDate: String = _//dateFormat.format(new Date)

@BeanProperty
//var bikeReviews: ArrayList[Review]  = _
var bikeReviews: String = _

}
