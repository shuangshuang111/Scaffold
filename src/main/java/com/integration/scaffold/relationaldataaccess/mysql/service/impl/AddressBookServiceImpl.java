package com.integration.scaffold.relationaldataaccess.mysql.service.impl;

import com.integration.scaffold.relationaldataaccess.mysql.dto.UserAddressBookDto;
import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;
import com.integration.scaffold.relationaldataaccess.mysql.entity.User;
import com.integration.scaffold.relationaldataaccess.mysql.mapper.AddressBookRepository;
import com.integration.scaffold.relationaldataaccess.mysql.mapper.UserRepository;
import com.integration.scaffold.relationaldataaccess.mysql.service.AddressBookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    // todo CrudRepository的扩展接口是PagingAndSortingRepository，它提供了额外的方法来使用分页和排序的抽象来检索实体。
    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private UserRepository userRepository;
    @Override
    public AddressBook save(AddressBook addressBook) {
        addressBook.setCreateTime(LocalDateTime.now());
        addressBook.setUpdateTime(LocalDateTime.now());
        // todo 这里的createUser都写成了1111111这个工号，之后将登录功能集成进来之后，可以获得当前用户，写入当前用户的工号
        addressBook.setCreateUser(1111111l);
        addressBook.setUpdateUser(1111111l);
        return addressBookRepository.save(addressBook);
    }

    @Override
    public AddressBook getById(Long id) {
        return addressBookRepository.findById(id).orElse(null);

      // todo 这里直接返回null是否不好？有没有可能使用Optional进行优化 一定要对返回值为null做一个专业的整改

    }

    @Override
    public List<AddressBook> listByUserId(Long userId) {
        return addressBookRepository.listByUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        addressBookRepository.deleteById(id);
    }

    @Override
    public AddressBook update(AddressBook addressBook) {
        return addressBookRepository.save(addressBook);
    }

    @Override
    public UserAddressBookDto getAllUserInfo(Long id) {
        UserAddressBookDto userAddressBookDto=new UserAddressBookDto();
        Optional<User> optionalUser=userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user=optionalUser.get();
            BeanUtils.copyProperties(user,userAddressBookDto);
            List<AddressBook> addressBookList=addressBookRepository.listByUserId(id);
            userAddressBookDto.setAddressBooklist(addressBookList);
            return userAddressBookDto;
        }

        return null;
    }

    @Override
    public List<AddressBook> getUserInfoByPage(Long userId, Long pageNum, Long pageSize) {
        List<AddressBook> addressBookList=addressBookRepository.getUserInfoByPage(userId,pageNum,pageSize);
        if(addressBookList.size()>0){
            return addressBookList;
        }
        return new ArrayList<AddressBook>();
    }

    @Override
    public Long countUserInfo(Long userId) {
        return addressBookRepository.countUserInfo(userId);
    }
}
