package br.uem.server;

import java.net.BindException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;

import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.commons.comunication.OperationRunner;

public class ThreadServerCreator {

	private int port;
	private String threadName;
	private OperationRunner operationRunner;

	public ThreadServerCreator(int port, String threadName, OperationRunner operationRunner) {
		this.port = port;
		this.threadName = threadName;
		this.operationRunner = operationRunner;
	}

	public Future<Void> createThread() throws InvalidComunicationStateException, BindException {
		
		Logger logger = Logger.getLogger(ThreadServerCreator.class);
		
		Server server = new Server(ServerSocketFactory.getDefault(), port);
		try {
			server.initServer();
		} catch (InvalidComunicationStateException e) {
			logger.error("Erro ao iniciar o servidor.", e);
			throw e;
		} catch (BindException bindException) {
			logger.error(String.format("Erro. porta %s em uso.", server.getPort()), bindException);
			throw bindException;
		}

		ServerRunner serverRunner = new ServerRunner(operationRunner);
		ServerCallable serverCallable = new ServerCallable(serverRunner, server, threadName);

		try {
			ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
			Future<Void> futureServerThread = newSingleThreadExecutor.submit(serverCallable);
			logger.info("+++++++++++++++++++++++++++++++++++++++++++");
			logger.info("SERVER is connected and ready for operation on port " + port);
			logger.info("+++++++++++++++++++++++++++++++++++++++++++");
			return futureServerThread;
		} catch (Exception e) {
			logger.error(String.format("Erro ao inciar a thread %s", threadName), e);
			throw e;
		}

	}

}
