package com.gr.commerce.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long usrSeq;
    private String usrTpCd;
    private String usrNm;
    private String usrEml;
    private String usrPw;
    private String usrId;
    private String usrPhnNo;
    private Date usrSgnUpDt;
    private String updUsr;
    private Date updDt;
}
