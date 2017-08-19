package me.zhangjin.springboot.demo.basic.support;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 此接口继承了 JpaRepository，让我们具备了 JpaRepository 所提供的方法
 * 继承 JpaSpecificationExecutor，让我们具备使用 Specification 的能力
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface CustomRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    Page<T> findByAuto(T example, Pageable pageable);
}
