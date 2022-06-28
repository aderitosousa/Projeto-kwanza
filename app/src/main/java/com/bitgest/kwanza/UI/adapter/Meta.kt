package com.bitgest.kwanza.UI.adapter

import android.os.Parcelable
import com.bitgest.kwanza.UI.autenticação.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(
    var id: String = "",
    var nomeMeta : String = "",
    var valorMeta: String = "",
    var meses: String = "",
    var iniMeta: String = "",
    var fimMeta: String = "",
    var mediaMeta: String = "",
    var status: Int = 0
) : Parcelable {
    init {
        this.id = FirebaseHelper.getDatabase().push().key ?: ""
    }
}


