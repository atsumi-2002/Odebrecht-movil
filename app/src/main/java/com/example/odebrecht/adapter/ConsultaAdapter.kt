package com.example.odebrecht.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.odebrecht.*;
import com.example.odebrecht.model.Consulta


class ConsultaAdapter(
    private val consultas:List<Consulta>,
    val context: Context):
    RecyclerView.Adapter<ConsultaAdapter.ConsultaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_consulta, parent, false)
        return ConsultaViewHolder(view)
    }

    override fun getItemCount(): Int = consultas.size

    override fun onBindViewHolder(holder: ConsultaViewHolder, position: Int) {
        holder.render(consultas[position])
    }

    inner class ConsultaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val consultaId = view.findViewById<TextView>(R.id.C_id)
        private val consultaDate = view.findViewById<TextView>(R.id.C_date)
        private val consultaButton = view.findViewById<TextView>(R.id.C_button)

        fun render(consulta: Consulta) {
            consultaId.text = consulta.id.toString()
            consultaDate.text = consulta.fecha
            consultaButton.setOnClickListener{
                var i: Intent = Intent(context,ConsultaDetallesActivity::class.java)
                i.putExtra("consultaid", consulta.id.toString())
                context.startActivity(i)
            }
        }
    }
    }
