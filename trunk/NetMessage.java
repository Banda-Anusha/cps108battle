import java.io.Serializable;


public abstract class NetMessage implements Serializable{
	public boolean serverSendBack;
	public boolean clientSendBack;
	public String myPlayerName;
	
	public NetMessage(IBattleshipPlayer player, boolean clientSB, boolean serverSB)
	{
		myPlayerName = (player == null) ? "" : player.getName();
		serverSendBack = serverSB;
		clientSendBack = clientSB;
	}
	
	public void clientExecute(IBattleshipPlayer p)
	{
		
	}
	
	public void serverExecute(IBattleshipModel m, IBattleshipPlayer proxy)
	{
		
	}
	
}
