package com.scalefocus.java.simeonyachev.security.responses;

import java.io.Serializable;
import java.util.Objects;

public class AuthenticationResponse implements Serializable {

    private String jwt;

    public AuthenticationResponse() {
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AuthenticationResponse)) {
            return false;
        }
        AuthenticationResponse that = (AuthenticationResponse) obj;
        return Objects.equals(jwt, that.jwt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwt);
    }
}
