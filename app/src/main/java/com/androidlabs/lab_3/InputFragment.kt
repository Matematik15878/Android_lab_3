package com.androidlabs.lab_3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileOutputStream

class InputFragment : Fragment() {

    private lateinit var companyGroup: RadioGroup
    private lateinit var typeGroup: RadioGroup
    private lateinit var okButton: Button
    private lateinit var openButton: Button

    interface OnFormSubmittedListener {
        fun onFormSubmitted(company: String, type: String)
    }
    private var listener: OnFormSubmittedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_input, container, false)
    }

    fun setOnFormSubmittedListener(listener: OnFormSubmittedListener) {
        this.listener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val companyRadioContainer = view.findViewById<LinearLayout>(R.id.company_radio_group)
        val typeRadioContainer = view.findViewById<LinearLayout>(R.id.type_radio_group)

        okButton = view.findViewById(R.id.button_ok)
        openButton = view.findViewById(R.id.button_open)

        companyGroup = RadioGroup(context).apply { orientation = RadioGroup.VERTICAL }
        typeGroup = RadioGroup(context).apply { orientation = RadioGroup.VERTICAL }
        companyRadioContainer.addView(companyGroup)
        typeRadioContainer.addView(typeGroup)

        setSampleData()

        okButton.setOnClickListener { onOkPressed() }
        openButton.setOnClickListener { openNewActivity() }
    }

    private fun addRadioButton(group: RadioGroup, name: String) {
        val newId = View.generateViewId()
        val radioButton = RadioButton(context).apply {
            text = name
            id = newId
            textSize = 20f
        }
        group.addView(radioButton)
    }

    private fun onOkPressed() {
        val selectedCompanyId = companyGroup.checkedRadioButtonId
        val selectedTypeId = typeGroup.checkedRadioButtonId

        if (selectedCompanyId != -1 && selectedTypeId != -1) {
            val selectedCompany = view?.findViewById<RadioButton>(selectedCompanyId)?.text.toString()
            val selectedType = view?.findViewById<RadioButton>(selectedTypeId)?.text.toString()

            saveDataToFile(selectedCompany, selectedType)

            listener?.onFormSubmitted(selectedCompany, selectedType)
        } else {
            Toast.makeText(context, getString(R.string.company_and_type), Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveDataToFile(company: String, type: String) {
        val file = File(context?.filesDir, "user_data.txt")
        try {
            FileOutputStream(file).use {
                it.write("Company: $company\nType: $type".toByteArray())
            }
            Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to save data", Toast.LENGTH_SHORT).show()
        }
    }


    private fun openNewActivity() {
        val intent = Intent(context, OpenActivity::class.java)
        startActivity(intent)
    }

    fun clearForm() {
        companyGroup.clearCheck()
        typeGroup.clearCheck()
    }

    private fun setSampleData() {
        listOf("D", "CCC", "AAAAA").forEach { addRadioButton(companyGroup, it) }
        listOf("T12", "T54", "T1", "T5", "R23").forEach { addRadioButton(typeGroup, it) }
    }
}
