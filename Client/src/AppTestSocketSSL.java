public class AppTestSocketSSL {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Iniciando client");
		new Thread(new Client()).start();
		
	}

}
