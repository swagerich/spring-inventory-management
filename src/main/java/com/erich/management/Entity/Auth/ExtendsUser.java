package com.erich.management.Entity.Auth;

import com.erich.management.Entity.User;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
public class ExtendsUser  {

    private  User user;
    private Long idEnterpride;
    /*public ExtendsUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }*/

    /*public ExtendsUser(String username, String password, Long idEnterpride ,Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.idEnterpride = idEnterpride;
    }*/

}
