package com.example.photosnsproject.Notifications;

import com.example.smartmarker.Notifications.MyResponse;
import com.example.smartmarker.Notifications.NotificationData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAJnkLQ1A:APA91bG1B1AZWegNZiL8cf1_zGx79PsxQ_9D2KpHULg-pMLR9nCvnmlJiyg6drctgRwMRa0EFIpO1HqPgWUiq5pMqRyvP1M9EhpB7eu6eGtBed6EBOrTGZZ6YMpl32kN_7H5kFrlbh8f"

                    //서버키
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationData body);
}
