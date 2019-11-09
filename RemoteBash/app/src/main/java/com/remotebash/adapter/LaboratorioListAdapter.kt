package com.remotebash.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.remotebash.R
import com.remotebash.model.LaboratorioModel
import kotlinx.android.synthetic.main.activity_layout_cardview.view.*

class LaboratorioListAdapter(private val listLab: List<LaboratorioModel>,
                             private val context: Context
) : RecyclerView.Adapter<LaboratorioListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val labList = listLab[position]
        holder.ivLaboratorio.text = labList.laboratorio
        holder.ivDescricao.text = labList.descricao
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_layout_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listLab.size
    }

    // class visivel apenas pela LaboratorioListAdapter
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivLaboratorio = itemView.laboratorio_item_nome
        val ivDescricao = itemView.descricao_item_nome

        fun bindView(labModel: LaboratorioModel) {
            ivLaboratorio.text = labModel.laboratorio
            ivDescricao.text = labModel.descricao
        }

    }

}
