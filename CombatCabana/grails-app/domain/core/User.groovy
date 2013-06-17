package core
import com.grailsrocks.authentication.*

class User {

	
	String login
	String password
	String email
	int status = AuthenticationService.STATUS_NEW
	//Rest of these are optional
	//BUT, required if you want to buy/sell

	String firstName 
	String lastName
	String address
	String phoneNumber
	
//	Date dateJoined
	
	//TODO handle security question(s)
	//TODO captcha for preventing bot signups
	//TODO handle passwords
	//TODO handle 'free disposable email' services like mailinator
	
	static hasOne = [cart:Cart, store:Store, statistic:Statistic]
	
	static hasMany = [payments:Payment, notifications:Notification]

	//TODO Allow everything besides [username, email, password] to be blank
	//UP UNTIL you want to sell/buy, then you have to enter
	//The process of adding a credit card, shipping point etc would insert 
	//Address etc for you
		
    static constraints = {
//		strongEnoughPassword()
		
		//Find a way to make this nicer
		cart nullable:true
		store nullable:true
		statistic nullable:true
		payments nullable:true
		notifications nullable:true
		firstName nullable:true
		lastName nullable:true
		address nullable:true
		phoneNumber nullable:true
		
		// Might be covered by authenticationUserClass?
		//login blank: false, size: 1..20, unique: true
		//email nullable: false, email: true
		//dateJoined nullable: true
		// min:3
    }
	
	boolean strongEnoughPassword(){
		int numberOfWords = password.collect { it.charAt(0).digit || it.charAt(0).letter ? it : ' ' }.join('').tokenize(' ').size()
		numberOfWords >= 3
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
}
