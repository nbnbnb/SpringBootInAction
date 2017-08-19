package me.zhangjin.springboot.demo.basic.web;

import me.zhangjin.springboot.demo.basic.dao.PersonRepository;
import me.zhangjin.springboot.demo.basic.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DataController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping("/save")
    public Person save(String name, String address, Integer age) {
        return personRepository.save(new Person(null, name, age, address));
    }

    @RequestMapping("/findByAddress")
    public List<Person> findByAddress(String address) {
        return personRepository.findByAddress(address);
    }

    @RequestMapping("/findByNameAndAddress")
    public List<Person> findByNameAndAddress(String name, String address) {
        return personRepository.findByNameAndAddress(name, address);
    }

    @RequestMapping("/withNameAndAddressQuery")
    public List<Person> withNameAndAddressQuery(String name, String address) {
        return personRepository.withNameAndAddressQuery(name, address);
    }

    @RequestMapping("/withNamedAndAddressNamedQuery")
    public List<Person> withNamedAndAddressNamedQuery(String name, String address) {
        return personRepository.withNameAndAddressNamedQuery(name, address);
    }

    @RequestMapping("/page")
    public Page<Person> page() {
        return personRepository.findAll(new PageRequest(1, 2));
    }

    @RequestMapping("/findAll")
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @RequestMapping("/findById")
    public Person findById(Long id) {
        return personRepository.findOne(id);
    }
}
