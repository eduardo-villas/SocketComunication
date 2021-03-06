package br.uem.server.main;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.BindException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;

import br.uem.server.OperationRunner;
import br.uem.server.Server;
import br.uem.server.ServerRunner;

public class App {

	private static Logger logger = Logger.getLogger(App.class);
	private static int PORT = 3000;

	public static void main(String args[]) throws Exception {

		Server server = new Server(ServerSocketFactory.getDefault(), App.PORT);

		try {
			server.initServer();
		} catch (BindException bindException) {
			logger.error(String.format("Erro. porta %s em uso.", server.getPort()), bindException);
			throw bindException;
		}

		OperationRunner operationRunner = new OperationRunner() {

			Map<String, Long> stats = new LinkedHashMap<>();
			long currentTimeTransfer = 0L;
			String currentKey = null;
			ElapTime elapTime = new ElapTime();
			
			@Override
			public void execute(Server server) throws Exception {
				int cont = 0;
				try(final PrintWriter printWriter = new PrintWriter(new FileWriter("stats6.log", true))) {
					while (server.isOpen()) {
						try {
							elapTime.start();
							String message = server.getMessage();
							elapTime.finish();
							if (message.startsWith("TestKey")) {
								if (currentTimeTransfer != 0) {
									stats.put(currentKey, currentTimeTransfer);
								}
								currentKey = message;
								currentTimeTransfer = 0L;
							} else {
								currentTimeTransfer += elapTime.elapTime();
							}
							logger.info(String.format("mensagem %d enviada pelo cliente %s", ++cont, message));
							
						} catch (Exception e) {
							stats.put(currentKey, currentTimeTransfer);
							
							Iterator<String> iterator = stats.keySet().iterator();
							while (iterator.hasNext()) {
								
								String withoutKey = iterator.next();
								long timeTransferWithoutPacket = stats.get(withoutKey);
								
								String withKey = iterator.next();
								long timeTransferWithPacket = stats.get(withKey);
								
								printWriter.write(String.format("%s= %d\n", withoutKey, timeTransferWithoutPacket));
								printWriter.write(String.format("%s= %d\n", withKey, timeTransferWithPacket));
								printWriter.write(String.format("Percentual transferencia                                 %4.3f\n", ((new Double(timeTransferWithPacket)/new Double(timeTransferWithoutPacket) * 100) - 100)));
							}
							stats.clear();
							printWriter.write("\n");
							
							printWriter.flush();
							printWriter.close();
							throw e;
						}
					}
				}
				
			}

		};

		ServerRunner serverRunner = new ServerRunner(operationRunner);
		serverRunner.forever(server);

	}

}
