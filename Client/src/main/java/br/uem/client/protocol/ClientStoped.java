package br.uem.client.protocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import br.uem.client.Client;

public class ClientStoped implements ClientState {

	private Client client;
	private Logger logger = Logger.getLogger(ClientStoped.class);

	public ClientStoped(Client client) {
		this.client = client;
	}

	@Override
	public void runClient() throws InvalidClientStateException {
		String host = this.client.getServerIp();
		int port = this.client.getServerPort();
		try {
			InetAddress inetAddress = InetAddress.getByName(host);
			client.setSocket(new Socket(inetAddress, port));
			client.initializeIOBuffers();
			logger.info("O Cliente está iniciado!");
			this.client.setState(new ClientHandshake(this.client));
		} catch (UnknownHostException e) {
			logger.error(String.format("Erro. Ip %s não encontrado.", host));
			client.closeResources();
			client.setState(new ClientError(client));
		} catch (IOException e) {
			logger.error("Erro ao receber a resposta do Servidor.", e);
			client.closeResources();
			client.setState(new ClientError(client));
		}
	}

	@Override
	public boolean isOpen() throws InvalidClientStateException {
		return true;
	}

	@Override
	public void doComunication() throws InvalidClientStateException, IOException {
		throw new InvalidClientStateException("O servidor esta parado.");
	}

}
