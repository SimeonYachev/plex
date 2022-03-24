package com.scalefocus.java.simeonyachev.services;

import com.scalefocus.java.simeonyachev.security.requests.AuthenticationRequest;
import com.scalefocus.java.simeonyachev.security.responses.AuthenticationResponse;

public interface LoginService {

    AuthenticationResponse login(AuthenticationRequest request);
}
