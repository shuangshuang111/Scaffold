package com.integration.scaffold.relationaldataaccess.mysql.mapper;

import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;
import com.integration.scaffold.relationaldataaccess.mysql.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface UserRepository extends PagingAndSortingRepository<User, Long>,CrudRepository<User, Long> {

}
