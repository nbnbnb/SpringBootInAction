package me.zhangjin.springboot.demo.basic.dao;

import me.zhangjin.springboot.demo.basic.domain.Person;
import me.zhangjin.springboot.demo.basic.support.CustomRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Spring Data REST 的默认规则，就是在实体类之后加 s 来形成路径
 * 此处修改为 people
 */
@RepositoryRestResource(path = "people")
public interface PersonRepository extends CustomRepository<Person, Long> {

    List<Person> findByAddress(String address);

    List<Person> findByNameAndAddress(String name, String address);

    @Query("select p from Person p where p.name=:name and p.address=:address")
    List<Person> withNameAndAddressQuery(@Param("name") String name, @Param("address") String address);

    // 这个查询是在 Person 实体上定义的
    List<Person> withNameAndAddressNamedQuery(String name, String address);

    // 只要添加 @RestResource 就会暴露为 REST 资源
    @RestResource(path = "nameStartsWith", rel = "nameStartsWith")
    List<Person> findByNameStartsWith(@Param("name") String name);
}
