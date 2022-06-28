package com.bitgest.kwanza.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bitgest.kwanza.R
import com.bitgest.kwanza.UI.adapter.initToolbar
import com.bitgest.kwanza.databinding.FragmentSolucoesBinding

class solucoesFragment : Fragment() {

    private var _binding: FragmentSolucoesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSolucoesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCliks()
    }

    private fun initCliks() {
       binding.emprestimo.setOnClickListener { findNavController().navigate(R.id.action_solucoes_to_upsFragment) }
       binding.seguro.setOnClickListener { findNavController().navigate(R.id.action_solucoes_to_upsFragment) }
       binding.investi.setOnClickListener { findNavController().navigate(R.id.action_solucoes_to_upsFragment) }
       binding.maissobre.setOnClickListener { findNavController().navigate(R.id.action_solucoes_to_upsFragment) }
    }
}