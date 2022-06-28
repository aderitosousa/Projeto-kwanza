package com.bitgest.kwanza.UI.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bitgest.kwanza.databinding.PlanoAdapterBinding

class PlanoAdapter (
    private val context: Context,
    private val  PlanoLista: List<Plano>,
    val seleConta: (Plano, Int) -> Unit
) : RecyclerView.Adapter<PlanoAdapter.MyViewHolder>() {

    companion object {
        val SELECT_EDIT: Int = 1
        val SELECT_REMOVE: Int = 2
        val SELECT_Done: Int = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PlanoAdapterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val Plano = PlanoLista[position]

        holder.binding.NomeConta.text = Plano.nomeconta
        holder.binding.NomeTransacao.text = Plano.nometransacao
        holder.binding.valorTransacao.text = Plano.valortransacao
        holder.binding.DataTransacao.text = Plano.datatransacao
        holder.binding.Categoria.text = Plano.categoria

        holder.binding.cardAdapter.setOnClickListener { seleConta(Plano, SELECT_EDIT) }
        holder.binding.remove.setOnClickListener { seleConta(Plano, SELECT_REMOVE) }
        holder.binding.done.setOnClickListener { seleConta(Plano, SELECT_Done) }

        when (Plano.status) {
            2 -> {
                holder.binding.done.isVisible = false
            }
        }
    }

    override fun getItemCount() = PlanoLista.size
    inner class MyViewHolder(val binding: PlanoAdapterBinding) :
        RecyclerView.ViewHolder(binding.root)
}