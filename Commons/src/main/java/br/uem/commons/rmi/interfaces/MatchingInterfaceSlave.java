package br.uem.commons.rmi.interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

public interface MatchingInterfaceSlave extends Remote {     
	public void receive(String messageRequisition) throws RemoteException, Exception;
    public void receive_slave_SR(Integer size, String idThread, String ip, long id, String option, String root, Integer group, Hashtable<String,String> subject, Hashtable<String,String> object, Hashtable<String,String> result, Hashtable<String,String> border) throws RemoteException, Exception;  
}

