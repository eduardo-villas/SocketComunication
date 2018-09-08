package br.uem.client.protocol;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uem.client.Client;
import br.uem.commons.comunication.ConnectionIsCloseException;
import br.uem.commons.comunication.InvalidComunicationStateException;

public class ClientReady implements ClientState {

	Logger logger = Logger.getLogger(ClientReady.class);
	private Client client;

	public ClientReady(Client client) {
		this.client = client;
	}

	@Override
	public void runClient() throws InvalidComunicationStateException {
		logger.warn("O Cliente já está rodando.");
	}

	@Override
	public boolean isOpen() throws InvalidComunicationStateException {
		return !client.getSocket().isClosed();
	}

	@Override
	public void doComunication() throws InvalidComunicationStateException, IOException {
		try {
			this.client.getOperationRunner().execute(this.client);
		} catch (ConnectionIsCloseException e) {
			logger.info("A conexao foi fechada pelo servidor");
		} catch (Exception e) {
			logger.error("Ocorreu um erro na execução da operação.", e);
		} finally {
			this.client.setState(new ClientSayGoodbye(this.client));
		}
	}

}
