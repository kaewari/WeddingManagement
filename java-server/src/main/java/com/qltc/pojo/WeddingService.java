package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
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
@Table(name = "wedding_services")
public class WeddingService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    private String name;
    
    @Basic(optional = false)
    private Boolean isAvailable = true;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service", orphanRemoval = true)
    private Set<WeddingServicePrice> prices = new HashSet<>();
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifiedBy")
    private User user;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof WeddingService)) { return false;}
        return id != null && id.equals(((WeddingService) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    public void addWeddingServicePrice(WeddingServicePrice weddingServicePrice) {
        prices.add(weddingServicePrice);
        weddingServicePrice.setService(this);
    }
    
    public void removeWeddingServicePrice(WeddingServicePrice weddingServicePrice) {
        prices.remove(weddingServicePrice);
        weddingServicePrice.setService(null);
    }
}
