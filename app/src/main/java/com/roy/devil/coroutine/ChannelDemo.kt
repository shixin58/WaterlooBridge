package com.roy.devil.coroutine

import com.bride.baselib.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

suspend fun main() {
//    basics()
    broadcast()
}

suspend fun basics() {
//    val channel = Channel<Int>(Channel.RENDEZVOUS)
//    val channel = Channel<Int>(Channel.UNLIMITED)
//    val channel = Channel<Int>(Channel.CONFLATED)
//    val channel = Channel<Int>(Channel.BUFFERED)
    val channel = Channel<Int>(1)

    val producer = GlobalScope.launch {
        for (i in 0..3) {
            log("Sending $i")
            // send是挂起函数
            channel.send(i)
            log("Sent $i")
        }
        // 关闭后调用send抛异常，SendChannel#isClosedForSend返回true，但还可receive缓存的数据。
        // 缓存消费完再receive抛异常，ReceiveChannel#isClosedForReceive返回true。
        channel.close()
    }

    val consumer = GlobalScope.launch {
        /*while (!channel.isClosedForReceive) {
            log("Receiving")
            // receiveCatching是挂起函数
            val value = channel.receiveCatching().getOrNull()
            log("Received $value")
        }*/

        // ReceiveChannel定义了operator fun iterator()和suspend operator fun hasNext()。
        for (i in channel) {
            log("Received $i")
        }
    }

    producer.join()
    consumer.join()
}

suspend fun producer() {
    val receiveChannel = GlobalScope.produce<Int>(capacity = Channel.UNLIMITED) {
        for (i in 0..3) {
            log("Sending $i")
            send(i)
            log("Sent $i")
        }
    }

    val consumer = GlobalScope.launch {
        for (i in receiveChannel) {
            log("Received $i")
        }
    }

    consumer.join()
}

suspend fun consumer() {
    val sendChannel = GlobalScope.actor<Int>(capacity = Channel.UNLIMITED) {
        for (i in this) {
            log("Received $i")
        }
    }

    val producer = GlobalScope.launch {
        for (i in 0..3) {
            log("Sending $i")
            sendChannel.send(i)
            log("Sent $i")
        }
    }

    producer.join()
}

suspend fun broadcast() {
//    val broadcastChannel = BroadcastChannel<Int>(Channel.BUFFERED)
    val broadcastChannel = GlobalScope.broadcast<Int> {
        for (i in 0..5) {
            send(i)
        }
    }

    List(5) { index ->
        GlobalScope.launch {
            val receiveChannel = broadcastChannel.openSubscription()
            for (i in receiveChannel) {
                log("#$index received: $i")
            }
        }
    }.joinAll()
}