package com.gamemetricbackend.global.aop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamemetricbackend.domain.dib.entity.Dib;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<E> {

    /**
     * status response code with message ,response type. data
     */
    private String status;
    private String msg;
    private E data;

    public ResponseDto(E data) {
        this.data = data;
    }

    public ResponseDto(String status, String msg, E data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * sending data on success
     *
     * @param data data
     * @param <T>  response data type
     * @return CommonResponseDto
     */
    public static <T> ResponseDto<Page<Dib>> success(T data) {
        return new ResponseDto<>(data);
    }

    /**
     * 실패시 상태코드와 메세지만 전달
     *
     * @param status status type
     * @param msg    message
     * @return CommonResponseDto
     */
    public static ResponseDto fail(String status, String msg) {
        return new ResponseDto<>(status, msg, null);
    }
}
