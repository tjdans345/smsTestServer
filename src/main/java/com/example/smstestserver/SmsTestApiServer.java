package com.example.smstestserver;

import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.Coolsms;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Random;

@RequiredArgsConstructor
@RestController
public class SmsTestApiServer {

    /*
     * 서버에서 받은 API_KEY, API_SECRET를 입력해주세요.
     */
    String api_key = "";
    String api_secret = "";
    Message coolsms = new Message(api_key, api_secret);

    @PostMapping
    public String smsSend(@RequestParam String phoneNumber) {

        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int i=0; i<6; i++) {
            // 0 ~ 9 까지의 무작위 int 값 리턴
            randomNumber.append(random.nextInt(10));
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("to", phoneNumber); // 수신번호

//       * 10월 16일 이후로 발신번호 사전등록제로 인해 등록된 발신번호로만 문자를 보내실 수 있습니다. 바로가기
        params.put("from", "01086691007"); // 발신번호
        params.put("text", "데따 휴대폰인증 테스트 메시지 : 인증번호는" + "["+randomNumber+"]" + "입니다.");
        params.put("type", "sms"); // 문자 타입

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        return randomNumber.toString();

    }






}
