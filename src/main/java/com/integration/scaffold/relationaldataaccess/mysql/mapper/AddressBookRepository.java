package com.integration.scaffold.relationaldataaccess.mysql.mapper;

import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;


public interface AddressBookRepository extends JpaRepositoryImplementation<AddressBook, Long> {
    // todo 本来继承的是extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> 正好JpaRepositoryImplementation和她层次一样，遂继承JpaRepositoryImplementation

    @Query(value = "SELECT DISTINCT(consignee) from address_book", nativeQuery = true)
    List<String> getAllConsignee();
}
