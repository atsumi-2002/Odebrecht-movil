package com.example.odebrecht

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class CuestionarioActivity : AppCompatActivity() {

    private lateinit var toggle : ActionBarDrawerToggle
    lateinit var radioButton: RadioButton
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuestionario)
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
        var radioGroup1: RadioGroup  = findViewById(R.id.q1)
        var radioGroup2: RadioGroup  = findViewById(R.id.q2)
        var radioGroup3: RadioGroup  = findViewById(R.id.q3)
        var radioGroup4: RadioGroup  = findViewById(R.id.q4)
        var radioGroup5: RadioGroup  = findViewById(R.id.q5)
        var radioGroup6: RadioGroup  = findViewById(R.id.q6)
        var radioGroup7: RadioGroup  = findViewById(R.id.q7)
        var radioGroup8: RadioGroup  = findViewById(R.id.q8)
        var radioGroup9: RadioGroup  = findViewById(R.id.q9)
        var radioGroup10: RadioGroup  = findViewById(R.id.q10)
        var radioGroup11: RadioGroup  = findViewById(R.id.q11)
        var radioGroup12: RadioGroup  = findViewById(R.id.q12)
        var radioGroup13: RadioGroup  = findViewById(R.id.q13)
        var radioGroup14: RadioGroup  = findViewById(R.id.q14)
        var radioGroup15: RadioGroup  = findViewById(R.id.q15)
        button = findViewById(R.id.cuestionarioButton)
        button.setOnClickListener {
            val r1 : Int= calculate(radioGroup1)
            val r2 : Int= calculate(radioGroup2)
            val r3 : Int= calculate(radioGroup3)
            val r4 : Int= calculate(radioGroup4)
            val r5 : Int= calculate(radioGroup5)
            val r6 : Int= calculate(radioGroup6)
            val r7 : Int= calculate(radioGroup7)
            val r8 : Int= calculate(radioGroup8)
            val r9 : Int= calculate(radioGroup9)
            val r10 : Int= calculate(radioGroup10)
            val r11 : Int= calculate(radioGroup11)
            val r12 : Int= calculate(radioGroup12)
            val r13 : Int= calculate(radioGroup13)
            val r14 : Int= calculate(radioGroup14)
            val r15 : Int= calculate(radioGroup15)
            val total = r1+r2+r3+r4+r5+r6+r7+r8+r9+r10+r11+r12+r13+r14+r15
            val resultado: String
            val med: Int
            if(total>=10) {
                resultado= "Muestras un nivel alto de inteligencia emocional"
                med = 1
            } else if (total in 5..9) {
                resultado = "Muestras un nivel medio de inteligencia emocional"
                med = 2
            } else {
                resultado = "Muestras un nivel bajo de inteligencia emocional"
                med = 3
            }
            val i: Intent = Intent(this, ResultadoActivity::class.java)
            i.putExtra("resultado",resultado)
            i.putExtra("med",med.toString())
            startActivity(i)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun calculate(radioGroup: RadioGroup): Int {
        var result: Int = 0
        val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
        radioButton = findViewById(intSelectButton)
        when(radioButton.text){
            "Siempre"-> result +=2
            "Con frecuencia"-> result +=1
            "A veces"-> result +=0
            "Rara vez"-> result -=1
            "Nunca"-> result -=2
        }
        return result
    }
}