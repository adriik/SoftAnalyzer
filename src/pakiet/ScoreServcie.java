/**
 * ScoreServcie.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pakiet;

public interface ScoreServcie extends java.rmi.Remote {
    public void reset() throws java.rmi.RemoteException;
    public pakiet.Score getScore() throws java.rmi.RemoteException;
    public int increaseTies() throws java.rmi.RemoteException;
    public int getWins() throws java.rmi.RemoteException;
    public pakiet.Score updateScore(int wins, int losses, int ties) throws java.rmi.RemoteException;
    public int increaseWins() throws java.rmi.RemoteException;
    public int getLosses() throws java.rmi.RemoteException;
    public int increaseLosses() throws java.rmi.RemoteException;
    public int getTies() throws java.rmi.RemoteException;
}
