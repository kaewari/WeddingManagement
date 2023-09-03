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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;


@Entity
@Data
@Table(name = "halls")
public class Hall implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Basic(optional = false)
    private String name;
    
    private String description;
    
    @Column(nullable = true)
    private String tableCount;
    
    @Column(nullable = true)
    private String guestUpTo;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();
    
    @Basic(optional = false)
    private Boolean isActive = true;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hall", orphanRemoval = true)
    private Set<HallPrice> prices = new HashSet<>();
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchId")
    private Branch atBranch;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modifiedBy")
    private User user;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Hall)) { return false;}
        return id != null && id.equals(((Hall) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    public void addHallPrice(HallPrice hallPrice) {
        prices.add(hallPrice);
        hallPrice.setHall(this);
    }
    
    public void removeHallPrice(HallPrice hallPrice) {
        prices.remove(hallPrice);
        hallPrice.setHall(null);
    }
}
