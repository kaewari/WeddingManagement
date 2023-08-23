/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author sonho
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String name;
    @Basic(optional = false)
    private String email;
    @Basic(optional = false)
    private String phone;
    @Basic(optional = false)
    private String password;
    @Basic(optional = false)
    private String identityNumber;
    @Basic(optional = false)
    private String address;
    @Basic(optional = false)
    private String avatar;
    @Basic(optional = false)
    private Boolean isActive;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Transient
    private MultipartFile file;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId")
    private Employee employeeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Set<Wedding> weddingCustomerSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Wedding> weddingUserSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerId")
    private Set<CustomerFeedback> customerFeedBacksSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<CustomerFeedback> userFeedBacksSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<UserPermission> userPermissionSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<UserInGroup> userInGroupSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Hall> hallSet;
}
