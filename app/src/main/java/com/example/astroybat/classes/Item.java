/**
 * File              : Item.java
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 22.03.2022
 * Last Modified Date: 24.03.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */
package com.example.astroybat.classes;

public class Item {
	public String uuid;
	public String smeta_uuid;
	public int    id;
	public int    parent;
	public int    number;
	public int    index;
	public String title;
	public String unit;
	public int    price;
	public int    count;

	public Item(
		String uuid,
		String smeta_uuid,
		int    id,
		int    parent,
		int    number,
		int    index,
		String title,
		String unit,
		int    price,
		int    count
	)
	{
		this.uuid       = uuid;
		this.smeta_uuid = smeta_uuid;
		this.id         = id;
		this.parent     = parent;
		this.number     = number;
		this.index      = index;
		this.title      = title;
		this.unit       = unit;
		this.price      = price;
		this.count      = count;
	}
}
