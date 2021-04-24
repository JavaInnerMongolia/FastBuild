package com.fast_build_auth.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -7797183521247423117L;

    private Integer id;

    private String userName;

    private String password;
}