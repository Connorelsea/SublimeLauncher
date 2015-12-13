package com.elsea.stone.groups;

public class Group extends Element
{
	private GroupSearch search;
	
	public Group(String name, Group parent)
	{
		super(name, null, parent);
	}
	
	public Group(String name)
	{
		super(name, null, null);
	}
	
	public Group()
	{
		super("parent", null, null);
	}
	
	/**
	 * If this object is null, the most recently created
	 * element was the group. If this object is not null,
	 * the most recently created  object is  the current
	 * group. This is used in identifying  which element
	 * to apply ID commands to.
	 */
	private Property recent;
	
	/**
	 * Returns a Group Search object that allows the group
	 * to be traversed and searched.
	 * 
	 * @return A Group Search object
	 */
	public GroupSearch search()
	{
		return (search == null) ? (search = new GroupSearch(this)) : search;
	}
	
	/**
	 * Creates a new group as a child of  the current  group and
	 * returns the newly made child group, effectively switching
	 * to the new child group during template creation.
	 * 
	 * @param name The name of the group to create
	 * @return     The created group
	 */
	public Group group(String name)
	{
		Group group = new Group(name, this);
		recent = null;
		addChild(group);
		return group;
	}
	
	/**
	 * Creates a new property with the given name and sets both the
	 * current and default values as the  same  value. The  current
	 * parent group is returned.
	 * 
	 * @param name  The name of the new property
	 * @param value The default and current value of the new property
	 * @return      The current parent group
	 */
	public Group property(String name, String value)
	{
		Property property = new Property(name, value, this);
		recent = property;
		addChild(property);
		return this;
	}
	
	/**
	 * Sets the ID of the current group and returns the current group.
	 * 
	 * @param id The ID of the current group.
	 * @return   The group
	 */
	public Group id(String id)
	{
		if (recent == null) setID(id);
		else recent.setID(id);
		
		return this;
	}
	
	/**
	 * Returns the parent of this group, effectively ending
	 * the possibility for the addition of  children during
	 * template creation.
	 * 
	 * @return The parent of the current group
	 */
	public Group end()
	{
		// Recent isn't used after group is done
		recent = null;
		return getParent();
	}
	
}