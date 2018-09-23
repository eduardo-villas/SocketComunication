package br.uem.commons.rmi.interfaces;
import java.rmi.*;
import java.util.*;
import java.lang.String;

public interface MatchingInterfaceMaster extends Remote {     
    public void result(String server, String option, long id_inicio, int carga, Hashtable<String,String> resultfinal) throws RemoteException, Exception;
}

