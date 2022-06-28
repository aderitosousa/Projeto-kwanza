package com.bitgest.kwanza.UI.Planejamento

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
import com.bitgest.kwanza.UI.adapter.Plano
import com.bitgest.kwanza.UI.adapter.initToolbar
import com.bitgest.kwanza.UI.autenticação.FirebaseHelper
import com.bitgest.kwanza.databinding.FragmentNovoPlanoBinding

class NovoPlano : Fragment() {

    private val args: NovoPlanoArgs by navArgs()

    private var _binding: FragmentNovoPlanoBinding? = null
    private val binding get() = _binding!!

    private lateinit var plano: Plano
    private  var novoPlano: Boolean = true
    private var statusPlano: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNovoPlanoBinding.inflate(inflater, container, false)
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

        args.plano.let {
            if (it != null) {
                plano = it

                configPlano ()
            }
        }
    }

    private  fun configPlano () {

        novoPlano = false

        statusPlano = plano.status
        binding.addPlano.text = "Editando plano"

        binding.Categoria.setText(plano.categoria)
        binding.NomeConta.setText(plano.nomeconta)
        binding.nomeTransacao.setText(plano.nometransacao)
        binding.valorTransacao.setText(plano.valortransacao)
        binding.Data.setText(plano.datatransacao)
        setStatus ()
    }

    private fun setStatus () {
      binding.radioGroup.check(
          when (plano.status) {
              0 -> {
                  R.id.receita

              }
              1-> {
                  R.id.despesa

              }
              else -> {
                  R.id.extrato

              }
          }
      )
    }

    private  fun  initListeners() {
        binding.btnCriar.setOnClickListener {validarPlano()}

        binding.radioGroup.setOnCheckedChangeListener{ _, id ->
            statusPlano = when (id) {
                R.id.receita -> 0
                R.id.despesa -> 1
                else -> 2
            }
        }
    }

    private fun  validarPlano(){
        val nome_Conta = binding.NomeConta.text.toString().trim()
        val  nome_Transacao = binding.nomeTransacao.text.toString().trim()
        val valorTransacao = binding.valorTransacao.text.toString().trim()
        val Data  = binding.Data.text.toString().trim()
        val categotia = binding.Categoria.text.toString().trim()

        if (categotia.isNotEmpty()) {

            if (nome_Conta.isNotEmpty()){

            if (nome_Transacao.isNotEmpty()){

                if (valorTransacao.isNotEmpty()){

                    if (Data.isNotEmpty()){


                            binding.progressBar.isVisible = true

                            if (novoPlano) plano = Plano()
                            plano.nomeconta = nome_Conta
                            plano.nometransacao = nome_Transacao
                            plano.valortransacao = valorTransacao
                            plano.datatransacao = Data
                            plano.status = statusPlano
                            plano.categoria = categotia

                            SalvarPlano()
                }else{
                        Toast.makeText(requireContext(), "Insira a data", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), "Insira quantos kwanza", Toast.LENGTH_SHORT).show()
                }
                }else{
                Toast.makeText(requireContext(), "Insira o nome da transação", Toast.LENGTH_SHORT).show()
            }

            }else{
                Toast.makeText(requireContext(), "Insira o nome da conta", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(requireContext(), "Insira a categoria", Toast.LENGTH_SHORT).show()
        }

    }

    private fun SalvarPlano(){

        FirebaseHelper
            .getDatabase()
            .child("plano")
            .child(FirebaseHelper.getIdUser() ?:"")
            .child(plano.id)
            .setValue(plano)
            .addOnCompleteListener { plano ->
                if (plano.isSuccessful){
                    if (novoPlano){//nova plano
                        binding.progressBar.isVisible = false
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Plano adicionado", Toast.LENGTH_SHORT).show()

                    }else{//editando plano
                        binding.progressBar.isVisible = false
                        findNavController().popBackStack()
                        Toast.makeText(requireContext(), "Plano atualizado com sucesso", Toast.LENGTH_SHORT).show()
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