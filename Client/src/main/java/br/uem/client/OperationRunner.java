package br.uem.client;

@FunctionalInterface
public interface OperationRunner {

	void execute(Client clientRef) throws Exception;

}
