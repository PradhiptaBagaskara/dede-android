package com.ruvio.reawrite.kategori.api;

import com.google.gson.annotations.SerializedName;

public class KategoriItem {

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("id")
	private int id;

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"KategoriItem{" +
			"kategori = '" + kategori + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}