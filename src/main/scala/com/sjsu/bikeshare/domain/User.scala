package com.sjsu.bikeshare.domain

/**
 * @author Swathi.m
 *
 */

import scala.beans._
import javax.persistence.Entity
import org.hibernate.validator.constraints.NotEmpty
@Entity
class User {
  
@BeanProperty
  var userId :String = _ ;  

 @BeanProperty
 @NotEmpty
  var email:String =_ ;
 
 @BeanProperty
 @NotEmpty
  var firstName:String =_ 
  
@BeanProperty
@NotEmpty
  var lastName:String =_ ;

 @BeanProperty
  @NotEmpty
  var password:String =_ ;

 
 @BeanProperty
 @NotEmpty
 var contactNo:String =_ ;
   
 @BeanProperty
 var twiliocode:Int =_ ;
 
 @BeanProperty
var created_at:String=_;


 @BeanProperty
var updated_at:String=_;

}