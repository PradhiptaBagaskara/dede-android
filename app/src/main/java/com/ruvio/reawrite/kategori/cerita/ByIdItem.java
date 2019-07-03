package com.ruvio.reawrite.kategori.cerita;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ByIdItem {

	@SerializedName("id_cerita")
	private int idCerita;

	@SerializedName("Diskripsi")
	private String diskripsi;

	@SerializedName("img")
	private String img;

	@SerializedName("penulis")
	private String penulis;

	@SerializedName("id_kategori")
	private String idKategori;

	@SerializedName("isi")
	private String isi;

	public void setIdCerita(int idCerita){
		this.idCerita = idCerita;
	}

	public int getIdCerita(){
		return idCerita;
	}

	public void setDiskripsi(String diskripsi){
		this.diskripsi = diskripsi;
	}

	public String getDiskripsi(){
		return diskripsi;
	}

	public void setImg(String img){
		this.img = img;
	}

	public String getImg(){
		return img;
	}

	public void setPenulis(String penulis){
		this.penulis = penulis;
	}

	public String getPenulis(){
		return penulis;
	}

	public void setIdKategori(String idKategori){
		this.idKategori = idKategori;
	}

	public String getIdKategori(){
		return idKategori;
	}

	public void setIsi(String isi){
		this.isi = isi;
	}

	public String getIsi(){
		return isi;
	}

	@Override
 	public String toString(){
		return 
			"KategoriItem{" +
			"id_cerita = '" + idCerita + '\'' + 
			",diskripsi = '" + diskripsi + '\'' + 
			",img = '" + img + '\'' + 
			",penulis = '" + penulis + '\'' + 
			",id_kategori = '" + idKategori + '\'' + 
			",isi = '" + isi + '\'' + 
			"}";
		}
}