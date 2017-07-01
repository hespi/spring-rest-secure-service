package org.ledgerty.services.security;

import org.jasypt.util.text.BasicTextEncryptor;
import org.ledgerty.common.GuidUtils;
import org.ledgerty.common.ValidationUtils;
import org.ledgerty.common.exceptions.BaseException;
import org.ledgerty.common.exceptions.InvalidParametersException;
import org.ledgerty.dao.User;
import org.ledgerty.services.exceptions.InvalidTokenException;
import org.ledgerty.services.interfaces.ISessionService;
import org.ledgerty.services.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Héctor on 17/06/2017.
 */
public class LedgertyRequestFilter extends OncePerRequestFilter {

    private ISessionService sessionService;

    /** OncePerRequestFilter MEMBERS **/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        sessionService = new SessionService(request, response);
        try {
            doValidateRequestToken(request, response);
            filterChain.doFilter(request, response);
        }
        catch (BaseException ex) {
            response.sendError(ex.getStatusCode(), ex.getMessage());
        }
    }

    private void doValidateRequestToken(HttpServletRequest request, HttpServletResponse response) {
        String encryptedToken = getRequestEncryptedToken(request);
        if (encryptedToken != null) {
            SecurityContextHolder.getContext().setAuthentication(sessionService.decryptToken(encryptedToken));
        } else {
            throw new InvalidTokenException("You must provide your session token");
        }
    }

    private String getRequestEncryptedToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.substring("Bearer ".length()) : null;
    }


}
