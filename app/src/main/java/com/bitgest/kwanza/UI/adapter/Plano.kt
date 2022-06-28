package com.bitgest.kwanza.UI.adapter

import android.os.Parcelable
import com.bitgest.kwanza.UI.autenticação.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plano(
    var id: String = "",
    var nomeconta : String = "",
    var nometransacao: String = "",
    var valortransacao: String = "",
    var datatransacao: String = "",
    var categoria: String = "",
    var status: Int = 0
) : Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}
