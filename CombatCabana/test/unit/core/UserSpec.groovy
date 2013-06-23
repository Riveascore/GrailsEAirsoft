package core

import security.User;
import spock.lang.Specification

//@TestFor(User)
class UserSpec extends Specification {

	def setupSpec() {}
	def setup() {
		mockDomain(User)
	}
	def "only need login, password, and email when signing up"(){
		//Testing so if we don't enter cart, address etc info, it will be fine :)
		
		when: 
		def chris = new User(
			login: 'Riveascore', 
			password: 'this is fun', 
			email: 'riveascore@yahoo.com'
			)
		
		then:
		chris.validate()
	}
	def cleanup() {}
	
	def "password strength test"(){
		when:
		def userGoodPassword = new User(
			login: 'GoodPassword',
			password: 'this is fun',
			email: 'liquidanubis3@yahoo.com'
			)
		def userBadPassword = new User(
			login: 'BadPassword',
			password: 'this is',
			email: 'riveascore@yahoo.com'
			)
		
		def goodWithSymbols = new User(
			login: 'goodWithSymbols',
			password: 'hell0 thi$ i$ w0rd$',
			email: 'riveascor@yahoo.com'
			)
		
		then:
		userGoodPassword.validate()
		!userBadPassword.validate()
		goodWithSymbols.validate()
	}
	
	def "prevent duplicate logins and emails"(){
		//Testing so if we don't enter cart, address etc info, it will be fine :)
		
		when:
		//TODO new tests based on authentication
		
		//Will use the following form:
		/*
		assert new AuthenticationUser(
			login: 'Riveascore',
			password: 'this is fun',
			email: 'riveascore@yahoo.com',
			status:AuthenticationService.STATUS_VALID).save()
		*/	
		def chris = new User(
			login: 'Riveascore',
			password: 'this is fun',
			email: 'riveascore@yahoo.com'
			)
		def steve = new User(
			login: 'Riveascore',
			password: 'this is fun',
			email: 'liquidanubis3@yahoo.com'
			)
		def frank = new User(
			login: 'FrankerZ',
			password: 'this is fun',
			email: 'riveascore@yahoo.com'
			)
		
		then:
		chris.validate()
		!steve.validate()
		!frank.validate()
	}
	
	def cleanupSpec() {}
}