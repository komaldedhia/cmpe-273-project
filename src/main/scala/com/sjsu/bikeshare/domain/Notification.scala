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
class Notification {
 
val dateFormat:DateFormat   = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"))


@BeanProperty
var ownerId: String =_

@BeanProperty
var requestorId: String =_

@BeanProperty
@NotEmpty
var status: String = _

@BeanProperty
@NotEmpty
var noteId: Int = _


@BeanProperty
@NotEmpty
var bikeId: String = _

@BeanProperty
@NotEmpty
var fromDate: String = dateFormat.format(new Date)

@BeanProperty
@NotEmpty
var toDate: String = dateFormat.format(new Date)

}