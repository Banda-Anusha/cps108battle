import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
public class NetMessageHandler extends Thread{

	public NetMessageInputHandler myInputHandler;
	public NetMessageOutputHandler myOutputHandler;
	
	public NetMessageHandler(ObjectInputStream in, ObjectOutputStream out)
	{
		myInputHandler = new NetMessageInputHandler(in);
		myOutputHandler = new NetMessageOutputHandler(out);
		myInputHandler.start();
		myOutputHandler.start();
	}
	
	public void send(NetMessage msg)
	{
		myOutputHandler.send(msg);
	}
	
	public NetMessage receive(Class whatToListenFor)
	{
		return myInputHandler.receive(whatToListenFor);
	}
	
	/**
	 * DESIGN CONSTRAINT: Anything who calls this must have the monitor for myInputHandler.myMessages.
	 * Otherwise there's a race condition
	 * @param c
	 * @return
	 */
	public boolean someoneListening(Class c)
	{
		if(c.equals(Registration.class) || c.equals(Unregistration.class))
			return true;
		return myInputHandler.myReceives.contains(c);
	}
	
}
