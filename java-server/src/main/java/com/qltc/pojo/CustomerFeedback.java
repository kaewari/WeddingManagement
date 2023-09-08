package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.qltc.json.deserializer.CustomerFeedbackDeserializer;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "customer_feedbacks") //fails with create new (null id in this pojo)
@JsonDeserialize(using = CustomerFeedbackDeserializer.class)
public class CustomerFeedback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Basic(optional = false)
    private String feedBackType;
    
    @Basic(optional = false)
    private String comment;
    
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Basic
    private String reply;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId")
    private User customer;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "userId", nullable = true)
    private User user;
    
    @Transient
    public Map getWhatCustomer() {
        if (this.customer == null) return new HashMap<>();
        Map<String, Object> userJson = new HashMap<>();
        userJson.put("id", customer.getId());
        userJson.put("name", customer.getName());
        userJson.put("avatar", customer.getAvatar());
        return userJson;
    }
    
    @Transient
    public Map getWhatUser() {
        if (this.user == null) return new HashMap<>();
        Map<String, Object> userJson = new HashMap<>();
        userJson.put("id", user.getId());
        userJson.put("name", user.getName());
        userJson.put("avatar", user.getAvatar());
        return userJson;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CustomerFeedback)) return false;
        return obj != null && this.equals(((CustomerFeedback) obj).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
