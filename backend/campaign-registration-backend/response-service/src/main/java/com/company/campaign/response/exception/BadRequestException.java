package com.company.campaign.response.exception;


public class BadRequestException extends RuntimeException{
    
    public BadRequestException(String message) {
        super(message);
    }

}
