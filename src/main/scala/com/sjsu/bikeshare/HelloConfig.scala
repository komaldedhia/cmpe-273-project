package com.sjsu.bikeshare

import org.springframework.context.annotation.Configuration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.{RequestMapping, RestController};



/**
 * This config class will trigger Spring @annotation scanning and auto configure Spring context.
 *
 * @author saung
 * @since 1.0
 */

@RestController
@Configuration
@EnableAutoConfiguration
@ComponentScan
class HelloConfig {
//@RequestMapping(Array("/"))
def home()  = { 
"Hello-World"
  
 
  }

}