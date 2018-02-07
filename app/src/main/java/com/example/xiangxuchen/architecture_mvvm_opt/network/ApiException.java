package com.example.xiangxuchen.architecture_mvvm_opt.network;

/**
 * Created by pngfi on 2017/11/7.
 */

public class ApiException extends Exception{


    private int code;

    public ApiException(String message, int code) {
        super(message);
        this.code=code;
    }

    public ApiException(String message){
        super(message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
