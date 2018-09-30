package br.uem.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import br.uem.client.protocol.ClientState;
import br.uem.client.protocol.ClientStoped;
import br.uem.commons.comunication.ConnectionIsCloseException;
import br.uem.commons.comunication.Constants;
import br.uem.commons.comunication.InvalidComunicationStateException;
import br.uem.commons.comunication.MyReader;
import br.uem.commons.comunication.OperationRunner;
import br.uem.commons.comunication.SenderReceiver;
import br.uem.commons.comunication.Writer;

public class Client implements ClientInterface, SenderReceiver {

	private ClientState clientState;
	private Socket socket = null;
	private MyReader inputMessage;
	private Writer outputMessage;
	private Logger logger = Logger.getLogger(Client.class);
	private String parameters;
	private OperationRunner operationRunner;
	private String serverIp;
	private int serverPort;

	public Client(String serverIp, int serverPort) throws IOException {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.clientState = new ClientStoped(this);
	}

	public String getParameters() {
		return this.parameters;
	}

	public void setState(ClientState clientState) {
		logger.info("Cliente mudou do estado " + this.clientState.getClass().getSimpleName() + " para "
				+ clientState.getClass().getSimpleName());
		this.clientState = clientState;
	}

	@Override
	public void runClient() throws InvalidComunicationStateException {
		String message = "Tentando conectar-se ao host:" + this.serverIp + ":" + this.serverPort + "-"
				+ this.clientState.getClass().getSimpleName();
		logger.info(message);
		clientState.runClient();
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		clientState.doComunication();
	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return clientState.isOpen();
	}

	public void closeResources() {
		try {
			if (inputMessage != null)
				inputMessage.close();
			if (outputMessage != null)
				outputMessage.close();
			if (getSocket() != null)
				getSocket().close();
		} catch (IOException e) {
			logger.error("Erro ao liberar os recursos do Cliente", e);
		} finally {
			inputMessage = null;
			outputMessage = null;
			setSocket(null);
		}

	}

	@Override
	public void sendMessage(String buffer) throws IOException {
		outputMessage.write(buffer);
		outputMessage.flush();
	}

	public void sendMessageWithoutFlush(String buffer) throws IOException {
		outputMessage.write(buffer);
	}

	@Override
	public String getMessage() throws IOException {

		String message;

		try {
			int messageLength = this.inputMessage.readHeader(0, Constants.HEADER_SIZE);
			char buffer[] = this.inputMessage.readBytes(Constants.HEADER_SIZE, messageLength);
			message = new String(buffer, 0, messageLength - Constants.HEADER_SIZE);

		} catch (Exception e) {
			if (!(e instanceof ConnectionIsCloseException)) {
				logger.error("Erro ao ler a mensagen do sevidor.", e);
			}
			throw new ConnectionIsCloseException("Erro ao ler a mensagen do sevidor.", e);
		}

		return message;

	}

	public void initializeIOBuffers() throws IOException {
		try {
			inputMessage = new MyReader(new BufferedReader(new InputStreamReader(getSocket().getInputStream())));
			outputMessage = new Writer(new BufferedWriter(new OutputStreamWriter(getSocket().getOutputStream())));
		} catch (IOException e) {
			logger.error("Erro ao inicializar os canais de entrada e saida.", e);
			throw new IOException("Erro ao inicializar os canais de entrada e saida.", e);
		}

	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setOperationRunner(OperationRunner operationRunner) {
		this.operationRunner = operationRunner;
	}

	public OperationRunner getOperationRunner() {
		return this.operationRunner;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

}
