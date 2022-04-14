package br.com.todoserver.todoapp.responses;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlRootElement
public class SuccessResponse {
    Object data;

    public SuccessResponse(Object data) {
        this.data = data;
    }
}
