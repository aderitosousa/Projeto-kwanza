package com.bitgest.kwanza.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bitgest.kwanza.R
import com.bitgest.kwanza.UI.adapter.initToolbar
import com.bitgest.kwanza.databinding.FragmentPlanoBinding

class PlanoFragment : Fragment() {

    private var _binding: FragmentPlanoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      initClick()

      }

     private fun initClick() {
        binding.cacMargem.setOnClickListener { Margem() }
        binding.cacPonto.setOnClickListener { Ponto() }
        binding.cacLucra.setOnClickListener { Lucra() }
        binding.cacPeriodo.setOnClickListener { Periodo() }
        binding.logout.setOnClickListener {
             findNavController().navigate(R.id.action_planoFragment_to_explicaFragment)
         }

      }

    private fun Margem() {
        val val1 = binding.edtMargem1.text.toString().trim()
        val val2 = binding.edtMargem2.text.toString().trim()

        if (val1.isNotEmpty()){
            if (val2.isNotEmpty()){
                val val1 = binding.edtMargem1.text.toString().toFloat()
                val val2 = binding.edtMargem2.text.toString().toFloat()

                val tot = (val1 - val2)/val1
                binding.resMargem.setText(tot.toString())
            }else{
                Toast.makeText(requireContext(), "insira o valor dos custos variados", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "insira o valor da recita total", Toast.LENGTH_SHORT).show()
        }


    }

    private fun Ponto() {
        val val1 = binding.edtPonto1.text.toString().trim()
        val val2 = binding.edtPonto2.text.toString().trim()

        if (val1.isNotEmpty()){
            if (val2.isNotEmpty()){
                val val1 = binding.edtPonto1.text.toString().toFloat()
                val val2 = binding.edtPonto2.text.toString().toFloat()

                val tot = (val1 / val2)
                binding.resPonto.setText(tot.toString())
            }else{
                Toast.makeText(requireContext(), "insira o valor da margem de contribuição", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "insira o valor dos custos fixos", Toast.LENGTH_SHORT).show()
        }

    }

    private fun Lucra() {
        val val1 = binding.edtLucra1.text.toString().trim()
        val val2 = binding.edtLucra2.text.toString().trim()

        if (val1.isNotEmpty()){
            if (val2.isNotEmpty()){
                val val1 = binding.edtLucra1.text.toString().toInt()
                val val2 = binding.edtLucra2.text.toString().toInt()

                val tot = (val1 - val2)
                binding.resLucra.setText(tot.toString())
            }else{
                Toast.makeText(requireContext(), "insira o valor do total de custos", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "insira o valor do total de receita", Toast.LENGTH_SHORT).show()
        }
    }

    private fun Periodo() {
        val val1 = binding.edtPerido1.text.toString().trim()
        val val2 = binding.edtPerido2.text.toString().trim()

        if (val1.isNotEmpty()){
            if (val2.isNotEmpty()){
                val val1 = binding.edtPerido1.text.toString().toInt()
                val val2 = binding.edtPerido2.text.toString().toInt()

                val tot = (val1 / val2)
                binding.resPeriodo.setText(tot.toString())

            }else{
                Toast.makeText(requireContext(), "insira o valor da lucratividade", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "insira o valor do total de investimento", Toast.LENGTH_SHORT).show()
        }
    }


}
