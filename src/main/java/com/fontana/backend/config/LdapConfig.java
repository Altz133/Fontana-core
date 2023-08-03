package com.fontana.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.naming.Context;
import java.util.Hashtable;

@Configuration
@RequiredArgsConstructor
public class LdapConfig {

    @Value("${ldap.factory}")
    private String initialContextFactoryValue;

    @Value("${ldap.url}")
    private String ldapProviderUrl;

    @Value("${ldap.authentication}")
    private String ldapSecurityAuthentication;

    @Value("${ldap.sufix}")
    private String ldapSecurityPrincipalSufix;

    /**
     * This method prepares a Hashtable containing the necessary properties for setting up the Initial Context
     * for accessing the LDAP server with the provided username and password.
     *
     * @param username of the user attempting to establish the LDAP connection given from client via http request.
     * @param password corresponding to the provided user password.
     * @return a Hashtable with LDAP configuration.
     */
    public Hashtable<String, String> getLdapConfig(String username, String password) {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactoryValue);

        env.put(Context.PROVIDER_URL, ldapProviderUrl);
        env.put(Context.SECURITY_AUTHENTICATION, ldapSecurityAuthentication);
        env.put(Context.SECURITY_PRINCIPAL, createSecurityPrincipal(username));
        env.put(Context.SECURITY_CREDENTIALS, password);
        return env;
    }

    private String createSecurityPrincipal(String username) {
        return "uid=" + username + ldapSecurityPrincipalSufix;
    }
}
