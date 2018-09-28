package br.uem.server;

import java.util.concurrent.Callable;

import com.google.common.base.Preconditions;

class ServerCallable implements Callable<Void> {

	private ServerRunner serverRunner;
	private Server server;
	private String threadName = "";

	public ServerCallable(ServerRunner serverRunner, Server server, String threadName) {
		this.serverRunner = serverRunner;
		this.server = server;
		this.threadName = threadName;
	}
	
	@Override
	public Void call() throws Exception {
		Preconditions.checkState(!threadName.isEmpty(), "O nome da thread deve ser especificado.");
		Thread.currentThread().setName(threadName);
		serverRunner.forever(server);
		
		return null;
	}

}
