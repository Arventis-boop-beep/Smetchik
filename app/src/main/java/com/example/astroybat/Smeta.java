/**
 * File              : Smeta.java
 * Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
 * Date              : 19.02.2022
 * Last Modified Date: 19.02.2022
 * Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */

package com.example.astroybat;

public class Smeta {
    public String uuid;
    public String title;
	public int date;
	public String zakazchik;
	public String podriadchik;
	public String raboti;
	public String obiekt;
	public String osnovaniye;
	
    public Smeta(
			String uuid,
			String title,
			int date,
			String zakazchik,
			String podriadchik,
			String raboti,
			String obiekt,
			String osnovaniye
	){
		this.uuid = uuid;
		this.title = title;
		this.date = date;
		this.zakazchik = zakazchik;
		this.podriadchik = podriadchik;
		this.raboti = raboti;
		this.obiekt = obiekt;
		this.osnovaniye = osnovaniye;
    }

	public String getTitle() {
		return title;
	}

}
