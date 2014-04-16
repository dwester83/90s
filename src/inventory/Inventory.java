package inventory;


import items.Item;
import exceptions.InvalidItemException;
import exceptions.ZeroItemsException;




public class Inventory
{
	protected Group[] itemsInInventory;
	protected int currentSizeOfInventory;
	protected int maxInventorySize;
	
	public Inventory(int inventorySize)
	{
		itemsInInventory = new Group[inventorySize];
		currentSizeOfInventory = 0;
		maxInventorySize = inventorySize;
	}
	/**
	 * adds a single Item to a Group of Items
	 * @param newItem
	 * @param index
	 */
	public void add(Item newItem, int index)
	{

		if(index < currentSizeOfInventory)
		{
			Item tempItem = itemsInInventory[index].getItemType();
			try
			{
				if(tempItem.equals(newItem))
				{
					itemsInInventory[index].addItems(newItem, 1);
				}
			}catch(InvalidItemException iie)
			{
				System.out.println("can't add " + newItem.getNameOfItem() + " to " + itemsInInventory[index].getItemType().getNameOfItem());
			}
		}
		else
		{
			Group tempGroup = new Group(newItem);
			tempGroup.addItems(newItem, 1);
			itemsInInventory[currentSizeOfInventory] = tempGroup;
			currentSizeOfInventory++;
		}

	}
	/**
	 * adds Multipule Items to a Group
	 * @param newItem
	 * @param index
	 * @param numberToAdd
	 */
	public void addMultiple(Item newItem, int index, int numberToAdd)
	{
		if(index < currentSizeOfInventory)
		{
			Item tempItem = itemsInInventory[index].getItemType();
			try
			{
				if(tempItem.equals(newItem))
				{
					itemsInInventory[index].addItems(newItem, numberToAdd);
				}
			}catch(InvalidItemException iie)
			{
				System.out.println("can't add " + newItem.getNameOfItem() + " to " + itemsInInventory[index].getItemType().getNameOfItem());
			}
		}
		else
		{
			Group tempGroup = new Group(newItem);
			tempGroup.addItems(newItem, numberToAdd);
			itemsInInventory[currentSizeOfInventory] = tempGroup;
			currentSizeOfInventory++;
		}

	}
	/**
	 * returns the max size of this inventory
	 * @return
	 */
	public int inventorySize()
	{
		return maxInventorySize;
	}
	public int currentInventorySize()
	{
		return currentSizeOfInventory;
	}
	/**
	 * removes an item from this inventory
	 * @return
	 */
	public Item useGroup(int index) throws ZeroItemsException
	{
		return itemsInInventory[index].useItem();
	}
	/**
	 * Just returns the item with out deducting it from total 
	 * @param index
	 * @return
	 */
	public Item getItem(int index)
	{
		if(itemsInInventory[index] == null)
			return null;
		else
			return itemsInInventory[index].getItemType();
	}
	public int getTotalItems(int index)
	{
		if(itemsInInventory[index] == null)
			return 0;
		else
			return itemsInInventory[index].getTotalItemsInGroup();
	}
	public String toString()
	{
		String result = "";
		
		for(int i = 0; i < currentSizeOfInventory; i++)
		{
			result = result + i + ". " + itemsInInventory[i].toString() + "\n"; 
		}
		return result;
	}

}
