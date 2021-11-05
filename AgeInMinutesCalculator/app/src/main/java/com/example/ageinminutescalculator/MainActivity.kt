package com.example.ageinminutescalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var selectedDateTextView: TextView? = null
    private var ageInMinutesTextView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonDatePicker = findViewById<Button>(R.id.buttonDatePicker)
        selectedDateTextView = findViewById(R.id.selectedDateTextView)
        ageInMinutesTextView = findViewById(R.id.ageInMinutesTextView)
        buttonDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                selectedDateTextView?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinutes =
                        theDate.time / 60_000 // 1000 to seconds, 60 to minutes
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60_000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        ageInMinutesTextView?.text = differenceInMinutes.toString()
                    }
                }
            },
            year, month, day
        )

        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 86_400_000
        datePickerDialog.show()
    }
}