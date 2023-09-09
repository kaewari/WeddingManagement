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
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_in_group")
public class UserInGroup implements Serializable {

    @JsonView(JsonMarkup.Identity.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "groupId")
    private UserGroup userGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInGroup)) {
            return false;
        }
        return id != null && id.equals(((UserInGroup) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
