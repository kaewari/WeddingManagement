package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.qltc.json.JsonMarkup;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "halls")
public class Hall implements Serializable {

    @JsonView(JsonMarkup.Identity.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private String name;
    
    @JsonView(JsonMarkup.CoreData.class)
    private String description;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Column(nullable = true)
    private String tableCount;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Column(nullable = true)
    private String guestUpTo;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private Boolean isActive = true;
    
    @JsonView(JsonMarkup.FullData.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hall", orphanRemoval = true)
    private Set<HallPrice> prices = new HashSet<>();
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branchId")
    private Branch atBranch;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "modifiedBy")
    private User user;
    
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
