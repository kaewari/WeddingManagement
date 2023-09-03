package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "order_halls_details")
public class OrderDetailsHall implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    private double price = 0;
    
    @Basic(optional = false)
    private float discount = 0;
    
    @ManyToOne
    @JoinColumn(name = "hallPriceId")
    private HallPrice hallPrice;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof OrderDetailsHall)) { return false;}
        return id != null && id.equals(((OrderDetailsHall) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
