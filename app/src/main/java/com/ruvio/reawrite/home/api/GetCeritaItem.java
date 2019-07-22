package com.ruvio.reawrite.home.api;

import com.google.gson.annotations.SerializedName;

public class GetCeritaItem {

	@SerializedName("id_cerita")
	private String idCerita;

	@SerializedName("img")
	private String img;

	@SerializedName("diskripsi")
	private String diskripsi;

	@SerializedName("judul")
	private String judul;

	@SerializedName("isi")
	private String isi;

	@SerializedName("username")
	private String username;

	@SerializedName("nama_kategori")
	private String namaKategori;

	public void setIdCerita(String idCerita){
		this.idCerita = idCerita;
	}

	public String getIdCerita(){
		return idCerita;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setDiskripsi(String diskripsi){
		this.diskripsi = diskripsi;
	}

	public String getDiskripsi(){
		return diskripsi;
	}

	public void setJudul(String judul){
		this.judul = judul;
	}

	public String getJudul(){
		return judul;
	}

	public void setIsi(String isi){
		this.isi = isi;
	}

	public String getIsi(){
		return isi;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setNamaKategori(String namaKategori){
		this.namaKategori = namaKategori;
	}

	public String getNamaKategori(){
		return namaKategori;
	}

	@Override
 	public String toString(){
		return 
			"GetCeritaItem{" +
			"id_cerita = '" + idCerita + '\'' + 
			",img = '" + img + '\'' + 
			",diskripsi = '" + diskripsi + '\'' + 
			",judul = '" + judul + '\'' + 
			",isi = '" + isi + '\'' + 
			",username = '" + username + '\'' + 
			",nama_kategori = '" + namaKategori + '\'' + 
			"}";
		}
}