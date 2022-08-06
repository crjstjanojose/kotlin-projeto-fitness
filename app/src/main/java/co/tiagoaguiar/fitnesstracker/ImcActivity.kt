package co.tiagoaguiar.fitnesstracker

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ImcActivity : AppCompatActivity() {

    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)

        val btnSend: Button = findViewById(R.id.btn_Imc_send)
        btnSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()

            val result = calculateImc(weight, height)
            val imcResponseID = imcResponse(result)

            // DIALOG
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, result))
                .setMessage(imcResponseID)
                .setPositiveButton(android.R.string.ok) { dialog, which ->

                }
                .create()
                .show()

            // TRATAMENTO FECHAMENTO TECLADO
            val serviceInput = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            serviceInput.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }
    }

    @StringRes
    private fun imcResponse(imc: Double): Int {
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_very_low_weight
            imc < 25 -> R.string.normal
            imc < 30 -> R.string.imc_high_weight
            imc < 35 -> R.string.imc_so_high_weight
            imc < 40 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }
    }

    private fun calculateImc(weight: Int, height: Int): Double {
        return (weight / ((height / 100.0) * (height / 100.0)))
    }

    // Validação do FORM
    // Não aceitar valores nulos
    // Não inicar com zero
    private fun validate(): Boolean {
        return (editWeight.text.toString().isNotEmpty() &&
                editHeight.text.toString().isNotEmpty() &&
                !editWeight.text.toString().startsWith("0") &&
                !editHeight.text.toString().startsWith("0"))
    }
}