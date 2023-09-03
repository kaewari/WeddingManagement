package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Data;


@Entity
@Data
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Basic(optional = false)
    private double total;
    
    @Basic(optional = false)
    private double discount = 0;
   
    @Column(nullable = true)
    private String receiptNo;
    
    @Column(nullable = true)
    private String padVia;
    
    @Column(nullable = true)
    private String note;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();
    
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "customerId")
    private User customer;
    
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeId")
    private User staff;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true)
    private Set<OrderDetailsDish> orderDetailsDishes = new HashSet<>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true)
    private Set<OrderDetailsHall> orderDetailsHalls = new HashSet<>();
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", orphanRemoval = true)
    private Set<OrderDetailsService> orderDetailsServices = new HashSet<>();
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Order)) return false;
        return obj != null && this.equals(((Order) obj).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
