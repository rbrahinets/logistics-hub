package com.translogistics.hub.logistics_hub.repository;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TestEntityRepository extends AbstractHibernateRepository<TestEntity, Long> {

    public TestEntityRepository(SessionFactory sessionFactory) {
        super(TestEntity.class, sessionFactory);
    }
}
