import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

	@Override
	public void run() {
		
		ServerSocket ss;
		try {
			ss = new ServerSocket(4100);
			System.out.println("Server ouvindo....");
			Socket accept = ss.accept();
			BufferedWriter inServer = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
			inServer.write("Hey Baby gilr");
			inServer.close();		
		} catch (IOException e) {
			System.out.println("Deu um errinho bobo!");
			e.printStackTrace();
		}
	}

}
