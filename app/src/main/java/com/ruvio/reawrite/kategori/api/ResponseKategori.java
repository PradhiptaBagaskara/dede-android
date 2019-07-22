package com.ruvio.reawrite.kategori.api;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseKategori{

	@SerializedName("msg")
	private boolean msg;

	@SerializedName("result")
	private List<KategoriItem> result;

	public void setMsg(boolean msg){
		this.msg = msg;
	}

	public boolean isMsg(){
		return msg;
	}

	public void setResult(List<KategoriItem> result){
		this.result = result;
	}

	public List<KategoriItem> getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"ResponseKategori{" + 
			"msg = '" + msg + '\'' + 
			",result = '" + result + '\'' + 
			"}";
		}
}