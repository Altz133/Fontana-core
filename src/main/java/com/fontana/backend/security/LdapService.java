package com.fontana.backend.security;

import com.fontana.backend.config.LdapConfig;
import com.fontana.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
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
public class LdapService {

    private final LdapConfig ldapConfig;
    private final UserService userService;

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

        Attributes attributes = context.getAttributes(env.get(Context.SECURITY_PRINCIPAL));
        List<Object> ldapDetails = new ArrayList<Object>();
        for (NamingEnumeration attributeEnumeration = attributes.getAll(); attributeEnumeration.hasMore();) {
            Attribute attr = (Attribute) attributeEnumeration.next();
            ldapDetails.add(attr.get());
        }

        userService.extractUserFromLDAP(ldapDetails);
        context.close();
    }
}
