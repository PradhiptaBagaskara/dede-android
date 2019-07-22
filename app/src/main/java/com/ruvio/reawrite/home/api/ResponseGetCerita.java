package com.ruvio.reawrite.home.api;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseGetCerita{

	@SerializedName("msg")
	private String msg;

	@SerializedName("result")
	private List<GetCeritaItem> result;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setResult(List<GetCeritaItem> result){
		this.result = result;
	}

	public List<GetCeritaItem> getResult(){
		return result;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetCerita{" + 
			"msg = '" + msg + '\'' + 
			",result = '" + result + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}