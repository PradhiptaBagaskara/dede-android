package com.ruvio.reawrite.kategori.cerita;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseById{

	@SerializedName("msg")
	private String msg;

	@SerializedName("result")
	private List<ByIdItem> result;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setResult(List<ByIdItem> result){
		this.result = result;
	}

	public List<ByIdItem> getResult(){
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
			"ResponseById{" + 
			"msg = '" + msg + '\'' + 
			",result = '" + result + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}