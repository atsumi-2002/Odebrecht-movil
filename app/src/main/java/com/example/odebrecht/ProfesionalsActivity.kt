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
import com.example.odebrecht.adapter.PsicologoAdapter
import com.example.odebrecht.adapter.PsiquiatraAdapter
import com.example.odebrecht.model.Profesional
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONObject

class ProfesionalsActivity : AppCompatActivity() {

    private lateinit var toggle : ActionBarDrawerToggle
    var psicologos = mutableListOf<Profesional>()
    var displaypsicologos = mutableListOf<Profesional>()
    var psiquiatras = mutableListOf<Profesional>()
    var displaypsiquiatras = mutableListOf<Profesional>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesionals)
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
        loadPsicologos()
        loadPsiquiatras()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadPsicologos() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.39/Odebrecht-movil-DB/listarPsicologos.php"
        val stringRequest = StringRequest(Request.Method.GET, url, { response->
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()){
                val jsonObject = JSONObject(jsonArray.getString(i))
                var id = jsonObject.get("id").toString().toLong()
                var address = jsonObject.get("address").toString()
                var description = jsonObject.get("description").toString()
                var email = jsonObject.get("email").toString()
                var first_name = jsonObject.get("first_name").toString()
                var image = jsonObject.get("image").toString()
                var last_name = jsonObject.get("last_name").toString()
                var licensed = jsonObject.get("licensed").toString()
                var local_number = jsonObject.get("local_number").toString().toInt()
                var type = jsonObject.get("type").toString()
                psicologos.add(Profesional(id,address,description,email,first_name,image,last_name,licensed, local_number, type))
            }

            displaypsicologos.addAll(psicologos)

            val psicologoList : RecyclerView = findViewById(R.id.psicologosList)
            psicologoList.layoutManager = LinearLayoutManager(this)
            val adapter = PsicologoAdapter(displaypsicologos,this)
            psicologoList.adapter = adapter
        }, { error -> })
        queue.add(stringRequest)
    }
    fun loadPsiquiatras() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.39/Odebrecht-movil-DB/listarPsiquiatras.php"
        val stringRequest = StringRequest(Request.Method.GET, url, { response->
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()){
                val jsonObject = JSONObject(jsonArray.getString(i))
                var id = jsonObject.get("id").toString().toLong()
                var address = jsonObject.get("address").toString()
                var description = jsonObject.get("description").toString()
                var email = jsonObject.get("email").toString()
                var first_name = jsonObject.get("first_name").toString()
                var image = jsonObject.get("image").toString()
                var last_name = jsonObject.get("last_name").toString()
                var licensed = jsonObject.get("licensed").toString()
                var local_number = jsonObject.get("local_number").toString().toInt()
                var type = jsonObject.get("type").toString()
                psiquiatras.add(Profesional(id,address,description,email,first_name,image,last_name,licensed, local_number, type))
            }

            displaypsiquiatras.addAll(psiquiatras)

            val psiquiatraList : RecyclerView = findViewById(R.id.psiquiatrasList)
            psiquiatraList.layoutManager = LinearLayoutManager(this)
            val adapter = PsiquiatraAdapter(displaypsiquiatras,this)
            psiquiatraList.adapter = adapter
        }, { error -> })
        queue.add(stringRequest)
    }
}