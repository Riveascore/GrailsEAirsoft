package security

import grails.plugins.springsecurity.SpringSecurityService;

class User {

	transient springSecurityService

	String username
	String password
	String confirmPassword
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static constraints = {
		username (blank: false, unique: true)
		password(
			validator: { myPassword, obj->
				def confirmPassword = obj.properties['confirmPassword']
				if(confirmPassword.equals(null)) return false
				if(confirmPassword != myPassword){
					return ['invalid.matchingpasswords']
				}
				return true
			})
	}
			

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodeConfirmedPassword()
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('confirmPassword')) {
			encodeConfirmedPassword()
		}
		if (isDirty('password')) {
			encodePassword()
		}
	}
	
	String getUpperCaseName() { username.toUpperCase() }

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	protected void encodeConfirmedPassword() {
		confirmPassword = springSecurityService.encodePassword(confirmPassword)
	}
}