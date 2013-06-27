import org.springframework.web.context.support.WebApplicationContextUtils

class BootStrap {

//	def authenticationService
//	Map failOnError = [failOnError: true]
	def springSecurityService
	
	def init = { servletContext ->

		new security.User(username: 'megaman', enabled: true, password: 'this is fun').save(flush: true)
		new security.User(username: 'riveascore', enabled: true, password: 'this is fun').save(flush: true)
	}
	
	def destroy = {
	}
	
}
