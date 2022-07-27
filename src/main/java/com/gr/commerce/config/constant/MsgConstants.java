package com.gr.commerce.config.constant;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

public final class MsgConstants {

    public static final String RESULT_MSG							= "resultMsg";
    public static final String RESULT_CODE							= "resultCode";
    public static final String RESULT_DATA							= "data";

    public static final int RESULT_DATA_SUCCESS_INT					= 1;
    public static final int RESULT_DATA_ERROR_INT					= -1;

    public static final String RESULT_CODE_SUCCESS					= "200";
    public static final String RESULT_CODE_AUTHORIZATION_FAIL   	= "401";


    public static final String RESULT_MSG_SUCCESS					= "SUCCESS";
    public static final String AUTHORIZATION_FAILED 				= "인증 실패";
    public static final String RESULT_MSG_AUTHORIZATION_FAIL		= "AUTHORIZATION FAILED";


    public static final String USER_NOT_FOUND						= "가입되지 않은 사용자입니다. 회원가입 후 사용해주세요";
    public static final String INVALID_PASSWORD						= "비밀번호를 확인해주세요";

    public static String authenticationFailed() {

        Map resultObj = new LinkedHashMap();
        resultObj.put(RESULT_CODE, RESULT_CODE_AUTHORIZATION_FAIL);
        resultObj.put(RESULT_MSG, RESULT_MSG_AUTHORIZATION_FAIL);
        resultObj.put(RESULT_DATA, AUTHORIZATION_FAILED);

        Gson gson = new Gson();
        return gson.toJson(resultObj);
    }

    public static String loginSuccessMessage() {
        Map resultObj = new LinkedHashMap();
        resultObj.put(RESULT_CODE, RESULT_CODE_SUCCESS);
        resultObj.put(RESULT_MSG, RESULT_MSG_SUCCESS);
        resultObj.put(RESULT_DATA, RESULT_DATA_SUCCESS_INT);

        Gson gson = new Gson();
        return gson.toJson(resultObj);
    }

    public static String loginFailedMessage() {
        Map resultObj = new LinkedHashMap();
        resultObj.put(RESULT_CODE, RESULT_CODE_SUCCESS);
        resultObj.put(RESULT_MSG, RESULT_MSG_SUCCESS);
        resultObj.put(RESULT_DATA, RESULT_DATA_ERROR_INT);

        Gson gson = new Gson();
        return gson.toJson(resultObj);
    }

    public static String userNotFound() {
        Map resultObj = new LinkedHashMap();
        resultObj.put(RESULT_CODE, RESULT_CODE_SUCCESS);
        resultObj.put(RESULT_MSG, USER_NOT_FOUND);
        resultObj.put(RESULT_DATA, RESULT_DATA_ERROR_INT);

        Gson gson = new Gson();
        return gson.toJson(resultObj);
    }

    public static String invalidPassword() {
        Map resultObj = new LinkedHashMap();
        resultObj.put(RESULT_CODE, RESULT_CODE_SUCCESS);
        resultObj.put(RESULT_MSG, INVALID_PASSWORD);
        resultObj.put(RESULT_DATA, RESULT_DATA_ERROR_INT);

        Gson gson = new Gson();
        return gson.toJson(resultObj);
    }



}
