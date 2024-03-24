package com.integration.scaffold.relationaldataaccess.mysql.mapper;

import com.integration.scaffold.relationaldataaccess.mysql.entity.User;
import org.springframework.data.repository.CrudRepository;



public interface UserRepository extends CrudRepository<User, Long> {

}
