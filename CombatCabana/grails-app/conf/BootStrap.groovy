import org.springframework.web.context.support.WebApplicationContextUtils

class BootStrap {

	def authenticationService
	Map failOnError = [failOnError: true]
	
	def init = { servletContext ->

		
//		def riveascore = new core.User(
//			login: 'Riveascore', 
//			password:authenticationService.encodePassword('freedom'), 
//			email: 'riveascore@yahoo.com', 
//			status:AuthenticationService.STATUS_VALID).save(failOnError)
		
	}
	
	def destroy = {
	}
	
}
