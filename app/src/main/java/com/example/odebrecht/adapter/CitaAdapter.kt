package com.example.odebrecht.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.odebrecht.ConsultaDetallesActivity
import com.example.odebrecht.R
import com.example.odebrecht.model.Cita
import com.example.odebrecht.model.Consulta

class CitaAdapter(
    private val citas:List<Cita>,
    val context: Context):
    RecyclerView.Adapter<CitaAdapter.CitaViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaAdapter.CitaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        holder.render(citas[position])
    }

    override fun getItemCount(): Int = citas.size

    inner class CitaViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val citaId = view.findViewById<TextView>(R.id.D_id)
        private val citaDate = view.findViewById<TextView>(R.id.D_date)
        private val citaProf = view.findViewById<TextView>(R.id.D_medic)

        fun render(cita: Cita) {
            citaId.text = cita.id.toString()
            citaDate.text = cita.fecha
            citaProf.text = cita.user_message
        }
    }
}