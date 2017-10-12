package br.uem.commons.comunication;

@FunctionalInterface
public interface OperationRunner {

	void execute(SenderReceiver senderReceiver) throws Exception;

}
