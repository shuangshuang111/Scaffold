package com.integration.scaffold.relationaldataaccess.mysql.mapper;

import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AddressBookRepository extends JpaRepository<AddressBook, Long>, JpaSpecificationExecutor<AddressBook> {

    @Query(value = "SELECT DISTINCT(consignee) from address_book", nativeQuery = true)
    List<String> getAllConsignee();
}
