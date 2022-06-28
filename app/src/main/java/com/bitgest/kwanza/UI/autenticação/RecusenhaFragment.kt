package com.bitgest.kwanza.UI.autenticação

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bitgest.kwanza.UI.adapter.initToolbar
import com.bitgest.kwanza.databinding.FragmentRecusenhaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RecusenhaFragment : Fragment() {

    //implementando o viewbinding no fragamento

    private var _binding: FragmentRecusenhaBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecusenhaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initClick()
        auth = Firebase.auth
    }
    private  fun initClick(){

        binding.btnRecuperar.setOnClickListener{validardados()}
        }

    private  fun  validardados() {
        val email = binding.edtEmail.text.toString().trim()

        if (email.isNotEmpty()) {
                binding.progresBar.isVisible = true

            RecupUs(email)

            } else {
                Toast.makeText(requireContext(), "informe seu email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun RecupUs(email: String){

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Acabamos de enviar um e-mail", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(requireContext(),FirebaseHelper.validarErro(task.exception?.message ?: ""), Toast.LENGTH_SHORT).show()
                }
                binding.progresBar.isVisible = false
            }

    }
}