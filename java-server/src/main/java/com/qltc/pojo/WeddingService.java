package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import com.qltc.json.JsonMarkup;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
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
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "wedding_services")
public class WeddingService implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonView(JsonMarkup.Identity.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private String name;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private Boolean isAvailable = true;
    
    @JsonView(JsonMarkup.FullData.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service", orphanRemoval = true)
    private Set<WeddingServicePrice> prices = new HashSet<>();
    
    @JsonView(JsonMarkup.FullData.class)
    @ManyToOne(fetch = FetchType.LAZY)
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
