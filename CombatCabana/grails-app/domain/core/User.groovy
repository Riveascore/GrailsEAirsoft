package core
import com.grailsrocks.authentication.*

class User {

	String login
	String password
	String email
	String favoriteDancer
	int status = AuthenticationService.STATUS_NEW

	//Rest of these are optional
	//BUT, required if you want to buy/sell


	//TODO handle security question(s)
	//TODO captcha for preventing bot signups
	//TODO handle passwords
	//TODO handle 'free disposable email' services like mailinator


	//TODO Allow everything besides [username, email, password] to be blank
	//UP UNTIL you want to sell/buy, then you have to enter
	//The process of adding a credit card, shipping point etc would insert
	//Address etc for you

	static constraints = {
		favoriteDancer nullable:true
//		login size: 3..20
//		email(email:true, nullable: true, blank: false)
		/*
		password password: true, validator: { value, obj ->
			int numberOfWords = value.collect {
				it.charAt(0).digit || it.charAt(0).letter ? it: ' '
			}.join('').tokenize(' ').size()
			if(numberOfWords < 3) {
				//returns custom error msg with this key in message.properties
				return 'className.propertyName.passphrase.validation.msg'
			}
		}
		*/
	}

	boolean equals(user){
		if(user.getClass() != User){
			return false
		}
		if(user.id != this.id){
			return false
		}
		true
	}

	String toString(){
		"${login}"
	}
}
