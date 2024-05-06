package io.cjl.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import io.cjl.app.databinding.FragmentHomeBinding
import io.cjl.app.ui.base.BaseFragment
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HomeFragment: BaseFragment() {

    private val logger: Logger = LoggerFactory.getLogger(HomeFragment::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding = FragmentHomeBinding.inflate(inflater)
        binding.btnDialog.setOnClickListener {
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionHomeToDialog()
            )
        }
        binding.btnFilter.setOnClickListener {
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionDialogToFilter()
            )
        }


        binding.btnDev.setOnClickListener {
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionHomeToDev()
            )
        }

        binding.btnTest.setOnClickListener {
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionHomeToDev()
            )
        }

        binding.btnAd.setOnClickListener {
            Navigation.findNavController(it).navigate(
                HomeFragmentDirections.actionHomeToDev()
            )
        }

        return binding.root
    }
}