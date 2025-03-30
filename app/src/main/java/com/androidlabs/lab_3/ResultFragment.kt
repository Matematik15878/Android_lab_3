package com.androidlabs.lab_3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ResultFragment : Fragment() {

    private lateinit var chosenCompany: TextView
    private lateinit var chosenType: TextView
    private lateinit var cancelButton: Button

    interface OnResultCancelledListener {
        fun onResultCancelled()
    }

    private var listener: OnResultCancelledListener? = null

    fun setOnResultCancelledListener(listener: OnResultCancelledListener) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        chosenCompany = view.findViewById(R.id.chosen_company)
        chosenType = view.findViewById(R.id.chosen_type)
        cancelButton = view.findViewById(R.id.button_cancel_result)

        cancelButton.setOnClickListener {
            clearResult()
            listener?.onResultCancelled()
        }
    }

    fun updateResult(company: String, type: String) {
        chosenCompany.text = getString(R.string.chosen_company) + " " + company
        chosenType.text = getString(R.string.chosen_type) + " " + type
    }

    private fun clearResult() {
        chosenCompany.text = getString(R.string.chosen_company)
        chosenType.text = getString(R.string.chosen_type)
    }

}