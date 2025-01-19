package net.nemisolv.productservice.exception;


import javax.naming.AuthenticationException;

public class OAuth2AuthenticationProcessionException extends AuthenticationException {
public OAuth2AuthenticationProcessionException(String msg) {
    super(msg);
}

public OAuth2AuthenticationProcessionException(String msg,Throwable t) {super(msg);}




}