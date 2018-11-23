package pakiet;

public class ScoreServcieProxy implements pakiet.ScoreServcie {
  private String _endpoint = null;
  private pakiet.ScoreServcie scoreServcie = null;
  
  public ScoreServcieProxy() {
    _initScoreServcieProxy();
  }
  
  public ScoreServcieProxy(String endpoint) {
    _endpoint = endpoint;
    _initScoreServcieProxy();
  }
  
  private void _initScoreServcieProxy() {
    try {
      scoreServcie = (new pakiet.ScoreServcieServiceLocator()).getScoreServcie();
      if (scoreServcie != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)scoreServcie)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)scoreServcie)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (scoreServcie != null)
      ((javax.xml.rpc.Stub)scoreServcie)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public pakiet.ScoreServcie getScoreServcie() {
    if (scoreServcie == null)
      _initScoreServcieProxy();
    return scoreServcie;
  }
  
  public pakiet.Score getScore() throws java.rmi.RemoteException{
    if (scoreServcie == null)
      _initScoreServcieProxy();
    return scoreServcie.getScore();
  }
  
  public pakiet.Score updateScore(int wins, int losses, int ties) throws java.rmi.RemoteException{
    if (scoreServcie == null)
      _initScoreServcieProxy();
    return scoreServcie.updateScore(wins, losses, ties);
  }
  
  public void reset() throws java.rmi.RemoteException{
    if (scoreServcie == null)
      _initScoreServcieProxy();
    scoreServcie.reset();
  }
  
  public int increaseWins() throws java.rmi.RemoteException{
    if (scoreServcie == null)
      _initScoreServcieProxy();
    return scoreServcie.increaseWins();
  }
  
  public int increaseLosses() throws java.rmi.RemoteException{
    if (scoreServcie == null)
      _initScoreServcieProxy();
    return scoreServcie.increaseLosses();
  }
  
  public int increaseTies() throws java.rmi.RemoteException{
    if (scoreServcie == null)
      _initScoreServcieProxy();
    return scoreServcie.increaseTies();
  }
  
  public int getWins() throws java.rmi.RemoteException{
    if (scoreServcie == null)
      _initScoreServcieProxy();
    return scoreServcie.getWins();
  }
  
  public int getLosses() throws java.rmi.RemoteException{
    if (scoreServcie == null)
      _initScoreServcieProxy();
    return scoreServcie.getLosses();
  }
  
  public int getTies() throws java.rmi.RemoteException{
    if (scoreServcie == null)
      _initScoreServcieProxy();
    return scoreServcie.getTies();
  }
  
  
}