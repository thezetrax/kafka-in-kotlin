import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.ServerSocket

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
            val inputStream = BufferedReader(InputStreamReader(client.getInputStream()))
            val outputStream = DataOutputStream(client.getOutputStream())

            inputStream.readLine() // Read the request line

            outputStream.writeInt(0)
            outputStream.writeInt(7)
            outputStream.flush()

            println("accepted new connection")
        }
    }
}
