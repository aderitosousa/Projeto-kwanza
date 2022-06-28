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
import com.bitgest.kwanza.UI.adapter.Meta
import com.bitgest.kwanza.UI.adapter.MetaAdapter
import com.bitgest.kwanza.UI.adapter.Plano
import com.bitgest.kwanza.UI.adapter.PlanoAdapter
import com.bitgest.kwanza.UI.autenticação.FirebaseHelper
import com.bitgest.kwanza.databinding.FragmentListaMetaBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ListaMetaFragment : Fragment() {
    private var _binding: FragmentListaMetaBinding? = null
    private val binding get() = _binding!!

    private lateinit var metaAdapter: MetaAdapter
    private val metaLista = mutableListOf<Meta>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaMetaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pegarMeta()
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

                            if (meta.status == 1){
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
            "Nenhuma receita cadastrada"
        }else{
            ""
        }
    }

    private fun initAdapter() {
        binding.rcvlist.layoutManager =  LinearLayoutManager (requireContext())
        binding.rcvlist.setHasFixedSize(true)
        metaAdapter = MetaAdapter(requireContext(), metaLista) { meta, int ->

            optionSelect(meta, int)

        }

        binding.rcvlist.adapter =metaAdapter
    }

    private  fun optionSelect(meta: Meta, select: Int){
        when (select){
            MetaAdapter.SELECT_REMOVE -> {
                apagarMeta(meta)
            }
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