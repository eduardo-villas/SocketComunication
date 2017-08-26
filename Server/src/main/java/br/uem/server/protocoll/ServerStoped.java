package br.uem.server.protocoll;

import java.io.IOException;
import java.net.BindException;

import org.apache.log4j.Logger;

import br.uem.server.Server;

public class ServerStoped implements ServerState {

	private Server server;
	Logger logger = Logger.getLogger(ServerStoped.class);
	
	public ServerStoped(Server server) {
		this.server = server;
	}
	
	@Override
	public void initServer() throws InvalidServerStateException, BindException {
		try {
			server.createServerSocket(server.getPort());
			server.setState(new ServerWaiting(server));
		} catch (BindException e){
			logger.error("Erro ao iniciar o servidor na porta:"+this.server.getPort(), e);
			server.setHasError(true);
			throw e;
		} catch (IOException e) {
			logger.error("Erro ao iniciar o servidor na porta:"+this.server.getPort(), e);
		}
		
	}

	String read() throws InvalidServerStateException , IOException {
		throw new InvalidServerStateException("Estado do servidor invalido para operação read");
	}

	void write(String message) throws InvalidServerStateException, IOException{
		throw new InvalidServerStateException("Estado do servidor invalido para operação write");
	}

	@Override
	public void waitForConnection() throws InvalidServerStateException {
		throw new InvalidServerStateException("Estado do servidor invalido para operação waiting");
	}

	@Override
	public boolean isOpen() throws InvalidServerStateException {
		return false;
	}

	@Override
	public void doComunication() throws InvalidServerStateException, IOException {
		write(read());
	}
	
}
