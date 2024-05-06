package io.cjl.socket

import android.text.TextUtils
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

class SocketServer(private val port: Int = 8000) {

    var serverAllowed :Boolean = true

    private var runnable = Runnable {
        val serverSocket = ServerSocket(port)
        val accept: Socket = serverSocket.accept()
        Thread { response(accept) }.start()
    }

    fun start(){
        Thread(runnable).start()
    }
    private fun response(accept: Socket) {
        try {
            //从客户端接收的信息
            val bufferedReaderIn: BufferedReader = BufferedReader(InputStreamReader(accept.getInputStream()))
            //发送信息给客户端
            val out: PrintWriter = PrintWriter(BufferedWriter(OutputStreamWriter(accept.getOutputStream())),true)
            while (serverAllowed){
                val msg = bufferedReaderIn.readLine()
                if(TextUtils.isEmpty(msg)){
                    println("收到客户端的信息为空，断开连接")
                    break
                }
                println("收到客户端的信息： $msg")
                val msgOp = "加工从客户端的信息： $msg"
                out.println(msgOp);
            }
            println("关闭服务")
            bufferedReaderIn.close()
            out.close()
            accept.close()

        }catch (e:Exception){
            println(e.message)
        }

    }

}