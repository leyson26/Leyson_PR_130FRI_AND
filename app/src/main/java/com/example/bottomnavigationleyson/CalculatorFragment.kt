package com.example.bottomnavigationleyson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.bottomnavigationleyson.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private var currentInput: String = ""
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)

        // Set click listeners for buttons
        binding.btn1.setOnClickListener { onDigit(binding.btn1) }
        binding.btn2.setOnClickListener { onDigit(binding.btn2) }
        binding.btn3.setOnClickListener { onDigit(binding.btn3) }
        binding.btn4.setOnClickListener { onDigit(binding.btn4) }
        binding.btn5.setOnClickListener { onDigit(binding.btn5) }
        binding.btn6.setOnClickListener { onDigit(binding.btn6) }
        binding.btn7.setOnClickListener { onDigit(binding.btn7) }
        binding.btn8.setOnClickListener { onDigit(binding.btn8) }
        binding.btn9.setOnClickListener { onDigit(binding.btn9) }
        binding.btn0.setOnClickListener { onDigit(binding.btn0) }
        binding.btnDecimal.setOnClickListener { onDecimalPoint(it) }
        binding.btnEquals.setOnClickListener { onEqual(it) }
        binding.btnClear.setOnClickListener { onClear(it) }
        binding.btnAdd.setOnClickListener { onOperator(it) }
        binding.btnSubtract.setOnClickListener { onOperator(it) }
        binding.btnMultiply.setOnClickListener { onOperator(it) }
        binding.btnDivide.setOnClickListener { onOperator(it) }

        return binding.root
    }

    fun onDigit(view: View) {
        currentInput += (view as Button).text
        binding.resultTextView.text = currentInput
        lastNumeric = true
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            currentInput += "."
            binding.resultTextView.text = currentInput
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(currentInput)) {
            currentInput += (view as Button).text
            binding.resultTextView.text = currentInput
            lastNumeric = false
            lastDot = false
        }
    }

    fun onClear(view: View) {
        currentInput = ""
        binding.resultTextView.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onEqual(view: View) {
        if (lastNumeric) {
            var result: String
            try {
                result = calculateResult(currentInput)
                binding.resultTextView.text = result
            } catch (e: ArithmeticException) {
                binding.resultTextView.text = "Error"
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/")
        }
    }

    private fun calculateResult(input: String): String {
        var prefix = ""
        var expression = input

        if (expression.startsWith("-")) {
            prefix = "-"
            expression = expression.substring(1)
        }

        if (expression.contains("-")) {
            val split = expression.split("-")
            var one = split[0]
            val two = split[1]

            if (prefix.isNotEmpty()) {
                one = prefix + one
            }

            return (one.toDouble() - two.toDouble()).toString()
        } else if (expression.contains("+")) {
            val split = expression.split("+")
            return (split[0].toDouble() + split[1].toDouble()).toString()
        } else if (expression.contains("*")) {
            val split = expression.split("*")
            return (split[0].toDouble() * split[1].toDouble()).toString()
        } else if (expression.contains("/")) {
            val split = expression.split("/")
            return (split[0].toDouble() / split[1].toDouble()).toString()
        }

        return ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
