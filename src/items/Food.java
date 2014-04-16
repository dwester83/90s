package items;
/**
 * A child class of Item
 * @author jchanke2607
 */
public class Food extends Item 
{
	private double caloriesInFood; //how many calories you have to use to get the benefit of this food

	/**
	 * 
	 * @param nameOfFood
	 * @param costOfFood
	 * @param calories
	 * @param maxGroupSize
	 */
	public Food(String nameOfFood, double costOfFood, int maxGroupSize, double weight, double calories)
	{
		super(nameOfFood, costOfFood, maxGroupSize, weight);
		setCaloriesInFood(calories);
	}
	/**
	 * @return the caloriesInFood
	 */
	public double getCaloriesInFood() {
		return caloriesInFood;
	}
	/**
	 * @param caloriesInFood the caloriesInFood to set
	 */
	private void setCaloriesInFood(double food) {
		caloriesInFood = food;
	}
	/**
	 * returns a String representation of this Food Item
	 */
	public String toString()
	{
		return super.getNameOfItem();
				
	}
}
