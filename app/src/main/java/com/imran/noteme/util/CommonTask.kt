package com.imran.noteme.util

import android.R
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.ListPopupWindow
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class CommonTask {

    fun showDatePickerWithMinDate(context: Context?, textInputLayout: TextInputLayout?) {
        val currentDate = Calendar.getInstance()
        val date = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            context!!, { view, year, month, dayOfMonth ->
                date[year, month] = dayOfMonth
                formatDateTime(date, textInputLayout)
            }, currentDate[Calendar.YEAR], currentDate[Calendar.MONTH],
            currentDate[Calendar.DATE]
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun formatDateTime(date: Calendar, textInputLayout: TextInputLayout?) {
        val myFormat = "MM/dd/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
        val formattedTime = sdf.format(date.time)
        textInputLayout?.editText?.setText(formattedTime)
    }

    fun showStatusList(
        context: Context,
        anchor: TextInputLayout,
        status: List<String>,
    ) {
        val popupWindow = ListPopupWindow(context)
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context,
            R.layout.simple_spinner_dropdown_item,
            status
        )
        popupWindow.anchorView = anchor
        popupWindow.setAdapter(adapter)
        popupWindow.width = 400
        popupWindow.setOnItemClickListener(OnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            anchor.editText?.setText(status[position])
            popupWindow.dismiss()
        })
        popupWindow.show()
    }

}