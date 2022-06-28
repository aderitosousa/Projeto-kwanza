package com.bitgest.kwanza.UI.Meta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bitgest.kwanza.R
import com.bitgest.kwanza.UI.Planejamento.NovoPlanoArgs
import com.bitgest.kwanza.UI.adapter.Meta
import com.bitgest.kwanza.UI.adapter.Plano
import com.bitgest.kwanza.UI.adapter.initToolbar
import com.bitgest.kwanza.UI.autenticação.FirebaseHelper
import com.bitgest.kwanza.databinding.FragmentNovaMetaBinding

class NovaMetaFragment : Fragment() {

    private val args: NovaMetaFragmentArgs by navArgs()

    private var _binding: FragmentNovaMetaBinding? = null
    private val binding get() = _binding!!

    private lateinit var meta: Meta
    private  var novaMeta: Boolean = true
    private var statusMeta: Int = 0

    override fun onCreateView(
        inflater:
        LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovaMetaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar)

        initListeners()

        getArgs()

    }

    private fun getArgs() {

        args.meta.let {
            if (it != null) {
                meta = it

                configMeta ()
            }
        }
    }

    private fun configMeta () {

        novaMeta = false

        statusMeta = meta.status
        binding.addMeta.text = "Editando a meta"

        binding.NomeMeta.setText(meta.nomeMeta)
        binding.ValorMeta.setText(meta.valorMeta)
        binding.PeriodoMeta.setText(meta.meses)
        binding.InMeta.setText(meta.iniMeta)
        binding.FimMeta.setText(meta.fimMeta)
    }


    private  fun  initListeners() {
        binding.btnCriar.setOnClickListener {validarMeta()}


    }

    private fun  validarMeta(){
        val nomeMeta = binding.NomeMeta.text.toString().trim()
        val valorMeta = binding.ValorMeta.text.toString().trim()
        val perMeta = binding.PeriodoMeta.text.toString().trim()
        val inMeta = binding.InMeta.text.toString().trim()
        val fimMeta = binding.FimMeta.text.toString().trim()

        if (nomeMeta.isNotEmpty()) {

            if (valorMeta.isNotEmpty()){

                if (perMeta.isNotEmpty()){

                    if (inMeta.isNotEmpty()){

                        if (fimMeta.isNotEmpty()){


                            binding.progressBar.isVisible = true



                            val valor = valorMeta.toInt()
                            val valor2 = perMeta.toInt()

                            val abnt = (valor / valor2)

                            if (novaMeta) meta = Meta()
                            meta.nomeMeta = nomeMeta
                            meta.valorMeta = valorMeta
                            meta.meses = perMeta
                            meta.iniMeta = inMeta
                            meta.fimMeta = fimMeta
                            meta.status = statusMeta
                            meta.mediaMeta = abnt.toString()

                            SalvarMeta()

                        }else{
                            Toast.makeText(requireContext(), "Insira a data de fim", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(requireContext(), "Insira a data de inicio", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), "Insira quantos meses", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(), "Insira o valor total", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(requireContext(), "Insira o nome da meta", Toast.LENGTH_SHORT).show()
        }

    }

    private fun SalvarMeta(){

        FirebaseHelper
            .getDatabase()
            .child("meta")
            .child(FirebaseHelper.getIdUser() ?:"")
            .child(meta.id)
            .setValue(meta)
            .addOnCompleteListener { meta ->
                if (meta.isSuccessful){
                    if (novaMeta){//nova meta
                        binding.progressBar.isVisible = false
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Nova meta adicionada", Toast.LENGTH_SHORT).show()

                    }else{//editando meta
                        binding.progressBar.isVisible = false
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Meta atualizada com sucesso", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), "Ocooreu algum erro inesperado", Toast.LENGTH_SHORT).show()
                }

            }.addOnFailureListener{
                binding.progressBar.isVisible = false
                Toast.makeText(requireContext(), "Ocorreu algum erro inesperado", Toast.LENGTH_SHORT).show()
            }

    }

}