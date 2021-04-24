package com.fast_build_auth.dto;

import org.springframework.http.HttpStatus;

public class RetDTO<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> RetDTO<T> success() {
        RetDTO retDTO = new RetDTO();
        retDTO.setCode(HttpStatus.OK.value());
        return retDTO;
    }

    public static <T> RetDTO<T> success(String msg) {
        RetDTO retDTO = new RetDTO();
        retDTO.setCode(HttpStatus.OK.value());
        retDTO.setMsg(msg);
        return retDTO;
    }

    public static <T> RetDTO<T> success(T data) {
        RetDTO retDTO = new RetDTO();
        retDTO.setCode(HttpStatus.OK.value());
        retDTO.setData(data);
        return retDTO;
    }

    public static <T> RetDTO<T> success(String msg, T data) {
        RetDTO retDTO = new RetDTO();
        retDTO.setCode(HttpStatus.OK.value());
        retDTO.setMsg(msg);
        retDTO.setData(data);
        return retDTO;
    }

    public static <T> RetDTO<T> error(String msg) {
        RetDTO retDTO = new RetDTO();
        retDTO.setCode(HttpStatus.BAD_REQUEST.value());
        retDTO.setMsg(msg);
        return retDTO;
    }

}
