package com.bitgest.kwanza.UI.Meta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bitgest.kwanza.R
import com.bitgest.kwanza.UI.Planejamento.DespesaFragment
import com.bitgest.kwanza.UI.Planejamento.ExtratoFragment
import com.bitgest.kwanza.UI.Planejamento.ReceitaFragment
import com.bitgest.kwanza.UI.adapter.ViewPageAdapter
import com.bitgest.kwanza.UI.adapter.initToolbar
import com.bitgest.kwanza.databinding.FragmentHomeMetaBinding
import com.bitgest.kwanza.databinding.FragmentPlanejamentoBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeMetaFragment : Fragment() {
    private var _binding: FragmentHomeMetaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater:
        LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =FragmentHomeMetaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configTablayout()

        initToolbar(binding.toolbar)
    }

    private fun configTablayout(){
        val adapter = ViewPageAdapter (requireActivity())
        binding.viewPager.adapter = adapter

        adapter.addFragment(MetaFragment(), "Metas atuais")
        adapter.addFragment(ListaMetaFragment(), "Metas Passadas")

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