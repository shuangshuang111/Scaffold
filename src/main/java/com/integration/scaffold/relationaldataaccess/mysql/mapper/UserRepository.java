package com.integration.scaffold.relationaldataaccess.mysql.mapper;

import com.integration.scaffold.relationaldataaccess.mysql.entity.User;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;


public interface UserRepository extends JpaRepositoryImplementation<User, Long> {

}
