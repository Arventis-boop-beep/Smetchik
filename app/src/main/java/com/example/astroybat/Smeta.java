/*
  File              : Smeta.java
  Author            : Igor V. Sementsov <ig.kuzm@gmail.com>
  Date              : 19.02.2022
  Last Modified Date: 19.02.2022
  Last Modified By  : Igor V. Sementsov <ig.kuzm@gmail.com>
 */

package com.example.astroybat;

import android.os.Parcel;
import android.os.Parcelable;

public class Smeta implements Parcelable {
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

	protected Smeta(Parcel in) {
		uuid = in.readString();
		title = in.readString();
		date = in.readInt();
		zakazchik = in.readString();
		podriadchik = in.readString();
		raboti = in.readString();
		obiekt = in.readString();
		osnovaniye = in.readString();
	}

	public static final Creator<Smeta> CREATOR = new Creator<Smeta>() {
		@Override
		public Smeta createFromParcel(Parcel in) {
			return new Smeta(in);
		}

		@Override
		public Smeta[] newArray(int size) {
			return new Smeta[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(uuid);
		parcel.writeString(title);
		parcel.writeInt(date);
		parcel.writeString(zakazchik);
		parcel.writeString(podriadchik);
		parcel.writeString(raboti);
		parcel.writeString(obiekt);
		parcel.writeString(osnovaniye);
	}
}
