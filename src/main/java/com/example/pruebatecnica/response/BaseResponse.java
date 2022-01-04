package com.example.pruebatecnica.response;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BaseResponse<T> {
	private Integer status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	private String message;
	
	private List<String> errors;
	
    private T data;
    
    @SuppressWarnings("rawtypes")
	public static BaseResponse successNoData(String message) {
        return BaseResponse.builder()
        		.status(HttpStatus.OK.value())
        		.message(message)
        		.timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> BaseResponse<T> successWithData(T data, String message) {
        return BaseResponse.<T>builder()
        		.status(HttpStatus.OK.value())
        		.message(message)
        		.timestamp(LocalDateTime.now())
                .data(data)
                .build();
    }
           
    @SuppressWarnings("rawtypes")
    public static BaseResponse error(HttpStatus statusError, String messageError, List<String> errorsList) {
        return BaseResponse.builder()
        		.status(statusError.value())
        		.timestamp(LocalDateTime.now())
        		.message(messageError)
        		.errors(errorsList)
                .build();
    }
}
