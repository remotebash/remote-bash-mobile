package com.remotebash.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.remotebash.CommandLine
import com.remotebash.R
import com.remotebash.model.ComputadorModel
import com.remotebash.model.LaboratorioModel
import kotlinx.android.synthetic.main.activity_layout_cardview_pc.view.*

class ComputadorListAdapter(
    private val listPc: LaboratorioModel,
    private val context: Context
) : RecyclerView.Adapter<ComputadorListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pcList = listPc.computerSet!![position]
        val idPc = listPc.id
        holder.bindView(pcList, idPc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.activity_layout_cardview_pc, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPc.computerSet!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivSistema = itemView.computador_sistema
        val ivMac = itemView.computador_mac
        val ivImagem = itemView.ivComputadores

        fun bindView(pcs: ComputadorModel, id: Long?) {
            ivSistema.text = pcs.operationalSystem
            ivMac.text = pcs.macaddress
            itemView.setOnClickListener {
                it.apply {
                    val comandoPc = Intent(context, CommandLine::class.java)
                    comandoPc.putExtra("idPc", pcs.id)
                    startActivity(context, comandoPc, null)
                }
            }
        }

    }

}





