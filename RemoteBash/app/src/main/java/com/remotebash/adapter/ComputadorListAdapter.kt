package com.remotebash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.remotebash.R
import com.remotebash.model.ComputadorModel
import kotlinx.android.synthetic.main.activity_layout_cardview_lab.view.*


class ComputadorListAdapter(
    private val listPc: List<ComputadorModel>,
    private val context: Context
) : RecyclerView.Adapter<ComputadorListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pcList = listPc[position]
        holder.bindView(pcList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_layout_cardview_lab, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPc.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivLaboratorio = itemView.laboratorio_item_nome
        val ivDescricao = itemView.descricao_item_nome

        fun bindView(labModel: ComputadorModel) {
            ivLaboratorio.text = "nomePc"
            ivDescricao.text = "descPc"

        }

    }

}

