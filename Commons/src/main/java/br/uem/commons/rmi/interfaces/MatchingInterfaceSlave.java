package br.uem.commons.rmi.interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatchingInterfaceSlave extends Remote {     
	public void receive(String messageRequisition) throws RemoteException, Exception;
    public void receive_slave_SR(String IntermediaryResult) throws Exception;  
}

