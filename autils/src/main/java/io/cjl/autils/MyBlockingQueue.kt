package io.cjl.autils

import java.util.concurrent.ArrayBlockingQueue

class MyBlockingQueue {
    var cmd: Any? = ArrayBlockingQueue<Any?>(50)
}