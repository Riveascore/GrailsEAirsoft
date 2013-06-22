import org.springframework.web.context.support.WebApplicationContextUtils
import com.grailsrocks.authentication.*

import com.grailsrocks.authentication.AuthenticationService;

class BootStrap {

	def authenticationService
	Map failOnError = [failOnError: true]
	
	def init = { servletContext ->

		def appCtx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
		
		// register custom auth events
		appCtx.authenticationService.events.onNewUserObject = { loginId ->
			// create and return your user instance
			return new core.User(login: loginId)
		}
		appCtx.authenticationService.events.onValidatePassword = { password ->
			int numberOfWords = password.collect {
				it.charAt(0).digit || it.charAt(0).letter ? it: ' '
			}.join('').tokenize(' ').size()
			return (numberOfWords >= 3)
		}
		
		def riveascore = new core.User(
			login: 'Riveascore', 
			password:authenticationService.encodePassword('freedom'), 
			email: 'riveascore@yahoo.com', 
			status:AuthenticationService.STATUS_VALID).save(failOnError)
			
		def megaman = new core.User(
			login: 'Megaman',
			password:authenticationService.encodePassword('this is fun'),
			email: 'riveascor@yahoo.com',
			status:AuthenticationService.STATUS_VALID).save(failOnError)
			
		def jarvan = new core.User(
			login: 'Jarvan',
			password:authenticationService.encodePassword('freedom'),
			email: 'liquidanubis3@yahoo.com',
			status:AuthenticationService.STATUS_VALID).save(failOnError)
		
	}
	
	def destroy = {
	}
	
}
