package com.elsea.stone.groups;

public class Property extends Element
{

	public Property(String name, String value, Group parent)
	{
		super(name, value, parent);
	}
	
	public Property(String name, String curVal, String defVal, Group parent)
	{
		super(name, curVal, parent);
		defaultValue(defVal);
	}
	
	/**
	 * Sets the current value of this property.
	 * 
	 * @param currentValue The new current value of this property.
	 */
	public void value(String currentValue)
	{
		currentValue(currentValue);
	}
	
	/**
	 * Sets the current and default values of this property.
	 * 
	 * @param currentValue The new current value of this property.
	 * @param defaultValue The new default value fo this property.
	 */
	public void value(String currentValue, String defaultValue)
	{
		currentValue(currentValue);
		defaultValue(defaultValue);
	}
	
	/**
	 * Sets the ID of the current property and returns the parent group.
	 * 
	 * @param id The ID of the current property.
	 * @return   The parent group
	 */
	public Group id(String id)
	{
		setID(id);
		return getParent();
	}
	
}