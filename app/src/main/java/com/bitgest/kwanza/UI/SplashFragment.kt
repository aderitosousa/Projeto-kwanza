package com.bitgest.kwanza.UI

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bitgest.kwanza.R
import com.bitgest.kwanza.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashFragment : Fragment() {

    //implementando o viewbinding no fragamento
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // programando tempo de execução da tela splash
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     Handler(Looper.getMainLooper()).postDelayed(this::checkAuth,3000)

    }

    //verificando se o usuario esta logado
    private fun checkAuth(){
        auth = Firebase.auth
       if (auth.currentUser == null) {
           findNavController().navigate(R.id.action_splashFragment_to_navigation)

       } else{
           findNavController().navigate(R.id.action_splashFragment_to_financasFragment)

       }

    }

}
