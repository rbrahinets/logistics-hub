package com.translogistics.hub.logistics_hub.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AbstractHibernateRepositoryTest {

    @Autowired
    private TestEntityRepository repository;

    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    void setUp() {
        repository.findAll().forEach(repository::delete);
        flushAndClear();
    }

    @Test
    void shouldSaveEntity() {
        var entity = new TestEntity("test");
        repository.save(entity);
        flushAndClear();

        assertThat(entity.getId()).isNotNull();
    }

    @Test
    void shouldFindById() {
        var entity = new TestEntity("test");
        repository.save(entity);
        flushAndClear();

        var found = repository.findById(entity.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("test");
    }

    @Test
    void shouldReturnEmptyOptional_whenNotFound() {
        var found = repository.findById(999L);

        assertThat(found).isEmpty();
    }

    @Test
    void shouldFindAll() {
        repository.save(new TestEntity("first"));
        repository.save(new TestEntity("second"));
        flushAndClear();

        var all = repository.findAll();

        assertThat(all).hasSize(2);
    }

    @Test
    void shouldUpdateEntity() {
        var entity = new TestEntity("original");
        repository.save(entity);
        flushAndClear();

        entity.setName("updated");
        repository.update(entity);
        flushAndClear();

        var found = repository.findById(entity.getId());
        assertThat(found.get().getName()).isEqualTo("updated");
    }

    @Test
    void shouldDeleteEntity() {
        var entity = new TestEntity("toDelete");
        repository.save(entity);
        flushAndClear();

        repository.delete(entity);
        flushAndClear();

        var found = repository.findById(entity.getId());
        assertThat(found).isEmpty();
    }

    @Test
    void shouldCountEntities() {
        repository.save(new TestEntity("one"));
        repository.save(new TestEntity("two"));
        repository.save(new TestEntity("three"));
        flushAndClear();

        var count = repository.count();

        assertThat(count).isEqualTo(3);
    }

    @Test
    void shouldReturnZeroCount_whenEmpty() {
        var count = repository.count();

        assertThat(count).isZero();
    }

    private void flushAndClear() {
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
}
