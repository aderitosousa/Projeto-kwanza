package com.bitgest.kwanza.UI.Meta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitgest.kwanza.R
import com.bitgest.kwanza.UI.Planejamento.PlanejamentoFragmentDirections
import com.bitgest.kwanza.UI.adapter.Meta
import com.bitgest.kwanza.UI.adapter.MetaAdapter
import com.bitgest.kwanza.UI.adapter.Plano
import com.bitgest.kwanza.UI.adapter.PlanoAdapter
import com.bitgest.kwanza.UI.autenticação.FirebaseHelper
import com.bitgest.kwanza.databinding.FragmentMetaBinding
import com.bitgest.kwanza.databinding.FragmentReceitaBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MetaFragment : Fragment() {
    private var _binding: FragmentMetaBinding? = null
    private val binding get() = _binding!!

    private lateinit var metaAdapter: MetaAdapter
    private val metaLista = mutableListOf<Meta>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMetaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiClick()

        pegarMeta()
    }

    private fun initiClick(){
        binding.floatingActionButton.setOnClickListener {
            val action =HomeMetaFragmentDirections
                .actionHomeMetaFragmentToNovaMetaFragment(null)
            findNavController().navigate(action)
        }
    }

    private fun pegarMeta(){
        FirebaseHelper
            .getDatabase()
            .child("meta")
            .child(FirebaseHelper.getIdUser() ?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){

                        metaLista.clear()
                        var abnt = 0.00
                        for (snap in snapshot.children){
                            val meta = snap.getValue(Meta::class.java) as Meta

                            if (meta.status == 0){
                                metaLista.add(meta)

                                abnt += meta.valorMeta.toFloat()
                                binding.res.text = abnt.toString()
                            }

                        }

                        metaLista.reverse()
                        initAdapter()
                    }
                    metaVazia()
                    binding.progressBar.isVisible = false
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Aconteceu algum erro inesperado", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun metaVazia() {
        binding.text.text = if (metaLista.isEmpty()) {
            "Nenhuma meta cadastrada"
        }else{
            ""
        }
    }

    private fun initAdapter() {
        binding.rclist.layoutManager =  LinearLayoutManager (requireContext())
        binding.rclist.setHasFixedSize(true)
        metaAdapter = MetaAdapter(requireContext(), metaLista) { meta, int ->

            optionSelect(meta, int)

        }

        binding.rclist.adapter =metaAdapter
    }

    private  fun optionSelect(meta: Meta, select: Int){
        when (select){
            MetaAdapter.SELECT_REMOVE -> {
                apagarMeta(meta)
            }

            MetaAdapter.SELECT_EDIT -> {
                val action = HomeMetaFragmentDirections
                    .actionHomeMetaFragmentToNovaMetaFragment(meta)
                findNavController().navigate(action)
            }

            MetaAdapter.SELECT_Done -> {
                meta.status = 1
                actualizarMeta(meta)
            }
        }
    }

    private fun actualizarMeta(meta: Meta){
        FirebaseHelper
            .getDatabase()
            .child("meta")
            .child(FirebaseHelper.getIdUser() ?:"")
            .child(meta.id)
            .setValue(meta)
            .addOnCompleteListener { meta ->
                if (meta.isSuccessful){
                    Toast.makeText(requireContext(),
                        " Acabou de cumprir uma meta",
                        Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                binding.progressBar.isVisible = false
                Toast.makeText(requireContext(), "Ocorreu algum erro inesperado", Toast.LENGTH_SHORT).show()
            }
    }

    private fun apagarMeta(meta: Meta){
        FirebaseHelper
            .getDatabase()
            .child("meta")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(meta.id)
            .removeValue()

        metaLista.remove(meta)
        metaAdapter.notifyDataSetChanged()
    }


}