import java.io.Serializable;
/**
 * This enumerated type represents the different types of messages that might be passed from the Model, Player, or Rule Deck to the View.
 * Different implementations of the View may handle each type differently.
 * @author Matt Johnson
 *
 */
public enum MessageType implements Serializable{
INFO,
WARNING,
ERROR,
POPUP
}
