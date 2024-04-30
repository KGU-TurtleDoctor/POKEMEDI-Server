package com.turtledoctor.kgu.error.DTO;

public class ValidException extends Exception{
    private final int ERR_CODE;
    public ValidException(String msg){
        this(msg,100);
    }

    public ValidException(String msg, int err_code){
        super(msg);
        ERR_CODE = err_code;
    }
    public int getErrCode(){
        return ERR_CODE;
    }

}
