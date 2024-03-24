package com.integration.scaffold.relationaldataaccess.mysql.service;

import com.integration.scaffold.relationaldataaccess.mysql.dto.UserAddressBookDto;
import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    AddressBook save(AddressBook addressBook);

    AddressBook getById(Long id);

    List<AddressBook> listByUserId(Long userId);

    void deleteById(Long id);

    AddressBook update(AddressBook addressBook);

    UserAddressBookDto getAllUserInfo(Long id);


    List<AddressBook> getUserInfoByPage(Long userId, Long pageNum, Long pageSize);

    Long countUserInfo(Long userId);
}
