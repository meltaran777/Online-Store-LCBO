package com.jaaarl.catalog.ui.storesearch


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jaaarl.catalog.R


/**
 * A simple [Fragment] subclass.
 */
class SelectPhoneNumberFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_select_phone_number, container, false)
    }
}
