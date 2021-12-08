package com.example.odebrecht.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.odebrecht.ConsultaDetallesActivity
import com.example.odebrecht.ProfesionalDetallesActivity
import com.example.odebrecht.R
import com.example.odebrecht.model.Profesional

class PsicologoAdapter(
    private val psicologos:List<Profesional>,
    val context: Context):
    RecyclerView.Adapter<PsicologoAdapter.PsicologoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PsicologoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_profesional, parent, false)
        return PsicologoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PsicologoViewHolder, position: Int) {
        holder.render(psicologos[position])
    }

    override fun getItemCount(): Int = psicologos.size

    inner class PsicologoViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val profesionalFullname = view.findViewById<TextView>(R.id.P_Fullname)
        private val profesionalLicencia = view.findViewById<TextView>(R.id.P_licencia)
        private val profesionalButton = view.findViewById<TextView>(R.id.P_button)
        fun render(profesional: Profesional) {
            profesionalFullname.text = "Dr(a) "+profesional.first_name+" "+profesional.last_name
            profesionalLicencia.text = profesional.licensed
            profesionalButton.setOnClickListener{
                var i: Intent = Intent(context, ProfesionalDetallesActivity::class.java)
                i.putExtra("profesionalID", profesional.id.toString())
                context.startActivity(i)
            }
        }
    }
}