package com.inspire.inspirebe.common.cookie;

import lombok.Builder;
import org.springframework.util.Assert;

import java.util.Set;

public class CookieSpec {
    private final String name;
    private final String value;
    private final int maxAge;
    private final String domain;
    private final String path;
    private final boolean httpOnly;
    private final boolean secure;
    private final String sameSite;

    @Builder
    public CookieSpec(String name,
                      String value,
                      Integer maxAge,
                      String domain,
                      String path,
                      Boolean httpOnly,
                      Boolean secure,
                      String sameSite) {

        Assert.notNull(name, "name must not be null");
        Assert.state(
                sameSite == null || Set.of("Strict", "Lax", "None").contains(sameSite),
                "SameSite must be one of Strict, Lax, or None. Found: " + sameSite
        );
        this.name = name;
        this.value = value != null ? value : "";
        this.maxAge = maxAge != null ? maxAge : -1; // optional, default : -1 (session cookie)
        this.domain = domain; // if null, it delegates to a servlet (the domain of HttpServletRequest)
        this.path = path; // if null, it delegates to a servlet (the path of HttpServletRequest)
        this.httpOnly = httpOnly != null ? httpOnly : false; // default : false
        this.secure = secure != null ? secure : false; // default : false
        this.sameSite = sameSite; // if null, it delegates to a browser (in most cases, Lax)
    }

    @Override
    public String toString() {
        StringBuilder cookieString = new StringBuilder();
        cookieString.append(this.name).append("=").append(this.value);
        if (this.path != null) cookieString.append("; Path=").append(this.path);
        if (this.domain != null) cookieString.append("; Domain=").append(this.domain);
        if (this.maxAge >= 0) cookieString.append("; Max-Age=").append(this.maxAge);
        if (this.httpOnly) cookieString.append("; HttpOnly");
        if (this.secure) cookieString.append("; Secure");
        if (this.sameSite != null) cookieString.append("; SameSite=").append(this.sameSite);
        return cookieString.toString();
    }
}