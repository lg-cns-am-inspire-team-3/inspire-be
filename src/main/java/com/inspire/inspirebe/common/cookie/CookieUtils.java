package com.inspire.inspirebe.common.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtils {
    public Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        return Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals(name))
                        .findFirst());
    }

    public void addCookie(HttpServletResponse response, CookieSpec cookieSpec) {
        response.addHeader("Set-Cookie", cookieSpec.toString());
    }

    public void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain, String path) {
        Optional.ofNullable(request.getCookies())
                .ifPresent(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> cookie.getName().equals(name))
                        .forEach(cookie -> {
                            cookie.setValue("");
                            cookie.setDomain(domain);
                            cookie.setPath(path);
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }));
    }
}