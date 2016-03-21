package com.shaad.server.dao;


import com.shaad.server.entities.Bank;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//todo: refactor variable names;
@Repository
@Transactional
public class BankDAOImpl implements BankDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Bank getByBank_ID(int Bank_ID) {
        return (Bank) sessionFactory.getCurrentSession().get(Bank.class, Bank_ID);
    }

    /**
     * return first matching element, because it meant to be unique
     *
     * @param name name of bank to search
     * @return Bank Object
     */
    @Override
    public Bank getByBank_Name(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Bank.class);
        List bankList = criteria.add(Restrictions.eq("name", name)).list();
        return (Bank) (bankList.size() == 0 ? null : bankList.get(0));
    }

    @Override
    public List<Bank> getAllBank() {
        return sessionFactory.getCurrentSession().createQuery("from Bank").list();
    }

    @Override
    public List<Bank> getBanksByCity(String city) {
        return sessionFactory.getCurrentSession().createQuery("select distinct b from Bank b join "
                + " fetch b.officeList o where o.city = ?").setString(0, city).list();
    }

    @Override
    public int save(Bank Bank) {
        return (Integer) sessionFactory.getCurrentSession().save(Bank);
    }

    @Override
    public void update(Bank Bank) {
        sessionFactory.getCurrentSession().merge(Bank);
    }

    @Override
    public void view(Bank Bank) {
        sessionFactory.getCurrentSession().merge(Bank);
    }

    @Override
    public void delete(int bankId) {
        Bank s = getByBank_ID(bankId);
        sessionFactory.getCurrentSession().delete(s);
    }

}
