package com.stahlt.tip_time

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.stahlt.tip_time.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etCostOfService.setOnKeyListener(View.OnKeyListener { view, i, _ -> handleKeyEvent(view, i) })
    }

    fun btCalculateOnClick(view: View) {
        val stringInTextField = binding.etCostOfService.text.toString()
        if (stringInTextField.isEmpty()) {
            binding.etCostOfService.error = "Cost of Service Must be Filled"
            return
        }
        val cost = stringInTextField.toDouble()

        val tipPercentage = when (binding.rgTipOption.checkedRadioButtonId) {
            binding.rbTwenty.id -> TIP_TWENTY
            binding.rbEighteen.id -> TIP_EIGHTEEN
            else -> TIP_FIFTEEN
        }

        var tip = cost * tipPercentage

        val roundUp = binding.sRoundUp.isChecked

        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tvTip.text = getString(R.string.tip_result, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                    as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
    companion object {
        const val TIP_TWENTY = 0.2
        const val TIP_EIGHTEEN = 0.18
        const val TIP_FIFTEEN = 0.15
    }
}