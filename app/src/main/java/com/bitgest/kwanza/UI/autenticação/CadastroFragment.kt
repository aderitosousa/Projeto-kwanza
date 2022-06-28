package com.bitgest.kwanza.UI.autenticação

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bitgest.kwanza.R
import com.bitgest.kwanza.UI.adapter.initToolbar
import com.bitgest.kwanza.databinding.FragmentCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CadastroFragment : Fragment(){
    //implementando o viewbinding no fragamento

    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)
        auth = Firebase.auth

        initClick()
    }

    //validar resgisto,verificação e controlo de criação de conta

    private fun initClick( ){
        binding.btnCriar.setOnClickListener {validardados()}

    }
    private  fun  validardados() {
        val email = binding.edtEmail.text.toString().trim()
        val senha = binding.edtSenha.text.toString().trim()

        if (email.isNotEmpty()) {
            if (senha.isNotEmpty()){

                binding.progresBar.isVisible = true

                registroUs(email, senha)

            }else{
                Toast.makeText(requireContext(), "informe a senha", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(requireContext(), "informe seu email", Toast.LENGTH_SHORT).show()
        }
    }

    //autenticando atraves do firebase, validação e criação
    private fun registroUs(email: String, senha: String){

        auth.createUserWithEmailAndPassword(email,senha)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_financasFragment)

                } else {
                    Toast.makeText(requireContext(),FirebaseHelper.validarErro(task.exception?.message ?: ""), Toast.LENGTH_SHORT).show()
                    binding.progresBar.isVisible = false

                }
            }

    }
}