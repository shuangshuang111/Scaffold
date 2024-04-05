package com.integration.scaffold.relationaldataaccess.mysql.mapper;

import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AddressBookRepository extends JpaRepository<AddressBook, Long>, JpaSpecificationExecutor<AddressBook> {

    @Query(value = "select distinct a.consignee from AddressBook a ")
    List<String> getAllConsignee();


}
