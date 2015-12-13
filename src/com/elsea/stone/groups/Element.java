package com.elsea.stone.groups;

import java.util.ArrayList;
import java.util.List;

public class Element
{
	private Group parent;
	private List<Element> children;
	
	private String name;
	private String id;
	private String currentValue;
	private String defaultValue;
	
	private boolean changed = false;
	
	public Element(String name, String value, Group parent)
	{
		this.name         = name;
		this.currentValue = value;
		this.defaultValue = value;
		this.parent       = parent;
	}
	
	/**
	 * Adds a child to an element. Ignores the addition if this method is
	 * performed on a property element. Triggers change.
	 * 
	 * @param element The element to be added as a child.
	 */
	public void addChild(Element element)
	{
		if (this instanceof Property)
		{
			System.out.println("Warning: Adding a child to a property is not allowed.");
			return;
		}
		
		if (children == null) children = new ArrayList<>();
		children.add(element);
		changed = true;
	}
	
	public List<Element> getChildren() { return children; }
	
	/**
	 * Displays the group and its children as a tree. Does not
	 * show parent element.
	 */
	public void show()
	{
		print(0, 0);
	}
	
	/**
	 * The lower level recursive method that allows the show
	 * method to recurse through the  children  elements and
	 * show each one with a different indentation.
	 * 
	 * @param level The level of indentation to begin at
	 */
	protected void print(int level, int spaces)
	{
		spaces(level);
		
		if (this instanceof Group)
		{
			System.out.printf("|--> Group::%s ", name);
			
			if (id != null && !id.equalsIgnoreCase("null"))
				System.out.printf("#%s", id);
			
			System.out.println();
			
			if (this.hasChildren())
			{
				int longest = 0;
				
				// Determine longest child name to align equals operators
				
				for (Element e : children)
				{
					if (e.name.length() > longest) longest = e.name.length();
				}
				
				// Print all children
				
				for (Element e : children) {
					e.print(level + 1, longest);
				}
			}
		}
		else
		{
			System.out.printf("|> Property::%s", name);
			
			int width = spaces - this.getName().length();
			
			if (id != null && !id.equalsIgnoreCase("null"))
				System.out.printf("#%s", id);
			
			// Print calculated spaces to align equals operators
			for (int i = 0; i < width; i++) System.out.print(" ");
			
			System.out.printf(" = \"%s\" \n", currentValue, defaultValue);
			
			
		}
		
	}
	
	private void spaces(int level)
	{
		for (int i = 0; i < level ; i++) System.out.print("    ");
	}
	
	/**
	 * Indicates whether or not this item has been changed. An item
	 * is considered changed when the contents stored in the object
	 * do not match the records stored on the disk.
	 * 
	 * @return
	 */
	public boolean isChanged() { return changed; }
	
	/**
	 * Changes the parent of this element. Triggers change.
	 * 
	 * @param parent The new parent of this element
	 */
	protected void setParent(Group parent) {
		this.parent = parent;
		changed = true;
	}
	
	public Group getParent() { return parent; }
	
	/**
	 * Changes the name of this element. Triggers change.
	 * 
	 * @param name The new name of this element
	 */
	public Element name(String name) {
		this.name = name;
		changed = true;
		return this;
	}
	
	public String getName() { return name; }
	
	/**
	 * Changes the id of this element. Triggers change.
	 * 
	 * @param id The new id of this element
	 */
	protected Element setID(String id) {
		this.id = id;
		changed = true;
		return this;
	}
	
	public String getid() { return id; }
	
	/**
	 * Changes the current value of the element and triggers a change,
	 * but only if the element is a property element.
	 * 
	 * @param value The new current value of this element
	 */
	protected Element currentValue(String value)
	{
		if (this instanceof Group)
		{
			System.out.println("Warning: Setting the value of a group not allowed");
			return (Group) this;
		}
		
		this.currentValue = value;
		changed = true;
		return (Property) this;
	}
	
	public String getCurrentValue() { return currentValue; }
	
	/**
	 * Changes the default value of the element and triggers a change,
	 * but only if the element is a property element.
	 * 
	 * @param value The new default value of this element
	 */
	protected Element defaultValue(String value)
	{
		if (this instanceof Group)
		{
			System.out.println("Warning: Setting the value of a group not allowed");
			return (Group) this;
		}
		
		this.defaultValue = value;
		changed = true;
		return (Property) this;
	}
	
	public String getDefaultValue() { return defaultValue; }
	
	public boolean hasChildren()
	{
		if (children != null && children.size() > 0) return true;
		else return false;
	}
}