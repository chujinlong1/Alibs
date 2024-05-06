package io.cjl.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.cjl.app.databinding.FragmentDevBinding
import io.cjl.app.ui.base.BaseFragment
import io.cjl.socket.SocketServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DevFragment: BaseFragment()  {

    private val logger: Logger = LoggerFactory.getLogger(DevFragment::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentDevBinding.inflate(inflater)
        try {
            SocketServer(6661).start()
        }catch (e: Exception){
            e.printStackTrace()
        }
        return binding.root
    }

}