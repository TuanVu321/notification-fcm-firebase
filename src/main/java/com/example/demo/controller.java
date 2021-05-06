package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class controller {
    @GetMapping("/notification")
    public void sendMessage() throws JSONException {
        String key = "dYjVOtJgSEGxP_ZMsGOAdC:APA91bEjtzEjyk0FuOj_bPgQ30OpWzf_ytUNA0X22Vv0cicwB3Lw4CeXjV2w2aN3ptrhdeLz0XnxpDd-yuG_688rD89tRoB8UH1pHXKuok3fKkpXopdyPwaCRWnGMmkUuUy-q4s_j94k";
        List keysList = new ArrayList();
        keysList.add(key);
        this.sendPushNotification(keysList, "title message", "thu xem co chay khong?");
    }


    private final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
    private final String FIREBASE_SERVER_KEY = "AAAAm9H_iSI:APA91bHV0PfDhZrvK0EI_KGHVoEHJFvzgx7D8wviih6IMPUQnEX0hUEuIYiiT4EvixXX-wYTu0jxDlW0RT72bn_C178Wx3QH-Q1Xy4nhoYcpzNkd4WAiOPnmNTtXlN-onGKNOc7YjVI5";


    public void sendPushNotification(List<String> keys, String messageTitle, String message) throws JSONException {


        JSONObject msg = new JSONObject();

        msg.put("title", messageTitle);
        msg.put("body", message);
//        msg.put("notificationType", "Test");
        String response = callToFcmServer(msg, keys);
//        keys.forEach(key -> {
//            System.out.println("\nCalling fcm Server >>>>>>>");
//            String response = null;
//            try {
//                response = callToFcmServer(msg, key);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Got response from fcm Server : " + response + "\n\n");
//        });

    }

    private String callToFcmServer(JSONObject message, List<String> receiverFcmKey) throws JSONException {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap httpHeaders = new LinkedMultiValueMap();
        httpHeaders.set("Authorization", "key=" + FIREBASE_SERVER_KEY);
        httpHeaders.set("Content-Type", "application/json");

        JSONObject json = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();

//        json.put("data", message);
        json.put("notification", message);
        json.put("registration_ids", new JSONArray(receiverFcmKey));

        System.out.println("Sending :" + json.toString());

        HttpEntity<String> httpEntity = new HttpEntity<String>(json.toString(), httpHeaders);
        return restTemplate.postForObject(FIREBASE_API_URL, httpEntity, String.class);
    }
}
