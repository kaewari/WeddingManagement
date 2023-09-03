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
@Table(name = "order_services_details")
public class OrderDetailsService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    private Integer quantity = 1;
    
    @Basic(optional = false)
    private double price;
    
    @Column(nullable = true)
    private String note;
    
    @ManyToOne
    @JoinColumn(name = "servicePriceId")
    private WeddingServicePrice servicePrice;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof OrderDetailsService)) { return false;}
        return id != null && id.equals(((OrderDetailsService) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
