package com.sjsu.bikeshare.domain


import javax.validation.constraints.NotNull
import scala.beans._;
import javax.persistence.Entity
@Entity
class UserLogin {
  
  @NotNull
  @BeanProperty
   var email:String =_ 
 
 
 @NotNull
  @BeanProperty
   var password:String =_ 
   
}