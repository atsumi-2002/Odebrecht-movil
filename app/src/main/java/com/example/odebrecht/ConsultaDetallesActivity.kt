package com.example.odebrecht

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.navigation.NavigationView
import org.json.JSONArray
import org.json.JSONObject

class ConsultaDetallesActivity : AppCompatActivity() {

    private lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_detalles)

        var resultado = findViewById<TextView>(R.id.C_resultado)
        var fecha = findViewById<TextView>(R.id.C_fecha)
        var medico = findViewById<TextView>(R.id.C_medico)

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

        loadDetalles(resultado,fecha,medico)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun loadMedOfDetalles(med: TextView, medic: String) {
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
    fun loadDetalles(res: TextView, date: TextView,med: TextView) {
        val consulta: String? = intent.getStringExtra("consultaid")
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.39/Odebrecht-movil-DB/buscarConsulta.php?id="+consulta
        val stringRequest = StringRequest(Request.Method.GET, url, { response->
            val jsonArray = JSONArray(response)
            val jsonObject = JSONObject(jsonArray.getString(0))
            val result: String = jsonObject.get("resultado").toString()
            val fecha: String = jsonObject.get("fecha").toString()
            val medId: String = jsonObject.get("medico_id").toString()
            res.text = result
            date.text = fecha
            loadMedOfDetalles(med,medId)
        }, { error -> })
        queue.add(stringRequest)
    }
}