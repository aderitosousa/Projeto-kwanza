package com.bitgest.kwanza.UI.Planejamento

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitgest.kwanza.UI.adapter.*
import com.bitgest.kwanza.UI.autenticação.FirebaseHelper
import com.bitgest.kwanza.databinding.FragmentReceitaBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ReceitaFragment : Fragment() {
    private var _binding: FragmentReceitaBinding? = null
    private val binding get() = _binding!!

    private lateinit var planoAdapter: PlanoAdapter

    private val planoLista = mutableListOf<Plano>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceitaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiClick()

        pegarPlano()
    }

    private fun initiClick(){
        binding.floatingActionButton.setOnClickListener {
            val action = PlanejamentoFragmentDirections
                .actionPlanejamentoFragmentToNovoPlano(null)
            findNavController().navigate(action)
        }
    }

    private fun pegarPlano(){
        FirebaseHelper
            .getDatabase()
            .child("plano")
            .child(FirebaseHelper.getIdUser() ?: "")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){

                        planoLista.clear()
                        var abnt = 0.00
                        for (snap in snapshot.children){
                            val plano = snap.getValue(Plano::class.java) as Plano

                            if (plano.status == 0){
                                planoLista.add(plano)

                                abnt += plano.valortransacao.toFloat()
                                binding.res.text = abnt.toString()
                            }

                        }

                        planoLista.reverse()
                        initAdapter()
                    }
                    planoVazio()
                    binding.progressBar.isVisible = false
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Aconteceu algum erro inesperado", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun planoVazio() {
        binding.text.text = if (planoLista.isEmpty()) {
            "Nenhuma receita cadastrada"
        }else{
            ""
        }
    }

    private fun initAdapter() {
        binding.rcvlist.layoutManager =  LinearLayoutManager (requireContext())
        binding.rcvlist.setHasFixedSize(true)
        planoAdapter = PlanoAdapter(requireContext(), planoLista) { plano, int ->

            optionSelect(plano, int)

        }

        binding.rcvlist.adapter =planoAdapter
    }

    private  fun optionSelect(plano: Plano, select: Int){
        when (select){
            PlanoAdapter.SELECT_REMOVE -> {
                apagarPlano(plano)
            }

             PlanoAdapter.SELECT_EDIT -> {
                val action = PlanejamentoFragmentDirections
                    .actionPlanejamentoFragmentToNovoPlano(plano)
            findNavController().navigate(action)
            }

            PlanoAdapter.SELECT_Done -> {
                plano.status = 2
                actualizarPlano(plano)
            }
        }
    }

    private fun actualizarPlano(plano: Plano){
        FirebaseHelper
            .getDatabase()
            .child("plano")
            .child(FirebaseHelper.getIdUser() ?:"")
            .child(plano.id)
            .setValue(plano)
            .addOnCompleteListener { plano ->
                if (plano.isSuccessful){
                        Toast.makeText(requireContext(),
                            "Plano adicionado ao extrato",
                            Toast.LENGTH_SHORT).show()
                    }
            }.addOnFailureListener{
                binding.progressBar.isVisible = false
                Toast.makeText(requireContext(), "Ocorreu algum erro inesperado", Toast.LENGTH_SHORT).show()
            }
    }

    private fun apagarPlano(plano: Plano){
        FirebaseHelper
            .getDatabase()
            .child("plano")
            .child(FirebaseHelper.getIdUser() ?: "")
            .child(plano.id)
            .removeValue()

        planoLista.remove(plano)
        planoAdapter.notifyDataSetChanged()
    }



}

