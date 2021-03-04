package com.example.utilsdemo.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpClientUtil {

    public static void sentHttpClient(String ipURL, String json) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("http://172.10.18.83:10080" + ipURL)
                .post(body)
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "b1ae3565-90ff-4345-8980-1d124141b9e2")
                .addHeader("Connection", "Keep-Alive")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            response.body().close();

            log.info(response.toString());
        } catch (IOException e) {
            log.error(e.toString());
//        }finally {
//            if (response != null && response.body() != null) {
//                response.body().close();
//            }
//        }
        }
    }

    public static Boolean sentGPGHttpClient(String ipURL, String json) {
        //OkHttpClient client = new OkHttpClient();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout
                .build();
        Boolean statue = false;
        MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url("http://172.10.18.88:5590" + ipURL)
                .post(body)
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "b1ae3565-90ff-4345-8980-1d124141b9e2")
                .addHeader("Connection", "Keep-Alive")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            log.info(responseBody);
            if(responseBody.equals("yes")){
                statue = true;
            }

            log.info(response.toString());
        } catch (IOException e) {
            log.error(e.toString());
//        }finally {
//            if (response != null && response.body() != null) {
//                response.body().close();
//            }
//        }
        }
        return statue;
    }

}
