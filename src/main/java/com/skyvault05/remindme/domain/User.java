package com.skyvault05.remindme.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    Long userId;
}
