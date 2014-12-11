package com.sjsu.bikeshare.domain


import javax.validation.constraints.NotNull
import scala.beans._;
import org.hibernate.validator.constraints.NotEmpty
import javax.persistence.Entity
@Entity
class UserLogin {
  
  @BeanProperty
  @NotEmpty
   var email:String =_ 
   
   @BeanProperty
   var name:String =_ 
 
  @BeanProperty
  @NotEmpty
   var password:String =_ 
   
}