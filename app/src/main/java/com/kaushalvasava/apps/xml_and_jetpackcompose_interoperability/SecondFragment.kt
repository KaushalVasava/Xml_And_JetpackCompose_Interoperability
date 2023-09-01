package com.kaushalvasava.apps.xml_and_jetpackcompose_interoperability

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment

class SecondFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            addView(
                ComposeView(requireContext()).apply {
                    setViewCompositionStrategy(
                        ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
                    )
                    id = R.id.compose_view_1
                    setContent {
                        Text("Hello Compose 1", color = Color.Gray)
                    }
                }
            )
            addView(TextView(requireContext()).apply {
                text =  "Hello TextView"
                textSize =  20f
            })
            addView(
                ComposeView(requireContext()).apply {
                    setViewCompositionStrategy(
                        ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
                    )
                    id = R.id.compose_view_2
                    setContent {
                        Text("Hello Compose 2", color = Color.Gray)
                    }
                }
            )
        }
    }
}