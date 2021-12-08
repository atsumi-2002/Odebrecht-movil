package com.example.odebrecht

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.odebrecht.adapter.ConsultaAdapter
import com.example.odebrecht.model.Consulta
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONObject

class ConsultasActivity : AppCompatActivity() {

    private lateinit var toggle : ActionBarDrawerToggle
    var consultas = mutableListOf<Consulta>()
    var displayconsultas = mutableListOf<Consulta>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultas)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.inicioButton -> startActivity(Intent(this, MainActivity::class.java))
                R.id.cuestionarioButton -> startActivity(Intent(this, CuestionarioActivity::class.java))
                R.id.profesionalsButton -> startActivity(Intent(this, ProfesionalsActivity::class.java))
                R.id.consultasButton -> startActivity(Intent(this, ConsultasActivity::class.java))
                R.id.citasButton -> startActivity(Intent(this, CitasActivity::class.java))
                R.id.perfilButton -> startActivity(Intent(this, PerfilActivity::class.java))
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
        loadConsultas()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadConsultas() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.39/Odebrecht-movil-DB/listarConsultas.php"
        val stringRequest = StringRequest(Request.Method.GET, url, { response->
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()){
                val jsonObject = JSONObject(jsonArray.getString(i))
                var id = jsonObject.get("id").toString().toLong()
                var date = jsonObject.get("fecha").toString()
                var medic = jsonObject.get("medico_id").toString().toLong()
                var result = jsonObject.get("resultado").toString()
                var user = jsonObject.get("usuario_id").toString().toLong()
                consultas.add(Consulta(id,date,medic,result,user))
            }

            displayconsultas.addAll(consultas)

            val consultaList : RecyclerView = findViewById(R.id.consultaList)
            consultaList.layoutManager = LinearLayoutManager(this)
            val adapter = ConsultaAdapter(displayconsultas,this)
            consultaList.adapter = adapter
        }, { error -> })
        queue.add(stringRequest)
    }
}

