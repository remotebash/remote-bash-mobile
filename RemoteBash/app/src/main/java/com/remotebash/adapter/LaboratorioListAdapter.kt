package com.remotebash.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.remotebash.Computadores
import com.remotebash.R
import com.remotebash.model.LaboratorioModel
import kotlinx.android.synthetic.main.activity_layout_cardview_lab.view.*

class LaboratorioListAdapter(
    private val listLab: List<LaboratorioModel>,
    private val context: Context
) : RecyclerView.Adapter<LaboratorioListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val labList = listLab[position]
        holder.bindView(labList)

        holder.itemView.setOnClickListener {
            it.setOnClickListener {
                Toast.makeText(it.context, "Laboratorio ${position}", Toast.LENGTH_SHORT).show()
                val computador = Intent(it.context, Computadores::class.java)
                computador.putExtra("idLaboratorio", listLab[position].id.toString())
                startActivity(it.context, computador, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_layout_cardview_lab, parent, false)
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
            ivLaboratorio.text = labModel.name
            ivDescricao.text = labModel.description

        }

    }

}
