package com.example.myresponsive.activity.ui.transform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myresponsive.R

class Dex2CSelectorFragment : Fragment() {

    private lateinit var container: LinearLayout
    private val classList = listOf(
        "com.example.app.MainActivity",
        "com.example.app.LoginActivity",
        "com.example.app.HomeActivity",
        "com.example.app.Utils",
        "com.example.app.NetworkHelper",
        "com.example.app.DatabaseManager",
        "com.example.app.CryptoUtils",
        "com.example.app.FileHelper"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dex2c_selector, container, false)
        this.container = view.findViewById(R.id.checkboxContainer)
        classList.forEach { className ->
            val cb = CheckBox(requireContext()).apply {
                text = className
                textSize = 14f
                setPadding(24, 12, 24, 12)
            }
            this.container.addView(cb)
        }
        view.findViewById<View>(R.id.btnStartProtect).setOnClickListener {
            val selected = (0 until this.container.childCount)
                .map { this.container.getChildAt(it) as CheckBox }
                .filter { it.isChecked }
                .map { it.text.toString() }
            if (selected.isEmpty()) {
                Toast.makeText(requireContext(), "请至少选择一个类", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(requireContext(), "已选择 ${selected.size} 个类", Toast.LENGTH_SHORT).show()
        }
        return view
    }
}