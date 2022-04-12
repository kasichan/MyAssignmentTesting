
import com.google.firebase.database.Exclude

data class Order(
    var name:String? = null,
    var buyerEmail:String? = null,
    var senderAddress:String? = null,
    var receiverAddress:String? = null,
    var totalPrice:Double?=0.0,
    var paymentMethod: String?=null,
    var buyQuantity: Int?= null,
    var sellerEmail: String?=null,


)