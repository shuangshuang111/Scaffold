package com.integration.scaffold.relationaldataaccess.mysql.service.impl;

import com.integration.scaffold.relationaldataaccess.mysql.dto.UserAddressBookDto;
import com.integration.scaffold.relationaldataaccess.mysql.entity.AddressBook;
import com.integration.scaffold.relationaldataaccess.mysql.entity.User;
import com.integration.scaffold.relationaldataaccess.mysql.mapper.AddressBookRepository;
import com.integration.scaffold.relationaldataaccess.mysql.mapper.UserRepository;
import com.integration.scaffold.relationaldataaccess.mysql.service.AddressBookService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    Logger logger = LoggerFactory.getLogger(AddressBookServiceImpl.class);
    // todo CrudRepository的扩展接口是PagingAndSortingRepository，它提供了额外的方法来使用分页和排序的抽象来检索实体。
    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private UserRepository userRepository;

    public static final Integer EACH_INSERT = 100;

    public static final Integer TOTAL_INSERT_DATA = 100000;

    @Override
    public AddressBook save(AddressBook addressBook) {

        if (Optional.ofNullable(addressBook.getId()).isPresent()) {
            addressBook.setUpdateTime(LocalDateTime.now());
            addressBook.setUpdateUser(11111111l);
            return addressBookRepository.save(addressBook);
        }
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
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(userId);
        Example<AddressBook> example = Example.of(addressBook);
        return addressBookRepository.findAll(example);
    }

    @Override
    public void deleteById(Long id) {
        addressBookRepository.deleteById(id);
    }


    @Override
    public UserAddressBookDto getAllUserInfo(Long id) {
        UserAddressBookDto userAddressBookDto = new UserAddressBookDto();
        Optional<User> optionalUser = userRepository.findById(id);
        // Specification实现动态查询，Root即root 获取实体类具体属性, CriteriaBuilder即cb 拼接查询条件的，拼接好查询条件之后，通过 CriteriaQuery即query 实现查询
        if (optionalUser.isPresent()) {
            Specification<AddressBook> queryCondition = new Specification<AddressBook>() {
                @Override
                public Predicate toPredicate(Root<AddressBook> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicateList = new ArrayList<>();
                    if (id != null) {
                        predicateList.add(criteriaBuilder.equal(root.get("userId"), id));
                    }
                    // 这里只是尝试下复杂查询是否真实可用，直接用了数字
                    predicateList.add(criteriaBuilder.like(root.get("consignee"), "%云志%"));
                    predicateList.add(criteriaBuilder.between(root.get("id"), 2, 5));
                    return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
                }
            };
            Sort sort = Sort.by(Sort.Direction.DESC, "updateTime").and(Sort.by(Sort.Direction.ASC, "id"));
            List<AddressBook> addressBookList = addressBookRepository.findAll(queryCondition, sort);

            userAddressBookDto.setAddressBooklist(addressBookList);
            return userAddressBookDto;
        }

        return null;
    }

    @Override
    public Page<AddressBook> getUserInfoByPage(Long userId, int pageNum, int pageSize) {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(userId);
        Example<AddressBook> example = Example.of(addressBook);
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime").and(Sort.by(Sort.Direction.ASC, "id"));
        PageRequest pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<AddressBook> addressBookPage = addressBookRepository.findAll(example, pageable);
//         JpaRepositoryImplementation<T, ID> 继承 JpaSpecificationExecutor<T>
//         JpaSpecificationExecutor<T> 里面有 Page<T> findAll(Specification<T> spec, Pageable pageable);方法 可以写复杂的查询条件
//         JpaRepository<T, ID> extends ListCrudRepository<T, ID>, ListPagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T>
//         QueryByExampleExecutor<T> 里面有<S extends T> Page<S> findAll(Example<S> example, Pageable pageable); 可以写简单点的查询方法
        return addressBookPage;

    }

    @Override
    public List<String> getAllConsignee() {
        return addressBookRepository.getAllConsignee();
    }

    @Override
    public void testSyncInsertDatas() {

        // 插入2万条数据，for循环插入，一次插入1千条
        for (int i = 0; i < TOTAL_INSERT_DATA / EACH_INSERT; i++) {
            List<AddressBook> addressBookList = new ArrayList<>();
            for (int j = 0; j < EACH_INSERT; j++) {

                AddressBook addressBook = new AddressBook();
                addressBook.setUpdateUser(3333333l);
                addressBook.setCreateUser(3333333l);
                addressBook.setCreateTime(LocalDateTime.now());
                addressBook.setUpdateTime(LocalDateTime.now());
                addressBook.setConsignee(new String[]{"云志", "霜霜", "栓栓"}[(int) Math.random() * 3]);
                addressBook.setUserId(2l);
                addressBook.setIsDeleted(1);
                addressBook.setIsDefault(1);
                addressBook.setSex("0");
                addressBook.setPhone("15210675046");
                addressBook.setDetail("test SyncInsert" + LocalDateTime.now());
                addressBookList.add(addressBook);

            }


            List<AddressBook> addressBooks = addressBookRepository.saveAll(addressBookList);
            if (addressBooks.size() == EACH_INSERT) {
                logger.info("第" + i + "个" + EACH_INSERT + "地址插入成功");
            }

        }
        logger.info(TOTAL_INSERT_DATA + "个地址插入成功");

    }

    @Override
    // @Async
    public Integer testAsyncInsertDatas() {
        int actualtotal = 0;

        for (int i = 0; i < TOTAL_INSERT_DATA / EACH_INSERT; i++) {
            List<AddressBook> addressBookList = new ArrayList<>();
            for (int j = 0; j < EACH_INSERT; j++) {

                AddressBook addressBook = new AddressBook();
                addressBook.setUpdateUser(3333333l);
                addressBook.setCreateUser(3333333l);
                addressBook.setCreateTime(LocalDateTime.now());
                addressBook.setUpdateTime(LocalDateTime.now());
                addressBook.setConsignee(new String[]{"云志", "霜霜", "栓栓"}[(int) Math.random() * 3]);
                addressBook.setUserId(2l);
                addressBook.setIsDeleted(1);
                addressBook.setIsDefault(1);
                addressBook.setSex("0");
                addressBook.setPhone("15210675046");
                addressBook.setDetail("test SyncInsert" + LocalDateTime.now());
                addressBookList.add(addressBook);


            }
            actualtotal += EACH_INSERT;

            List<AddressBook> addressBooks = addressBookRepository.saveAll(addressBookList);
            if (addressBooks.size() == EACH_INSERT) {
                logger.info("异步第" + i + "个" + EACH_INSERT + "地址插入成功");
            }

        }
        logger.info(TOTAL_INSERT_DATA + "个地址异步插入成功");
        if (actualtotal != TOTAL_INSERT_DATA) {
            throw new RuntimeException("批量异步插入数据失败");
        }


        return actualtotal;
    }
}
