package andrade.ignacio.tiptime

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    @SuppressLint("StringFormatInvalid", "UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Aplica padding para las barras del sistema (estado, navegación)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los elementos de la UI
        val costOfServiceEditText: EditText = findViewById(R.id.cost_of_service)
//        println("el costo es:  $costOfServiceEditText")
        val tipOptionsRadioGroup: RadioGroup = findViewById(R.id.tip_options)
        val roundUpSwitch: Switch = findViewById(R.id.round_up_switch)
        val tipResultTextView: TextView = findViewById(R.id.tip_result)
        val calculateButton: Button = findViewById(R.id.calculate_button)

        // Establece el valor inicial de tipResultTextView a 0.00
        tipResultTextView.text = getString(R.string.tip_amount, 0.00)

        // Configura la acción del botón para calcular la propina
        calculateButton.setOnClickListener {
            val cost = costOfServiceEditText.text.toString().toDoubleOrNull()
//            println("el costo es:  $cost")
            if (cost == null || cost == 0.0) {
                tipResultTextView.text = getString(R.string.tip_amount, 0.0)
                return@setOnClickListener
            }

            // Determina el porcentaje de propina basado en la selección del usuario
            val tipPercentage = when (tipOptionsRadioGroup.checkedRadioButtonId) {
                R.id.option_twenty_percent -> 0.20
                R.id.option_eighteen_percent -> 0.18
                R.id.option_fifteen_percent -> 0.15
                else -> 0.0
            }

            // Calcula la propina
            var tip = cost * tipPercentage
//            println("El tip es $tip")

            // Redondea la propina si está habilitada la opción de redondeo
            if (roundUpSwitch.isChecked) {
                tip = ceil(tip)
            }

            // Muestra el resultado en el TextView
            tipResultTextView.text = getString(R.string.tip_amount, tip)
            println("El resultado es: $tip")
        }
    }
}
