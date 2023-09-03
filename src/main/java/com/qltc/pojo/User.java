package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;


@Entity
@Data
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    private String name;
    
    private String email;
    
    private String phone;
    
    @JsonIgnore
    @Basic(optional = false)
    private String password;
    
    @Column(nullable = true)
    private String identityNumber;
    
    @Column(nullable = true)
    private String address;
    
    private String avatar;
    
    @Basic(optional = false)
    private Boolean isActive = true;
    
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();
    
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private Employee employee;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Order> orders;
    
    @JsonIgnore
    @OneToMany(mappedBy = "staff")
    private Set<Order> ordersByStaff;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Wedding> weddings;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Wedding> weddingContracts;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
    private Set<CustomerFeedback> feedbacks;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<CustomerFeedback> repliedFeedbacks;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Set<UserPermission> userHasPermissions;
   
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private Set<UserInGroup> userInGroups;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Hall> hallsHaveModified;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<WeddingService> weddingServicesHaveModified;
}
