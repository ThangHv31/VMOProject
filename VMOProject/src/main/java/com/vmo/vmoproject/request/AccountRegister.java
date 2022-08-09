package com.vmo.vmoproject.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegister {
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> roleName;
}
