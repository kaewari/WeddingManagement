/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sonho
 */
@Getter
@Setter
@Entity
@Table(name = "comment")
public class CustomerFeedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    private String feedBackType;
    @Basic(optional = false)
    private String comment;
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    private String reply;
    @JoinColumn(name = "customerId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private User customerId;
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private User userId;

}
