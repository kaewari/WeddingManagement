/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.repository.impl;

import com.qltc.pojo.Dish;
import com.qltc.pojo.Hall;
import com.qltc.pojo.HallPrice;
import com.qltc.pojo.OrderDetailsDish;
import com.qltc.pojo.OrderDetailsHall;
import com.qltc.pojo.OrderDetailsService;
import com.qltc.pojo.WeddingService;
import com.qltc.pojo.WeddingServicePrice;
import com.qltc.repository.RevenueRepository;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sonho
 */
@Repository
@Transactional
public class RevenueRepositoryImpl implements RevenueRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Object[]> getTopBestSellerDishes(int limit) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rOdd = q.from(OrderDetailsDish.class);
        Root rD = q.from(Dish.class);

        q.multiselect(rD.get("id"), rD.get("name"), b.sum(rOdd.get("quantity")));

        q.where(b.equal(rOdd.get("dish"), rD.get("id")));
        q.groupBy(rD.get("id"), rD.get("name"));
        q.orderBy(b.desc(b.sum(rOdd.get("quantity"))));

        Query query = session.createQuery(q);
        return query.setMaxResults(limit).getResultList();
    }

    @Override
    public List<Object[]> getTopBestSellerServices(int limit) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rOds = q.from(OrderDetailsService.class);
        Root rWsp = q.from(WeddingServicePrice.class);
        Root rWs = q.from(WeddingService.class);
        q.multiselect(rWsp.get("id"), rWs.get("name"), b.sum(rOds.get("quantity")));

        q.where(b.equal(rOds.get("servicePrice"), rWsp.get("id")));
        q.where(b.equal(rWsp.get("service"), rWs.get("id")));
        q.groupBy(rWsp.get("id"), rWs.get("name"));
        q.orderBy(b.desc(b.sum(rOds.get("quantity"))));

        Query query = session.createQuery(q);
        return query.setMaxResults(limit).getResultList();
    }

    @Override
    public List<Object[]> getStatsRevenue(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        String queryString
                = "select o.id as 'id', sum(odd.quantity*odd.price) + sum(ohd.price) + sum(osd.quantity*osd.price) as 'total'\n"
                + "from orders o, orders_dishes_details odd, orders_halls_details ohd, orders_services_details osd\n"
                + "where o.id = odd.orderId and o.id = ohd.orderId and o.id=osd.orderId and "
                + "((o.createdDate >= ?1 and o.createdDate <= ?2) or "
                + "quarter(o.createdDate) = quarter(?3))\n"
                + "group by o.id\n"
                + "order by 2 desc";
        String fromDateTime = null;
        String toDateTime = null;
        String quarter = null;
        Date currentDate = new Date();
        if (params != null) {
            try {
                fromDateTime = params.get("fromDateTime");
            } catch (Exception e) {
            }
            try {
                toDateTime = params.get("toDateTime");
            } catch (Exception e) {
            }
            try {
                quarter = params.get("quarter");
            } catch (Exception e) {
            }
        }

        Query query = session.createNativeQuery(queryString);
        if (fromDateTime == null) {
            query.setParameter(1, currentDate);
        } else {
            query.setParameter(1, fromDateTime);
        }
        if (toDateTime == null) {
            query.setParameter(2, currentDate);
        } else {
            query.setParameter(2, toDateTime);
        }
        query.setParameter(3, quarter);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getTopBestSellerHalls(int limit) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rOdh = q.from(OrderDetailsHall.class);
        Root rHp = q.from(HallPrice.class);
        Root rH = q.from(Hall.class);
        q.multiselect(rHp.get("id"), rH.get("name"), b.sum(rOdh.get("hallPrice")));

        q.where(b.equal(rOdh.get("hallPrice"), rHp.get("id")));
        q.where(b.equal(rHp.get("hall"), rH.get("id")));
        q.groupBy(rHp.get("id"), rH.get("name"));
        q.orderBy(b.desc(b.sum(rOdh.get("hallPrice"))));

        Query query = session.createQuery(q);
        return query.setMaxResults(limit).getResultList();
    }

}
