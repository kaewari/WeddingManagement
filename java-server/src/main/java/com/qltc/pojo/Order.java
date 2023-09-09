package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    @Basic(optional = false)
    private double total;

    @Basic(optional = false)
    private double discount = 0;

    @Column(nullable = true)
    private String receiptNo;

    @Column(nullable = true)
    private String paidVia;
    @Column(nullable = true)
    private String note;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customerId", nullable = true)
    private User customer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employeeId")
    private User employee;

    @JsonIgnore
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Wedding wedding;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetailsDish> orderDetailsDishes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetailsHall> orderDetailsHalls = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetailsService> orderDetailsServices = new HashSet<>();

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

    public void setWedding(Wedding wedding) {
        this.wedding = wedding;
        wedding.setOrder(this);
    }

    public void addOrderDetailsDish(OrderDetailsDish orderDetailsDish) {
        orderDetailsDishes.add(orderDetailsDish);
        orderDetailsDish.setOrder(this);
    }

    public void removeOrderDetailsDish(OrderDetailsDish orderDetailsDish) {
        orderDetailsDishes.remove(orderDetailsDish);
        orderDetailsDish.setOrder(null);
    }

    public void addOrderDetailsService(OrderDetailsService orderDetailsService) {
        orderDetailsServices.add(orderDetailsService);
        orderDetailsService.setOrder(this);
    }

    public void removeOrderDetailsService(OrderDetailsService orderDetailsService) {
        orderDetailsServices.remove(orderDetailsService);
        orderDetailsService.setOrder(null);
    }

    public void addOrderDetailsHall(OrderDetailsHall orderDetailsHall) {
        orderDetailsHalls.add(orderDetailsHall);
        orderDetailsHall.setOrder(this);
    }

    public void removeOrderDetailsHall(OrderDetailsHall orderDetailsHall) {
        orderDetailsHalls.remove(orderDetailsHall);
        orderDetailsHall.setOrder(null);
    }
}
