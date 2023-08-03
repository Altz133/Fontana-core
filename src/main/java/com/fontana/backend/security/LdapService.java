package com.fontana.backend.security;

import com.fontana.backend.config.LdapConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LdapService {

    private final LdapConfig ldapConfig;

    public boolean isLdapRegistered(String username, String password) {
        return isUserValid(username, password);
    }

    private boolean isUserValid(String username, String password) {
        try {
            Hashtable<String, String> env = ldapConfig.getLdapConfig(username, password);
            getInitialDirContext(env);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void getInitialDirContext(Hashtable<String, String> env) throws NamingException {

        //FIXME temporary solution

        DirContext context = new InitialDirContext(env);

        Attributes attrs = context.getAttributes(env.get(Context.SECURITY_PRINCIPAL));
        List<String> l = new ArrayList<String>();
        for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
            Attribute attr = (Attribute) ae.next();
            l.add(attr.getID());
        }

        context.close();
    }
}