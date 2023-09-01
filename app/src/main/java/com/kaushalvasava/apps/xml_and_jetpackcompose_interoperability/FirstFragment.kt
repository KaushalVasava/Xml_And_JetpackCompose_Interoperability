package com.kaushalvasava.apps.xml_and_jetpackcompose_interoperability

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import com.kaushalvasava.apps.xml_and_jetpackcompose_interoperability.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                FirstScreen()
            }
        }
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    @Preview
    @Composable
    fun FirstScreenPreview() {
        FirstScreen()
    }

    @Composable
    private fun FirstScreen() {
        Box(Modifier.fillMaxSize()) {
            Text(
                "Hello Compose View",
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Center)
            )
            Button(
                onClick = {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text("Next")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}