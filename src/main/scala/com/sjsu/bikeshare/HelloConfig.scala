package com.sjsu.bikeshare

import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.{RequestMapping, RestController};
import org.springframework.stereotype.Controller;



/**
 * This config class will trigger Spring @annotation scanning and auto configure Spring context.
 *
 * @author saung
 * @since 1.0
 */

@Controller
@Configuration
@EnableAutoConfiguration
@ComponentScan
class HelloConfig {

  @RequestMapping(Array("/bikeshare"))
  def home():String = { 
 return "bikeshare"
 
  }
  
   @RequestMapping(Array("/SignUp"))
  def signUp():String = { 
 return "SignUp"
 
  }

   @RequestMapping(Array("/login"))
  def login():String = { 
 return "login"
  }
}