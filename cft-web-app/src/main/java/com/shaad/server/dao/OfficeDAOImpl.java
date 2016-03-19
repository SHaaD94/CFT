package com.shaad.server.dao;


import com.shaad.server.entities.Office;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class OfficeDAOImpl implements OfficeDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Office getByOffice_ID(int Office_ID) {
        return (Office) sessionFactory.getCurrentSession().get(Office.class, Office_ID);
    }

    @Override
    public List<Office> getAllOffice() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Office.class);
        return criteria.list();
    }

    @Override
    public int save(Office Office) {
        return (Integer) sessionFactory.getCurrentSession().save(Office);
    }

    @Override
    public void update(Office Office) {
        sessionFactory.getCurrentSession().merge(Office);
    }

    @Override
    public void view(Office Office) {
        sessionFactory.getCurrentSession().merge(Office);
    }

    @Override
    public void delete(int Office_ID) {
        Office s = getByOffice_ID(Office_ID);
        s.getBank().getOfficeList().remove(s);
        sessionFactory.getCurrentSession().delete("Office", s);
    }

}
