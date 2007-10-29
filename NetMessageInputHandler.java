import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class NetMessageInputHandler extends Thread {

	public Set<Class> myReceives;
	public ArrayList<NetMessage> myMessages;
	private ObjectInputStream myStream;
	
	public NetMessageInputHandler(ObjectInputStream in)
	{
		myReceives = new HashSet<Class>();
		myStream = in;
		myMessages = new ArrayList<NetMessage>();
	}
	
	public void run()
	{
		NetMessage m = null;
		while(true)
		{
			try {
				m = (NetMessage) (myStream.readObject());
				//System.err.println("NMIH Received a "+m.getClass().getCanonicalName());
			} catch (IOException e) {
				System.err.println("Error in NetMessageInputHandler readObject(): "+e);
				System.exit(0);
			} catch (ClassNotFoundException e) {
				System.err.println("Class not found in NetMessageInputHandler readObject()");
			}
			synchronized (myMessages) {
				//System.err.println("NMIH run() got the lock.");
				myMessages.add(m);	
			}
			//System.err.println("NMIH run() released the lock.");
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				System.err.println("Error in NetMessageInputHandler run() sleep()");
				System.exit(0);
			}
		}
	}
	
	public NetMessage receive(Class whatToListenFor)
	{
		//System.err.println("receive...");
		//was synchronized before
		myReceives.add(whatToListenFor);
		//was synchronized before
		//System.err.println("NMIH waiting for a "+whatToListenFor.getCanonicalName());
		NetMessage nm;
		while(true)
		{
			synchronized(myMessages) {
				//System.err.println("NMIH receive() got the lock (myMessages.size() = "+myMessages.size()+")");
				for(int i = 0; i < myMessages.size(); i++)
				{
					nm = myMessages.get(i);
					if(nm.getClass().isAssignableFrom(whatToListenFor))
					{
						myMessages.remove(i);
						//System.err.println("NMIH received a "+nm.getClass().getCanonicalName());
						myReceives.remove(whatToListenFor);
						return nm;
					}
				}
			}
			//System.err.println("NMIH receive() released the lock.");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.err.println("Error in NetMessageInputHandler receive() sleep()");
				System.exit(0);
			}
		}
	}
}
