package items;

/**
 * The parent class for all of the items in the game
 * @author jchanke2607
 */
public class Item 
{
	private String nameOfItem;
	private double costOfItem;
	private int maxGroupSize;
	private double itemsWeight;
	
	public Item(String name, double cost, int groupSize, double weight)
	{
		setNameOfItem(name);
		setCostOfItem(cost);
		setMaxGroupSize(groupSize);
		setItemsWeight(weight);
		
	}
	/**
	 * @return the itemsWieght
	 */
	public double getItemsWeight() {
		return itemsWeight;
	}
	/**
	 * @param wieght the itemsWieght to set
	 */
	public void setItemsWeight(double weight) {
		itemsWeight = weight;
	}
	/**
	 * @return the nameOfItem
	 */
	public String getNameOfItem() {
		return nameOfItem;
	}
	/**
	 * @param nameOfItem the nameOfItem to set
	 */
	private void setNameOfItem(String name) {
		nameOfItem = name;
	}
	/**
	 * @return the costOfItem
	 */
	public double getCostOfItem() {
		return costOfItem;
	}
	/**
	 * @param costOfItem the costOfItem to set
	 */
	private void setCostOfItem(double cost) {
		costOfItem = cost;
	}
	/**
	 * @return the maxGroupSize
	 */
	public int getMaxGroupSize() {
		return maxGroupSize;
	}
	/**
	 * @param maxGroupSize the maxGroupSize to set
	 */
	public void setMaxGroupSize(int GroupingSize) {
		maxGroupSize = GroupingSize;
	}
	/**
	 * Compares the name of This Item and the passed in Object to see if they are equal
	 * @return true if Strings are same
	 */
	@Override
	public boolean equals(Object obj)
	{
	      Item tempItem;
	      if (obj instanceof Item)
	      {
	        tempItem = (Item)obj;

	        return(this.nameOfItem.equals(tempItem.nameOfItem)); 
	      }
	      return false;
	}
	public String toString()
	{
		return nameOfItem;
	}
}
