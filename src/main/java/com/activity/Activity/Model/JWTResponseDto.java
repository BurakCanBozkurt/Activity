package com.activity.Activity.Model;

import lombok.Data;

@Data
public class JWTResponseDto {
    private  String token;
    private  String tokenType="Bearer";
}
