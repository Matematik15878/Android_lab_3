package com.androidlabs.lab_1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var chosenCompany: TextView
    private lateinit var chosenType: TextView
    private lateinit var companyGroup: RadioGroup
    private lateinit var typeGroup: RadioGroup
    private lateinit var buttonCancel: Button
    private lateinit var buttonOk: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lab_1_layout)

        val companyRadioGroup = findViewById<LinearLayout>(R.id.company_radio_group)
        val typeRadioGroup = findViewById<LinearLayout>(R.id.type_radio_group)

        buttonCancel = findViewById(R.id.button_cancel)
        buttonOk = findViewById(R.id.button_confirm)
        chosenCompany = findViewById(R.id.chosen_company)
        chosenType = findViewById(R.id.chosen_type)

        companyGroup = RadioGroup(this).apply { orientation = RadioGroup.VERTICAL }
        typeGroup = RadioGroup(this).apply { orientation = RadioGroup.VERTICAL }

        companyRadioGroup.addView(companyGroup)
        typeRadioGroup.addView(typeGroup)

        setSampleData()

        buttonOk.setOnClickListener { onOkPressed() }
        buttonCancel.setOnClickListener { onCancelPressed() }
    }

    private fun addRadioButton(group: RadioGroup, name: String) {
        val newId = View.generateViewId()
        val radioButton = RadioButton(this).apply {
            text = name
            id = newId
            textSize = 20f
        }
        group.addView(radioButton)
    }

    private fun onOkPressed() {
        val selectedFirmaId = companyGroup.checkedRadioButtonId
        val selectedTypeId = typeGroup.checkedRadioButtonId

        if (selectedFirmaId != -1 && selectedTypeId != -1) {
            val selectedFirma = findViewById<RadioButton>(selectedFirmaId).text.toString()
            val selectedType = findViewById<RadioButton>(selectedTypeId).text.toString()

            val companyResult = getString(R.string.chosen_company) + ' ' + selectedFirma
            val typeResult = getString(R.string.chosen_type) + ' ' + selectedType

            chosenCompany.text = companyResult
            chosenType.text = typeResult
        } else {
            Toast.makeText(this, getString(R.string.company_and_type), Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCancelPressed() {
        typeGroup.clearCheck()
        companyGroup.clearCheck()
        chosenType.text = getString(R.string.chosen_type)
        chosenCompany.text = getString(R.string.chosen_company)
    }

    private fun setSampleData() {
        listOf("D", "CCC", "AAAAA").forEach { addRadioButton(companyGroup, it) }
        listOf("T12", "T54", "T1", "T5", "R23").forEach { addRadioButton(typeGroup, it) }
    }

}