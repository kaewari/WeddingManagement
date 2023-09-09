package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
@JsonIgnoreProperties({"orderDetailsDishesSet", "orderDetailsHallsSet", "orderDetailsServicesSet"})
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    private double total;

    @Basic(optional = false)
    private double discount;

    @Column(nullable = true, unique = true)
    private String receiptNo;

    @Column(nullable = true)
    private String paidVia;

    private String note = null;

    @Temporal(TemporalType.TIMESTAMP)
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate = new Timestamp(System.currentTimeMillis());

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId")
    private User customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employeeId")
    private User employee;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetailsDish> orderDetailsDishesSet;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetailsHall> orderDetailsHallsSet;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetailsService> orderDetailsServicesSet;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Order)) {
            return false;
        }
        return obj != null && this.equals(((Order) obj).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
