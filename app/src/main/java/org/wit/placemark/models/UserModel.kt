import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(var id: Long = 0,
                     var firstName: String = "",
                     var lastName: String = "",
                     var email: String ="",
                     var phoneNumber: Long =0,
                     var address: String = "",
                     var iconImage: Uri = Uri.EMPTY,
                     var provider : Boolean ,
                     var appAdmin : Boolean ,
                     var ppsNumber: String = "") : Parcelable