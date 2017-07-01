package org.ledgerty.services.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Héctor on 25/06/2017.
 */
public class Role implements GrantedAuthority {

    private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + roleName;
    }
}
