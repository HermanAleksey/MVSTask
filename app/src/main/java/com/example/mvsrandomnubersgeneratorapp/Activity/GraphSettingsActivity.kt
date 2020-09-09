package com.example.mvsrandomnubersgeneratorapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.example.mvsrandomnubersgeneratorapp.R
import com.example.mvsrandomnubersgeneratorapp.ViewHolder.MainActivityHolder
import kotlinx.android.synthetic.main.activity_graph_settings.*

class GraphSettingsActivity : AppCompatActivity() {

    var graphNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_settings)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val arguments = intent.extras
        graphNumber = arguments!!.getInt("graphNumber")
//        setTitle("Настройки графа #$graphNumber")

        setupInfoAboutGraph(graphNumber)

        button_settings_activity_confirm.setOnClickListener {
            saveInfoAboutGraph(graphNumber)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        saveInfoAboutGraph(graphNumber)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun saveInfoAboutGraph(graphNumber: Int) {
        MainActivityHolder.graphSettings[graphNumber - 1].startX =
            edit_text_settings_activity_x_start.text.toString().toInt()
        MainActivityHolder.graphSettings[graphNumber - 1].endX =
            edit_text_settings_activity_x_end.text.toString().toInt()
        MainActivityHolder.graphSettings[graphNumber - 1].startY =
            edit_text_settings_activity_y_start.text.toString().toInt()
        MainActivityHolder.graphSettings[graphNumber - 1].endY =
            edit_text_settings_activity_y_end.text.toString().toInt()
        MainActivityHolder.graphSettings[graphNumber - 1].zoomable =
            checkBox_settings_activity_enable_zooming.isChecked
        MainActivityHolder.graphSettings[graphNumber - 1].scrollable =
            checkBox_settings_activity_enable_scrolling.isChecked

    }

    private fun setupInfoAboutGraph(graphNumber: Int) {
        edit_text_settings_activity_x_start.setText(MainActivityHolder.graphSettings[graphNumber - 1].startX.toString())
        edit_text_settings_activity_x_end.setText(MainActivityHolder.graphSettings[graphNumber - 1].endX.toString())
        edit_text_settings_activity_y_start.setText(MainActivityHolder.graphSettings[graphNumber - 1].startY.toString())
        edit_text_settings_activity_y_end.setText(MainActivityHolder.graphSettings[graphNumber - 1].endY.toString())
        checkBox_settings_activity_enable_zooming.isChecked =
            MainActivityHolder.graphSettings[graphNumber - 1].zoomable
        checkBox_settings_activity_enable_scrolling.isChecked =
            MainActivityHolder.graphSettings[graphNumber - 1].scrollable
    }
}

