package com.elsea.stone.groups;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GroupSearch {
	
	private Group group;
	
	public GroupSearch(Group group)
	{
		this.group = group;
	}
	
	/**
	 * Returns the first group found with the given name. Ignores groups
	 * with duplicate names and still returns the first group.  If there
	 * are no groups to be found with that name, null is returned.
	 * 
	 * @param name The name of the group to find.
	 * @return     The first group with the specified name, or null
	 */
	public Group group(String name)
	{
		List<Element> list = filter(p -> p.getName().equals(name) && p instanceof Group);
		
		if (list != null && list.size() > 0)
		{
			if (list.size() > 1)
				System.out.println("Warning: Returning first of many groups with duplicate names");
			
			return (Group) list.get(0);
		}
		else return null;
	}
	
	/**
	 * Returns all groups with a certain name.
	 * 
	 * @param name The name of the groups to find.
	 * @return     A list of the groups with the specified name.
	 */
	public List<Group> groups(String name)
	{
		return filter(p -> p.getName().equals(name) && p instanceof Group)
				.stream()
					.map(e -> (Group) e)
					.collect(Collectors.toList());
	}
	
	/**
	 * Returns the first  property found with the  given name. Ignores properties
	 * with duplicate names and still returns the first property. If there are no
	 * properties to be found with that name, null is returned.
	 * 
	 * @param name The name of the property to find.
	 * @return     The first property with the specified name, or null
	 */
	public Property property(String name)
	{
		List<Element> list = filter(p -> p.getName().equals(name) && p instanceof Property);
		
		if (list != null && list.size() > 0)
		{
			if (list.size() > 1)
				System.out.println("Warning: Returning first of many groups with duplicate names");
			
			return (Property) list.get(0);
		}
		else return null;
	}
	
	/**
	 * Returns all properties with a certain name.
	 * 
	 * @param name The name of the properties to find.
	 * @return     A list of the properties with the specified name.
	 */
	public List<Property> properties(String name)
	{
		return filter(p -> p.getName().equals(name) && p instanceof Property)
				.stream()
					.map(e -> (Property) e)
					.collect(Collectors.toList());
	}
	
	/**
	 * Returns the first group with the specified ID. Ignores groups with
	 * duplicate IDs and returns the  first group. If there are no groups
	 * to be found with that ID, null is returned.
	 * 
	 * @param id The id of the group to find.
	 * @return   The first group with the specified id, or null
	 */
	public Group groupid(String id)
	{
		List<Element> list = filter(p -> p instanceof Group && p.getid().equals(id));
		
		if (list != null && list.size() > 0)
		{
			if (list.size() > 1) {
				System.out.println("Warning: IDs should not duplicate. You have multiple #" + id);
				System.out.println("Warning: Returning first of many groups with duplicate IDs");
			}
			
			return (Group) list.get(0);
		}
		else return null;
	}
	
	/**
	 * Returns the first property with the specified ID. Ignores properties with
	 * duplicate IDs and returns the  first property. If there are no properties
	 * to be found with that ID, null is returned.
	 * 
	 * @param id The id of the group to find.
	 * @return   The first group with the specified id, or null
	 */
	public Group propertyid(String id)
	{
		List<Element> list = filter(p -> p instanceof Property && p.getid().equals(id));
		
		if (list != null && list.size() > 0)
		{
			if (list.size() > 1) {
				System.out.println("Warning: IDs should not duplicate. You have multiple #" + id);
				System.out.println("Warning: Returning first of many properties with duplicate IDs");
			}
			
			return (Group) list.get(0);
		}
		else return null;
	}
	
	/**
	 * Tests a predicate against each element under the specified group
	 * 
	 * @param p The predicate to use
	 * @return  A list of elements that are true for the specified predicate
	 */
	public List<Element> filter(Predicate<Element> p)
	{
		return recursive_filter(new ArrayList<Element>(), p, group);
	}
	
	private List<Element> recursive_filter(List<Element> list, Predicate<Element> p, Element cur)
	{
		if (p.test(cur)) list.add(cur);

		if (cur.hasChildren())
			for (Element e : cur.getChildren()) recursive_filter(list, p, e);
		
		return list;
	}

}