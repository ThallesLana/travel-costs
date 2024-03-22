package com.thalleslana.travelcosts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.thalleslana.travelcosts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // use lateinit para que a variavel só seja cobrada após o OnCreate da Activity
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding Conecta a interface a logica.
        // inflate expande a interface e chama os atributos da mesma
        binding = ActivityMainBinding.inflate(layoutInflater);

        setContentView(binding.root)

        // evento de clique
        binding.buttonCalc.setOnClickListener(this)

        binding.editPrice.addTextChangedListener(MoneyTextWatcher(binding.editPrice))
    }

    override fun onClick(view: View) {
        if (view.id == R.id.button_calc) {
            calculate()
        }
    }

    private fun validateDistance(): Boolean {
        return if(binding.editDistance.text.toString().isBlank()){
            binding.editDistance.error = getString(R.string.validate_distance)
            Toast.makeText(applicationContext, R.string.validate_distance, Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
    private fun validatePrice(): Boolean {
        return if(binding.editPrice.text.toString().isBlank()){
            binding.editPrice.error = getString(R.string.validate_price)
            Toast.makeText(applicationContext, R.string.validate_price, Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
    private fun validateAutonomy(): Boolean {
        return if(binding.editAutonomy.text.toString().isBlank() || binding.editAutonomy.text.toString().toFloat() == 0f){
            binding.editAutonomy.error = getString(R.string.validate_autonomy)
            Toast.makeText(applicationContext, R.string.validate_autonomy, Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun isValid(): Boolean {
        return validateDistance() && validatePrice() && validateAutonomy()
    }
    private fun calculate() {
        if(isValid()){
            val distance = binding.editDistance.text.toString().toFloat();
            val price = binding.editPrice.text.toString().toFloat();
            val autonomy = binding.editAutonomy.text.toString().toFloat();

            val total = (distance * price) / autonomy;

            // formatação de numero para decimal com 2 casas.
            binding.textCosts.text = "R$ ${"%.2f".format(total)}";

            // Toast Notify
            Toast.makeText(applicationContext, "Calculo Feito com Sucesso!", Toast.LENGTH_SHORT).show();
        }

    }
}