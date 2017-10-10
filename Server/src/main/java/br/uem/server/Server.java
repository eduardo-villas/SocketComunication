package br.uem.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;

import br.uem.server.protocoll.ConnectionIsClose;
import br.uem.server.protocoll.InvalidServerStateException;
import br.uem.server.protocoll.ServerState;
import br.uem.server.protocoll.ServerStoped;

public class Server implements ServerInterface {

	ServerState serverState;
	Socket socket = null;
	ServerSocket serverSocket;
	ServerSocketFactory serverSocketFactory;
	private Reader inputMessage;
	private Writer outputMessage;
	private Logger logger = Logger.getLogger(Server.class);
	private OperationRunner operationRunner;
	private int port;
	private boolean hasError;

	public Server(ServerSocketFactory serverSocketFactory, int port) {
		this.hasError = false;
		this.port = port;
		this.serverSocketFactory = serverSocketFactory;
		this.serverState = new ServerStoped(this);
		logger.info("Iniciando o servidor no estado " + this.serverState.getClass().getSimpleName());
	}

	public void setState(ServerState serverState) {
		logger.info("Servidor mudou do estado " + this.serverState.getClass().getSimpleName() + " para "
				+ serverState.getClass().getSimpleName());
		this.serverState = serverState;
	}

	@Override
	public void initServer() throws InvalidServerStateException, BindException {
		serverState.initServer();
	}

	@Override
	public boolean isOpen() throws InvalidServerStateException {
		return serverState.isOpen();
	}

	@Override
	public void waitForConnection() throws InvalidServerStateException {
		serverState.waitForConnection();
	}

	public void closeResources() {
		try {
			if (inputMessage != null)
				inputMessage.close();
			if (outputMessage != null)
				outputMessage.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			logger.error("Erro ao liberar os recursos do servidor", e);
		} finally {
			inputMessage = null;
			outputMessage = null;
			socket = null;
		}

	}

	public void sendMessage(String buffer) throws IOException {
		buffer = String.format("%4d%s", buffer.length()+4, buffer);
		logger.info("Servidor enviando mensagem: " + buffer);
		outputMessage.write(buffer);
		outputMessage.flush();
	}

	public String getMessage() throws IOException {
		
		int messageLength = readHeader(4);
		char buffer[] = readBytes(messageLength - 4); 
		String message = new String(buffer, 0, messageLength-4);

		if (isGoodbye(message))
			throw new ConnectionIsClose();
		return message;
	}

	private int readHeader(int len) throws IOException {
		char bytes[] = readBytes(len);
		int sizePacket = Integer.parseInt(new String(bytes, 0, 4)); 
		return sizePacket;
	}
	
	private char[] readBytes(int len) throws IOException {
		
		char buffer[] = new char[len];
		int offset = 0;
		int messageLength = 0;
		do {
			offset = inputMessage.read(buffer, offset, len);
			messageLength += offset;
			len -= offset;
		} while (hasMoreBytes(buffer, offset, len, messageLength));
		
		return buffer;
	}

	private boolean hasMoreBytes(char[] buffer, int offset, int len, int lenMessage) {
		return lenMessage < len;
	}

	private boolean isGoodbye(String message) {
		return "GOODBYE".equals(message);
	}

	public void initializeIOBuffers() throws IOException {
		try {
			inputMessage = new br.uem.comons.Reader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			outputMessage = new br.uem.comons.Writer(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
		} catch (IOException e) {
			logger.error("Erro ao inicializar os canais de entrada e saida.", e);
			throw new IOException("Erro ao inicializar os canais de entrada e saida.", e);
		}

	}

	public void createServerSocket(int port) throws IOException, BindException {
		serverSocket = serverSocketFactory.createServerSocket(port);
	}

	public Socket getSocket() {
		return socket;
	}

	public void accept() throws IOException {
		socket = serverSocket.accept();
	}

	public void setOperationRunner(OperationRunner operationRunner) {
		this.operationRunner = operationRunner;
	}

	public OperationRunner getOperationRunner() {
		return operationRunner;
	}

	@Override
	public void doComunication() throws InvalidServerStateException, IOException {
		serverState.doComunication();
	}

	public int getPort() {
		return port;
	}

	public boolean getHasError() {
		return hasError;
	}

	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
}
