package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "wedding")
public class Wedding implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @Basic(optional = false)
    private double deposit;
    
    @Basic(optional = false)
    private double totalLeft;
    
    @Basic(optional = false)
    private double discount = 0;
    
    @Basic(optional = false)
    private String paidVia;
    
    private String receiptNo;
    
    @Basic(optional = false)
    private Boolean isCompleted = false;
    
    @Basic(optional = false)
    private int tableNumber;
    
    @Basic(optional = false)
    private int guestNumber;
    
    @Basic(optional = false)
    private String description;
    
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date celebrityDate;
    
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "orderId")
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User user;
    
    @JoinColumn(name = "customerId")
    @ManyToOne
    private User customer;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wedding", orphanRemoval = true)
    private Set<WeddingPicture> pictures;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Wedding)) { return false;}
        return id != null && id.equals(((Wedding) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    public void addWeddingPicture(WeddingPicture weddingPicture) {
        pictures.add(weddingPicture);
        weddingPicture.setWedding(this);
    }
    
    public void removeWeddingPicture(WeddingPicture weddingPicture) {
        pictures.remove(weddingPicture);
        weddingPicture.setWedding(null);
    }
}
