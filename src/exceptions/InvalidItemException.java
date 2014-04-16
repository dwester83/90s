package exceptions;
/**
 * InvalidItemException
 * @author jchanke2607
 *
 */
@SuppressWarnings("serial")
public class InvalidItemException extends RuntimeException 
{

	public InvalidItemException(String msg)
	{
		super(msg);
	}
}
