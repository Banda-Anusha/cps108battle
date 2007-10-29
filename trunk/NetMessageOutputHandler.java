import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class NetMessageOutputHandler extends Thread {

	private ArrayList<NetMessage> myMessages;
	private ObjectOutputStream myStream;
	
	public NetMessageOutputHandler(ObjectOutputStream out)
	{
		myStream = out;
		myMessages = new ArrayList<NetMessage>();
	}
	
	public void run()
	{
		while(true)
		{
			synchronized (myMessages) {
				while(!myMessages.isEmpty())
				{
						NetMessage nm = myMessages.remove(0);
						try {
							//System.err.println("NMOH Sending a "+nm.getClass().getCanonicalName());
							myStream.writeObject(nm);
						} catch (IOException e) {
							System.err.println("Error in NetMessageOutputHandler writeObject(): "+e);
							System.exit(0);
						}
				}
			}
			try {
				myStream.flush();
			} catch (IOException e) {
				System.err.println("Error flushing OutStream in NetMessageOutputHandler");
				System.exit(0);
			}

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.err.println("Error in NetMessageHandler sleep()");
				System.exit(0);
			}
		}
	}
	
	public void send(NetMessage msg)
	{
		synchronized(myMessages)
		{
			myMessages.add(msg);
		}
	}
	
	
}
