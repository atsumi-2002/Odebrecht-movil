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

class PerfilActivity : AppCompatActivity() {

    private lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
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
        loadPerfil()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadPerfil() {
        val perfilNombre = findViewById<TextView>(R.id.perfilNombre)
        val perfilApellido = findViewById<TextView>(R.id.perfilApellido)
        val perfilEmail = findViewById<TextView>(R.id.perfilEmail)
        val perfilPhone = findViewById<TextView>(R.id.perfilTelefono)
        val perfilBirth = findViewById<TextView>(R.id.perfilNacimiento)
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.39/Odebrecht-movil-DB/perfil.php"
        val stringRequest = StringRequest(Request.Method.GET, url, { response->
            val jsonArray = JSONArray(response)
            val jsonObject = JSONObject(jsonArray.getString(0))
            perfilNombre.text = jsonObject.get("first_name").toString()
            perfilApellido.text = jsonObject.get("last_name").toString()
            perfilEmail.text = jsonObject.get("email").toString()
            perfilPhone.text = jsonObject.get("cellphone").toString()
            perfilBirth.text = jsonObject.get("birth").toString()

        }, { error -> })
        queue.add(stringRequest)
    }
}