package com.ruvio.reawrite.login.api;

import com.google.gson.annotations.SerializedName;

public class UserItem {

	@SerializedName("foto_user")
	private String fotoUser;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("nama_user")
	private String namaUser;

	@SerializedName("auth_key")
	private String authKey;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public void setFotoUser(String fotoUser){
		this.fotoUser = fotoUser;
	}

	public String getFotoUser(){
		return fotoUser;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setNamaUser(String namaUser){
		this.namaUser = namaUser;
	}

	public String getNamaUser(){
		return namaUser;
	}

	public void setAuthKey(String authKey){
		this.authKey = authKey;
	}

	public String getAuthKey(){
		return authKey;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"UserItem{" +
			"foto_user = '" + fotoUser + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",nama_user = '" + namaUser + '\'' + 
			",auth_key = '" + authKey + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}