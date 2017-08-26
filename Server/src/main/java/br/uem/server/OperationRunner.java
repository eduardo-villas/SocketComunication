package br.uem.server;

@FunctionalInterface
public interface OperationRunner {

	void execute(Server serverRef) throws Exception;

}
