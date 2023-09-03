package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "order_dish_details")
public class OrderDetailsDish implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    private Integer quantity = 1;
    
    @Basic(optional = false)
    private double price;
    
    @Basic(optional = false)
    private float discount = 0;
    
    @Column(nullable = true)
    private String note;
    
    @ManyToOne
    @JoinColumn(name = "dishId")
    private Dish dish;
    
    @JsonIgnore    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof OrderDetailsDish)) { return false;}
        return id != null && id.equals(((OrderDetailsDish) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
