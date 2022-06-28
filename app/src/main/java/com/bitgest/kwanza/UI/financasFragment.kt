package com.bitgest.kwanza.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bitgest.kwanza.R
import com.bitgest.kwanza.UI.adapter.initToolbar
import com.bitgest.kwanza.databinding.FragmentFinancasBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class financasFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private var _binding: FragmentFinancasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinancasBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)
        initiClick()

        auth = Firebase.auth
    }

    private fun initiClick(){

        binding.te7.setOnClickListener { findNavController().navigate(R.id.action_financa_to_upsFragment)}

        binding.logout.setOnClickListener {logoutapp()}

        binding.floatingActionButton.setOnClickListener { findNavController().navigate(R.id.action_financa_to_homeMetaFragment) }

    }

    private fun  logoutapp(){
        auth.signOut()

        findNavController().navigate(R.id.action_global_navigation)
    }

}