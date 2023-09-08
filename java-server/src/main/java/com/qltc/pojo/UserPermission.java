package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.qltc.json.JsonMarkup;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "user_permission")
public class UserPermission implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonView(JsonMarkup.Identity.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private Boolean allows = false;
    
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "permissionId", nullable = false)
    private Permission permission;
    
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof UserPermission)) { return false;}
        return id != null && id.equals(((UserPermission) o).getId());
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
