package br.uem.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;

import br.uem.commons.comunication.ConnectionIsCloseException;
import br.uem.commons.comunication.Constants;
import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.commons.comunication.MyReader;
import br.uem.commons.comunication.OperationRunner;
import br.uem.commons.comunication.SenderReceiver;
import br.uem.commons.comunication.Writer;
import br.uem.commons.comunication.exceptions.InvalidMessageLength;
import br.uem.server.protocoll.ServerState;
import br.uem.server.protocoll.ServerStoped;

public class Server implements ServerInterface, SenderReceiver {

	ServerState serverState;
	Socket socket = null;
	ServerSocket serverSocket;
	ServerSocketFactory serverSocketFactory;
	private MyReader inputMessage;
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
	public void initServer() throws InvalidComunicationStateException, BindException {
		serverState.initServer();
	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return serverState.isOpen();
	}

	@Override
	public void waitForConnection() throws InvalidComunicationStateException {
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
		if (buffer.isEmpty())
			throw new RuntimeException("Erro buffer vazio");
		Preconditions.checkState(outputMessage != null, "Erro. O objeto de mensagem não pode ser null. Provavelmente a conexão foi fechada.");
		outputMessage.write(buffer);
	}

	public String getMessage() throws IOException {

		String message;

		try {
			int messageLength = this.inputMessage.readHeader(0, Constants.HEADER_SIZE);
			if (messageLength <= 0) {
				InvalidMessageLength invalidMessageLengthException = new InvalidMessageLength("Erro. Tamanho da mensagem inválida.", messageLength);
				logger.error("Erro. Tamanho da mensagem inválida.", invalidMessageLengthException);
				throw invalidMessageLengthException;
			}
			char buffer[] = this.inputMessage.readBytes(Constants.HEADER_SIZE, messageLength);
			message = new String(buffer, 0, messageLength - Constants.HEADER_SIZE);
		} catch (Exception e) {
			logger.error("Erro ao ler a mensagen do cliente.", e);
			throw new ConnectionIsCloseException("Erro ao ler a mensagen do cliente.", e);
		}

		return message;
	}

	public void initializeIOBuffers() throws IOException {
		try {
			inputMessage = new MyReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			outputMessage = new Writer(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
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
	public void doComunication() throws InvalidComunicationStateException, IOException {
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
