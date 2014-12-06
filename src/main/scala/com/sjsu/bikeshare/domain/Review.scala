package com.sjsu.bikeshare.domain

/**
 * @author kiranmaiye.m 
 *
 */



import javax.validation.constraints.NotNull
import scala.beans._;
import javax.persistence.Entity
@Entity
class Review {
  
  @NotNull
  @BeanProperty
  var userEmail: String =_
 
  @BeanProperty
  var bikeId: String =_
 
  @NotNull
  @BeanProperty
  var comment:String =_ 

  override def toString(): String = "{ " + userEmail + " : " + comment + "} "   
}
