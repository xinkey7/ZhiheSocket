package com.example.tecpie.ZhiheSocket.pojo;

/**
 * Created by xsy35 on 2017/12/13.
 */

public class FBoxLoginBean {
    private String token_type;
    private int expires_in;
    private String refresh_token;
    private String access_token;
    private int status;


    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String tokenType) {
        token_type = tokenType;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    public String toString() {
        return "FBoxLoginBean [access_token=" + access_token + ", expires_in="
                + expires_in + ", refresh_token=" + refresh_token + ", status="
                + status + ", token_type=" + token_type + "]";
    }
}
