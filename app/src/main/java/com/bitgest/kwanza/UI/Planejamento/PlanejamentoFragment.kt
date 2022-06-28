package com.bitgest.kwanza.UI.Planejamento

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bitgest.kwanza.UI.adapter.ViewPageAdapter
import com.bitgest.kwanza.UI.adapter.initToolbar
import com.bitgest.kwanza.databinding.FragmentPlanejamentoBinding
import com.google.android.material.tabs.TabLayoutMediator

class PlanejamentoFragment : Fragment() {

    private var _binding: FragmentPlanejamentoBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater:
        LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanejamentoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configTablayout()
    }

    private fun configTablayout(){
        val adapter = ViewPageAdapter (requireActivity())
        binding.viewPager.adapter = adapter

        adapter.addFragment(ReceitaFragment(), "Receita")
        adapter.addFragment(DespesaFragment(), "Despesas")
        adapter.addFragment(ExtratoFragment(), "Extratos")

        binding.viewPager.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) {tab, position ->
            tab.text = adapter.getTitle(
                position
            )
        }.attach()
    }
}