package com.sjsu.bikeshare.domain

/**
 * @author Swathi.m
 *
 */

import javax.validation.constraints.NotNull
import scala.beans._;
import javax.persistence.Entity
@Entity
class User {
  
@BeanProperty
  var userId :String = _ ;  
  
 @NotNull
  @BeanProperty
  var email:String =_ ;
 
 
 @NotNull
  @BeanProperty
    var firstName:String =_ 
   

 @NotNull
  @BeanProperty
  var lastName:String =_ ;
   
 
 @NotNull
  @BeanProperty
  var password:String =_ ;

 
  @BeanProperty
 var contactNo:String =_ ;
   
  @BeanProperty
 var twiliocode:Int =_ ;
 
 @BeanProperty
var created_at:String=_;


 @BeanProperty
var updated_at:String=_;

}