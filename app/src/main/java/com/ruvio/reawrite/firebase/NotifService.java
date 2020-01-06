package com.ruvio.reawrite.firebase;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface NotifService {
    @Headers({
                "Content-Type:application/json",
                "Authorization:key=AAAAdKCLzws:APA91bGgztT3sTgMdwfZKE47XaHqzyxTNBUE61Wmg5EvQmIwqk9TTs5eamSnkXZu43D2BOKe6EeGkgnDRefQJHlpTq8fa_o7x8GlRkQLX_U9KWm5W8QH-U0Hq6khpGUPw9lixZb4BEXc"
    })

        @POST("fcm/send")
        Call<Respon> sendNotification(@Body Sender body);
    }

