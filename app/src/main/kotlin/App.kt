import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.ServerSocket
import java.nio.ByteBuffer

fun main(args: Array<String>) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.err.println("Logs from your program will appear here!")

    val serverSocket = ServerSocket(9092)

    //region SocketOptions
    // Since the tester restarts your program quite often, setting SO_REUSEADDR
    // ensures that we don't run into 'Address already in use' errors
    serverSocket.reuseAddress = true
    //endregion

    while(true) {
        val socket = serverSocket.accept()
        socket.use { client ->
            val inputStream = client.getInputStream()
            val outputStream = DataOutputStream(client.getOutputStream())

            val messageSize = ByteBuffer.wrap(inputStream.readNBytes(4)).getInt()
            val messageBytes = inputStream.readNBytes(messageSize)

            val correlationId = messageBytes.slice(4 until 8).toByteArray()

            outputStream.writeInt(0)
            outputStream.write(correlationId)
            outputStream.flush()

            println("accepted new connection")
        }
    }
}
