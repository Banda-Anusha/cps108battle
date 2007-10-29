import java.util.ArrayList;


public class NetMessageListener extends Thread{
	private NetMessageHandler myNMH;
	private IBattleshipPlayer myPlayer;
	private IBattleshipModel myModel;
	private boolean myAmServer = false;
	
	public NetMessageListener(IBattleshipPlayer p, IBattleshipModel m, NetMessageHandler nmh, boolean server)
	{
		if(p == null && !server)
		{
			System.err.println("Error: Clients must have a player specified in the NetMessageListener constructor.");
			System.exit(0);
		}
		if(m == null && server)
		{
			System.err.println("Error: Servers must have a model specified in the NetMessageListener constructor.");
			System.exit(0);
			
		}
		myNMH = nmh;
		myPlayer = p;
		myModel = m;
		myAmServer = server;
	}
	
	public void run()
	{
		ArrayList<NetMessage> thingsToTakeCareOf = new ArrayList<NetMessage>();
		int i;
		while(true)
		{
			i = 0;
			synchronized (myNMH.myInputHandler.myMessages) {
				//System.err.println("NML Got the lock (size = "+myNMH.myInputHandler.myMessages.size()+")");
				while(i < myNMH.myInputHandler.myMessages.size())
				{
					if(!myNMH.someoneListening(myNMH.myInputHandler.myMessages.get(i).getClass()))
						thingsToTakeCareOf.add(myNMH.myInputHandler.myMessages.remove(i));
					else
						i++;
				}
			}
			while(!thingsToTakeCareOf.isEmpty())
			{
				NetMessage nm = thingsToTakeCareOf.remove(0);
				//System.err.println("NML Dealing With "+nm.getClass().getCanonicalName());
				if(myAmServer)
					nm.serverExecute(myModel, myPlayer);
				else
					nm.clientExecute(myPlayer);
				//System.err.println("NML Done With "+nm.getClass().getCanonicalName());
				if((myAmServer && nm.serverSendBack) || (!myAmServer && nm.clientSendBack))
				{
					//System.err.println("NML Sending Back "+nm.getClass().getCanonicalName());
					myNMH.send(nm);
				}
				//else
					//System.err.println("NML NOT Sending Back "+nm.getClass().getCanonicalName()+": myAmServer: "+myAmServer+", sendBack: "+((myAmServer) ? nm.serverSendBack : nm.clientSendBack));
			}
			//System.err.println("NML released the lock");
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				System.err.println("Error in NetMessageListener sleep()");
				System.exit(0);
			}
		}
	}
	
	
	
}
