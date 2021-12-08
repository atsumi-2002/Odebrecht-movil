package com.example.odebrecht

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class ResultadoActivity : AppCompatActivity() {

    private lateinit var toggle : ActionBarDrawerToggle
    private lateinit var guardarButton: Button

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)

        val nivelEmo = findViewById<TextView>(R.id.resultado)
        val medic = findViewById<TextView>(R.id.medico)
        guardarButton = findViewById(R.id.guardarButton)

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
        loadResultados(nivelEmo,medic)
        guardarButton.setOnClickListener {
            val result: String? = intent.getStringExtra("resultado")
            val medic: String? = intent.getStringExtra("med")
            val sdf = SimpleDateFormat("yyyy-MM-dd ")
            val fecha = sdf.format(Date())
            val user: String? = "1"
            val queue = Volley.newRequestQueue(this)
            val url = "http://192.168.1.39/Odebrecht-movil-DB/guardarConsulta.php"
            val stringRequest = object: StringRequest(Request.Method.POST, url, { response->
                Toast.makeText(applicationContext,"Consulta guardada",Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
            }, { error -> }){
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String,String>()
                    params.put("fecha",fecha.toString())
                    if (medic != null) { params.put("medico_id",medic) }
                    if (result != null) { params.put("resultado",result) }
                    if (user != null) { params.put("usuario_id",user) }
                    return params
                }
            }
            queue.add(stringRequest)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadResultados(nv: TextView, med: TextView) {
        val result: String? = intent.getStringExtra("resultado")
        val medic: String? = intent.getStringExtra("med")
        nv.text = result
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.39/Odebrecht-movil-DB/buscarProf.php?id="+medic
        val stringRequest = StringRequest(Request.Method.GET, url, { response->
            val jsonArray = JSONArray(response)
            val jsonObject = JSONObject(jsonArray.getString(0))
            val medicNom: String = jsonObject.get("first_name").toString()
            val medicaApe: String = jsonObject.get("last_name").toString()
            med.text = "Dr(a) $medicNom $medicaApe"
        }, { error -> })
        queue.add(stringRequest)
    }
}