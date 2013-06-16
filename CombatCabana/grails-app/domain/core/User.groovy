package core

class User {

	//These 4 required for authentication
	String login
	String password
	String email
	int status
	
	//Rest of these are optional
	//BUT, required if you want to buy/sell
	String firstName, lastName, username
	String address, phoneNumber
	Date dateJoined
	
	//TODO handle security question(s)
	//TODO captcha for preventing bot signups
	//TODO handle passwords
	//TODO handle 'free disposable email' services like mailinator
	
	static hasOne = [cart:Cart, store:Store, statistic:Statistic]
	
	static hasMany = [payments:Payment, notification:Notification]

	//TODO Allow everything besides [username, email, password] to be blank
	//UP UNTIL you want to sell/buy, then you have to enter
	//The process of adding a credit card, shipping point etc would insert 
	//Address etc for you
		
    static constraints = {
		// Might be covered by authenticationUserClass?
		username blank: false, size: 1..20, unique: true
		email nulable: false, email: true
		//password.collect { it.charAt(0).digit || it.charAt(0).letter ? it : ' ' }.join('').tokenize(' ').size() min:3
    }
}
