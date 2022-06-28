package com.bitgest.kwanza.UI.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bitgest.kwanza.databinding.MetaAdapterBinding

class MetaAdapter (
    private val context: Context,
    private val  MetaLista: List<Meta>,
    val seleMeta: (Meta, Int) -> Unit
    ) : RecyclerView.Adapter<MetaAdapter.MyViewHolder>() {

        companion object {
        val SELECT_EDIT: Int = 1
        val SELECT_REMOVE: Int = 2
        val SELECT_Done: Int = 3
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            return MyViewHolder(
                MetaAdapterBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            val Meta = MetaLista[position]

            holder.binding.NomeMeta.text = Meta.nomeMeta
            holder.binding.ValorMeta.text = Meta.valorMeta
            holder.binding.MesMeta.text = Meta.meses
            holder.binding.InMeta.text = Meta.iniMeta
            holder.binding.FimMeta.text = Meta.fimMeta
            holder.binding.media.text = Meta.mediaMeta

            holder.binding.remove.setOnClickListener { seleMeta(Meta, SELECT_REMOVE) }
            holder.binding.done.setOnClickListener { seleMeta(Meta,SELECT_Done) }
            holder.binding.edit.setOnClickListener { seleMeta(Meta, SELECT_EDIT) }

            when (Meta.status) {
                1 -> {
                    holder.binding.edit.isVisible = false
                    holder.binding.done.isVisible = false
                    holder.binding.textVew22.isVisible = false
                    holder.binding.textiw22.isVisible = false
                    holder.binding.MesMeta.isVisible = false
                    holder.binding.textView25.isVisible = false
                    holder.binding.media.isVisible = false
                    holder.binding.textView23.text = "Total ecozomizado"
                }
            }
        }

        override fun getItemCount() = MetaLista.size
        inner class MyViewHolder(val binding: MetaAdapterBinding) :
            RecyclerView.ViewHolder(binding.root)
}