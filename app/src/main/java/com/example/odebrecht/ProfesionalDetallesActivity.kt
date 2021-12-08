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

class ProfesionalDetallesActivity : AppCompatActivity() {

    private lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesional_detalles)

        var fullname = findViewById<TextView>(R.id.P_fullname)
        var licenciado = findViewById<TextView>(R.id.P_licenced)
        var descripcion = findViewById<TextView>(R.id.P_descripcion)
        var address = findViewById<TextView>(R.id.P_address)
        var email = findViewById<TextView>(R.id.P_email)
        var cell = findViewById<TextView>(R.id.P_celular)

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
        loadDetalles(fullname,licenciado,descripcion,address,email,cell)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun loadDetalles(fn: TextView, lic: TextView, des: TextView, dir: TextView, em: TextView, cel: TextView) {
        val prof: String? = intent.getStringExtra("profesionalID")
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.1.39/Odebrecht-movil-DB/buscarProf.php?id="+prof
        val stringRequest = StringRequest(Request.Method.GET, url, { response->
            val jsonArray = JSONArray(response)
            val jsonObject = JSONObject(jsonArray.getString(0))
            val name: String = jsonObject.get("first_name").toString()
            val lastname: String = jsonObject.get("last_name").toString()
            val licencia: String = jsonObject.get("licensed").toString()
            val descripcion: String = jsonObject.get("description").toString()
            val address: String = jsonObject.get("address").toString()
            val email: String = jsonObject.get("email").toString()
            val cell: String = jsonObject.get("local_number").toString()
            fn.text = name+" "+lastname
            lic.text = licencia
            des.text = descripcion
            dir.text = address
            em.text = email
            cel.text = cell
        }, { error -> })
        queue.add(stringRequest)
    }
}