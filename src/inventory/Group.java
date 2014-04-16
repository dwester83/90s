package inventory;


import items.Item;
import exceptions.InvalidItemException;
import exceptions.ZeroItemsException;



/**
 * The Group class holds a stack of Items for the Inventory
 *  or passing to another character
 * @author jchanke2607
 *
 */
public class Group 
{
	private int totalItemsInGroup;
	private int maxGroupSize;
	private Item itemsInGroup;
	
	public Group(Item itemInGroup)
	{
		itemsInGroup = itemInGroup;
		maxGroupSize = 100;
	}
	/**
	 * Returns the type of Item in this group
	 * @return
	 */
	public Item getItemType()
	{
		if(itemsInGroup == null)
			return null;
		return itemsInGroup;
	}
	/**
	 * Returns the total number of Items in this Group
	 * @return
	 */
	public int getTotalItemsInGroup()
	{
		return totalItemsInGroup;
	}
	/**
	 * returns an Item from this Group
	 * @return
	 * @throw ZeroItemsException
	 */
	public Item useItem()
	{
		if(totalItemsInGroup == 0)
		{
			throw new ZeroItemsException();
		}
		else
		{
			totalItemsInGroup--;
			return itemsInGroup;
		}
	}
	/**
	 * adds the a number of Items to this Group
	 * @param moreItems
	 * @param numberToAdd
	 * @throw InvalidItemException if Items are not equal
	 */
	public void addItems(Item moreItems, int numberToAdd)
	{
		if(itemsInGroup.equals(moreItems))
		{
			totalItemsInGroup += numberToAdd;
		}
		else
		{
			throw new InvalidItemException(moreItems.getNameOfItem());
		}
	}
	public String toString()
	{
		return "" + itemsInGroup.toString() + "(" + totalItemsInGroup + ")";
	}
}
