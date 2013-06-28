package security

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
		username blank: false, unique: true
		password blank: false
//		password(blank: false, nullable: false, size:5..20, validator: {password, obj ->
//			def confirmPassword = obj.properties['confirmPassword']
//			if(confirmPassword == null) return true // skip matching password validation (only important when setting/resetting pass)
//			confirmPassword == password ? true : ['invalid.matchingpasswords']
//		})
		password(blank: false, validator: {password, obj ->
			def confirmPassword = obj.properties['confirmPassword']
			if(confirmPassword == null) return true
			confirmPassword.equals(password) ? true : ['invalid.matchingpasswords']
		})
	}
			

	static mapping = {
		password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}
	
	String getUpperCaseName() { username.toUpperCase() }

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	static transients = ['confirmPassword']
	//^I believe this prevents it from being stored in the database
}
