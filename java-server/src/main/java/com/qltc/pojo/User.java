package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@JsonIgnoreProperties({"file", "employeeSet", "userInGroupSet", "employeeOrderSet", "customerOrderSet"})
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
    private String avatar = null;
    private Boolean isActive = false;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate = new Timestamp(System.currentTimeMillis());
    @Transient
    private MultipartFile file;

//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
//    private Set<Employee> employeeSet;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "userId")
    private Employee employee;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<UserInGroup> userInGroupSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staff")
    private Set<Order> employeeOrderSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Order> customerOrderSet;

    public User(int userId) {
        this.id = userId;
    }

    public User() {
    }
}
