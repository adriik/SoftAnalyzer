package pakiet;

import java.rmi.RemoteException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScoreServcieProxy proxy = new ScoreServcieProxy();
		try {
			int x = proxy.getWins();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
