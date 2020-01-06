package com.ruvio.reawrite.profile.api;

import com.google.gson.annotations.SerializedName;

public class ResponseProfile{

	@SerializedName("msg")
	private String msg;

	@SerializedName("result")
	private Result result;

	@SerializedName("status")
	private boolean status;

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return msg;
	}

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
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
			"ResponseProfile{" + 
			"msg = '" + msg + '\'' + 
			",result = '" + result + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}