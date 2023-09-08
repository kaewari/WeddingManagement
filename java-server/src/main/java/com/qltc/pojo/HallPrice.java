package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.qltc.json.JsonMarkup;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "hall_price")
public class HallPrice implements Serializable {

    @JsonView(JsonMarkup.Identity.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private String period;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private double price;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private float discount = 0;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hallId")
    private Hall hall;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hallPrice", fetch = FetchType.LAZY)
    private Set<OrderDetailsHall> orderDetails;
    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof HallPrice)) return false;
        return obj != null && this.equals(((HallPrice) obj).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
