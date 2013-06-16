package core

class Product {

	static belongsTo = [store: Store]
	static hasOne = [auction: Auction]
	
    static constraints = {
    }
}
