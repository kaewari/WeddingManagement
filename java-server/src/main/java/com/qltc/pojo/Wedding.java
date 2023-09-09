package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.qltc.json.JsonMarkup;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "wedding")
public class Wedding implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonView(JsonMarkup.Identity.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @JsonView(JsonMarkup.CoreData.class)
    private double deposit;
    
    @JsonView(JsonMarkup.CoreData.class)
    private double totalLeft;
    
    @JsonView(JsonMarkup.CoreData.class)
    private double discount = 0;
    
    @JsonView(JsonMarkup.CoreData.class)
    private String paidVia;
    
    @JsonView(JsonMarkup.CoreData.class)
    private String receiptNo;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private Boolean isCompleted = false;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic
    private int tableNumber;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic
    private int guestNumber;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic
    private String description;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date celebrityDate;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();
    
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "orderId")
    private Order order;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "createdBy", nullable = true)
    private User user;
    
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "customerId", nullable = false)
    private User customer;
    
    @JsonView(JsonMarkup.FullData.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wedding", orphanRemoval = true)
    private Set<WeddingPicture> pictures;    
    
    @Transient
    public Map getWhatCustomer() {
        if (this.customer == null) return new HashMap<>();
        Map<String, Object> userJson = new HashMap<>();
        userJson.put("id", customer.getId());
        userJson.put("name", customer.getName());
        userJson.put("avatar", customer.getAvatar());
        return userJson;
    }
    
    @Transient
    public Map getWhatUser() {
        if (this.user == null) return new HashMap<>();
        Map<String, Object> userJson = new HashMap<>();
        userJson.put("id", user.getId());
        userJson.put("name", user.getName());
        userJson.put("avatar", user.getAvatar());
        return userJson;
    }
    
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
