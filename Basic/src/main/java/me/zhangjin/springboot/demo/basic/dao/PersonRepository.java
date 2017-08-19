package me.zhangjin.springboot.demo.basic.dao;

import me.zhangjin.springboot.demo.basic.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByAddress(String address);

    List<Person> findByNameAndAddress(String name, String address);

    @Query("select p from Person p where p.name=:name and p.address=:address")
    List<Person> withNameAndAddressQuery(@Param("name") String name, @Param("address") String address);

    // 这个查询是在 Person 实体上定义的
    List<Person> withNameAndAddressNamedQuery(String name, String address);

}
