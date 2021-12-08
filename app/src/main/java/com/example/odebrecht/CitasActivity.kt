package com.example.odebrecht

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.odebrecht.adapter.CitaAdapter
import com.example.odebrecht.adapter.ConsultaAdapter
import com.example.odebrecht.model.Cita
import com.example.odebrecht.model.Consulta
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONObject

class CitasActivity : AppCompatActivity() {

    private lateinit var toggle : ActionBarDrawerToggle
    var citas = mutableListOf<Cita>()
    var displaycitas = mutableListOf<Cita>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
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
        loadCitas()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun loadCitas() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.39/Odebrecht-movil-DB/listarCitas.php"
        val stringRequest = StringRequest(Request.Method.GET, url, { response->
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()){
                val jsonObject = JSONObject(jsonArray.getString(i))
                var id = jsonObject.get("id").toString().toLong()
                var date = jsonObject.get("fecha").toString()
                var medic = jsonObject.get("user_id").toString().toLong()
                var result = jsonObject.get("user_message").toString()
                var user = jsonObject.get("professional_id").toString().toLong()
                citas.add(Cita(id,date,result,user,medic))
            }

            displaycitas.addAll(citas)

            val citasList : RecyclerView = findViewById(R.id.citaList)
            citasList.layoutManager = LinearLayoutManager(this)
            val adapter = CitaAdapter(displaycitas,this)
            citasList.adapter = adapter
        }, { error -> })
        queue.add(stringRequest)
    }
}