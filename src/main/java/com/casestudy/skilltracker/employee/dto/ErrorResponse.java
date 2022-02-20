package com.casestudy.skilltracker.employee.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {
    private int code;
    private String message;
}
