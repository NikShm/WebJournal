package com.webjournal.security.payload.response;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class MessageResponse
 * @since 11/16/2022 - 12.14
 **/
public class MessageResponse {
    private String response;

    public MessageResponse() {
    }

    public MessageResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "response='" + response + '\'' +
                '}';
    }
}
