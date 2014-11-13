package com.sjsu.bikeshare

import org.springframework.boot.SpringApplication

/**
 * This object bootstraps Spring Boot web application.
 * Via Gradle: gradle bootRun
 *
 * @author komal
 * @since 1.0
 */

//@EnableAutoConfiguration
//@ComponentScan
object HelloWebApplication {

	def main(args: Array[String]) {
	
	   SpringApplication.run(classOf[HelloConfig]);
	}
}