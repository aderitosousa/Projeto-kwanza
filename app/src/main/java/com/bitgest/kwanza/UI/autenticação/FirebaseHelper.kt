package com.bitgest.kwanza.UI.autenticação

import com.bitgest.kwanza.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {

    companion object{

        //recuperando instancias do meu banco de dados do firebase
        fun  getDatabase() = FirebaseDatabase.getInstance().reference

        //recuperando instancias de autenticação do usuario
        private fun  getAuth() = FirebaseAuth.getInstance()

        //recuperar id do usuario logado o app
        fun  getIdUser() = getAuth().uid

        //verificando se o usario esta autenticado no app
       fun isAutenticated() = getAuth().currentUser != null

        fun  validarErro(erro: String): Int {
            return when {
                erro.contains("There is no user record corresponding to this identifier. The user may have been deleted.") -> {
                    R.string.account_not_registered_register_fragment
                }
                erro.contains("The email address is badly formatted.")-> {
                    R.string.invalid_email_register_fragment
                }
                erro.contains("The password is invalid or the user does not have a password.")->{
                    R.string.invalid_password_register_fragment
                }
                erro.contains("The email address is already in use by another account.")->{
                    R.string.email_in_use_register_fragmente
                }
                erro.contains("The given password is invalid. [ Password should be at least 6 characters ]")->{
                    R.string.strong_password_register_fragment
                }
                else -> {
                    R.string.Global_erro
                }
            }
        }

    }
}