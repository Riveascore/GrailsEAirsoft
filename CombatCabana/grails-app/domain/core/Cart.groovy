package core

class Cart {

	static belongsTo = [user: User]
	static hasMany = [products: Product]
	
    static constraints = {
    }
}
