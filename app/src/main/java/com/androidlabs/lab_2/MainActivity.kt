package com.androidlabs.lab_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(),
    InputFragment.OnFormSubmittedListener,
    ResultFragment.OnResultCancelledListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val resultFragment = ResultFragment()
            resultFragment.setOnResultCancelledListener(this)
            supportFragmentManager.commit {
                replace(R.id.container_result, resultFragment, "RESULT_FRAGMENT")
            }

            val inputFragment = InputFragment()
            inputFragment.setOnFormSubmittedListener(this)
            supportFragmentManager.commit {
                replace(R.id.container_input, inputFragment, "INPUT_FRAGMENT")
            }
        }
    }

    override fun onFormSubmitted(company: String, type: String) {
        val resultFragment = supportFragmentManager.findFragmentByTag("RESULT_FRAGMENT") as? ResultFragment
        resultFragment?.updateResult(company, type)
    }

    override fun onResultCancelled() {
        val inputFragment = supportFragmentManager.findFragmentByTag("INPUT_FRAGMENT") as? InputFragment
        inputFragment?.clearForm()
    }

}