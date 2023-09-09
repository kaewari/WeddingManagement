package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"file", "employeeSet", "userInGroupSet"})
public class User implements Serializable {

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
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Basic(optional = false)
    private String password;
    
    @Basic(optional = false)
    private String identityNumber;
    
    @Basic(optional = false)
    private String address;
    
    private String avatar;
    
    private Boolean isActive;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private MultipartFile file;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<Employee> employeeSet;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<UserInGroup> userInGroupSet;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<UserPermission> userPermissions;
    
    public User(int id) {
        this.id = id;
    }
    
    public User() {};
}
