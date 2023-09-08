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
@Table(name = "service_prices")
public class WeddingServicePrice implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonView(JsonMarkup.Identity.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private String period;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private Double price;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private Boolean isAvailable = true;
    
    
    @JsonView(JsonMarkup.FetchedData.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wServiceId")
    private WeddingService service;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "servicePrice", fetch = FetchType.LAZY)
    private Set<OrderDetailsService> orderDetails;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof WeddingServicePrice)) { return false;}
        return id != null && id.equals(((WeddingServicePrice) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
