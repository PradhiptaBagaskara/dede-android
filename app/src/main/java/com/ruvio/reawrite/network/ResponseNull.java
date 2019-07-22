package com.ruvio.reawrite.network;

import com.google.gson.annotations.SerializedName;

public class ResponseNull {
    @SerializedName("msg")
    private String msg;

    @SerializedName("result")
    private Object result;

    @SerializedName("status")
    private boolean status;

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

    public void setResult(Object result){
        this.result = result;
    }

    public Object getResult(){
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
                "ResponseNull{" +
                        "msg = '" + msg + '\'' +
                        ",result = '" + result + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}
