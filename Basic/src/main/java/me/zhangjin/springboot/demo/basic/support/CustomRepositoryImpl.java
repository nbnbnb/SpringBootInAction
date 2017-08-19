package me.zhangjin.springboot.demo.basic.support;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

import static me.zhangjin.springboot.demo.basic.spec.CustomerSpecs.byAuto;

/**
 * 此类继承 JpaRepository 的实现类 SimpleJpaRepository
 * 让我们可以使用 SimpleJpaRepository 的方法
 * 此类当然还要实现我们自定义的接口 CustomRepository
 *
 * @param <T>
 * @param <ID>
 */
public class CustomRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements CustomRepository<T, ID> {

    private final EntityManager entityManager;

    public CustomRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Page<T> findByAuto(T example, Pageable pageable) {
        return findAll(byAuto(entityManager, example), pageable);
    }
}
