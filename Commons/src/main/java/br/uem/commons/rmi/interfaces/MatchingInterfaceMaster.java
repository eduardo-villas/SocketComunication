package br.uem.commons.rmi.interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MatchingInterfaceMaster extends Remote {     
    public void result(String message) throws RemoteException, Exception;
}

