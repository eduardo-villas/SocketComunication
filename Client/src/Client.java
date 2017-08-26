import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client implements Runnable {

	@Override
	public void run() {
//		SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		Socket socket;
		try {
			socket = new Socket("192.168.25.132", 4100);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String any = in.readLine();
			System.out.println("Cliente recebendo... " + any);
			in.close();

		} catch (IOException e) {
			System.out.println("Deeu um erro no cara que tem sempre a razão!");
			e.printStackTrace();
		}
	}

}
