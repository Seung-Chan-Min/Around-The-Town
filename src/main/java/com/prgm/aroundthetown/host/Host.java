package com.prgm.aroundthetown.host;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "host")
@Builder
public class Host {

    @Id
    @Column(name = "id")
    Long id;

    @Column(name = "host_name")
    String hostName;

    @Column(name = "host_email")
    String hostEmail;

    @Column(name = "host_phone_number")
    String hostPhoneNumber;

    protected Host() {

    }
}
