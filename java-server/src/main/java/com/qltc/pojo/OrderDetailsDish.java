package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.qltc.json.JsonMarkup;
import com.qltc.json.deserializer.OrderDetailsDishDeserializer;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "order_dish_details")
@JsonDeserialize(using = OrderDetailsDishDeserializer.class)
public class OrderDetailsDish implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonView(JsonMarkup.Identity.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private Integer quantity = 1;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private double price;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Basic(optional = false)
    private float discount = 0;
    
    @JsonView(JsonMarkup.CoreData.class)
    @Column(nullable = true)
    private String note;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate = new Timestamp(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "dishId")
    private Dish dish;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetailsDish)) {
            return false;
        }
        return id != null && id.equals(((OrderDetailsDish) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
