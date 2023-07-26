package com.todo.backend.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtTokenResponse {
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiration;
    private Date accessTokenIssued;
    private Date refreshTokenExpiration;
    private Date refreshTokenIssued;
}
