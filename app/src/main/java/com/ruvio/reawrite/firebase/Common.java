package com.ruvio.reawrite.firebase;


public class Common {

    //public static String currentToken = "";

    private static String baseUrl = "https://fcm.googleapis.com/";

    public static NotifService getFCMClient(){

        return RetroClient.getClient(baseUrl).create(NotifService.class);
    }
}