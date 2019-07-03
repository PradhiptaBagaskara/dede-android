package com.ruvio.reawrite.kategori.cerita;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseById{

	@SerializedName("msg")
	private boolean msg;

	@SerializedName("result")
	private List<ByIdItem> result;

	@SerializedName("order_id")
	private String orderId;

	public void setMsg(boolean msg){
		this.msg = msg;
	}

	public boolean isMsg(){
		return msg;
	}

	public void setResult(List<ByIdItem> result){
		this.result = result;
	}

	public List<ByIdItem> getResult(){
		return result;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	@Override
 	public String toString(){
		return 
			"ResponseById{" + 
			"msg = '" + msg + '\'' + 
			",result = '" + result + '\'' + 
			",order_id = '" + orderId + '\'' + 
			"}";
		}
}