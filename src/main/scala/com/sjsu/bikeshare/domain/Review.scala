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
 
 
  @NotNull
  @BeanProperty
  var comments:String =_ 

  override def toString(): String = "{ " + userEmail + " : " + comments + "} "   
}
