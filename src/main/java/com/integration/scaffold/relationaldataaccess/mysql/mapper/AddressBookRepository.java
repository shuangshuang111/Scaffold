package com.integration.scaffold.relationaldataaccess.mysql.mapper;

import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AddressBookRepository  extends PagingAndSortingRepository<AddressBook, Long> ,CrudRepository<AddressBook, Long> {
    @Query("SELECT `address_book`.`id` AS `id`, `address_book`.`sex` AS `sex`, `address_book`.`phone` AS `phone`, `address_book`.`label` AS `label`, `address_book`.`user_id` AS `user_id`, `address_book`.`detail` AS `detail`,\n" +
            " `address_book`.`city_code` AS `city_code`, `address_book`.`city_name` AS `city_name`, `address_book`.`is_deleted` AS `is_deleted`, `address_book`.`is_default` AS `is_default`, `address_book`.`consignee` AS `consignee`, \n" +
            " `address_book`.`update_time` AS `update_time`, `address_book`.`create_time` AS `create_time`, `address_book`.`create_user` AS `create_user`, \n" +
            " `address_book`.`update_user` AS `update_user`, `address_book`.`province_name` AS `province_name`, `address_book`.`district_name` AS `district_name`, \n" +
            " `address_book`.`province_code` AS `province_code`, `address_book`.`district_code` AS `district_code` from address_book where user_id = :userId order by update_time desc")
    List<AddressBook> listByUserId(Long userId);



    @Query("select * from address_book where user_id = :userId")
    Page<AddressBook> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
}
