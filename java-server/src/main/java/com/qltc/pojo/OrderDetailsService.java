package com.qltc.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.qltc.json.JsonMarkup;
import com.qltc.json.deserializer.OrderDetailsServiceDeserializer;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
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
@Table(name = "order_services_details")
@JsonDeserialize(using = OrderDetailsServiceDeserializer.class)
public class OrderDetailsService implements Serializable {

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
    @Column(nullable = true)
    private String note;
    
    @JsonView({JsonMarkup.FullData.class, JsonMarkup.FetchedData.class})
    @ManyToOne
    @JoinColumn(name = "serviceId")
    private WeddingServicePrice servicePrice;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetailsService)) {
            return false;
        }
        return id != null && id.equals(((OrderDetailsService) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
