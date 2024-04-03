package com.integration.scaffold.taskexecutionandscheduling;

import com.integration.scaffold.relationaldataaccess.mysql.mapper.AddressBookRepository;
import com.integration.scaffold.relationaldataaccess.mysql.service.AddressBookService;
import com.integration.scaffold.relationaldataaccess.mysql.service.impl.AddressBookServiceImpl;
import org.awaitility.Durations;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class ScheduledTasksTest {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasksTest.class);
    @SpyBean //@Spy、@SpyBean: 类似@Mock、@MockBean, 不过是执行的时候能够执行真实的函数。
    ScheduledTasks tasks;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    public void reportCurrentTime() {
        // atMost 在抛出超时异常之前，最多等待超时。
        // 开始构建一个await语句。返回:条件工厂

        await().atMost(Durations.TEN_SECONDS).untilAsserted(() -> {
            verify(tasks, atLeast(1)).reportCurrentTime();
        });
    }


    private Callable<Boolean> syncUserIsAdded() throws ExecutionException, InterruptedException {
        tasks.syncInsertDatas();
        log.info("list的实际长度为--------------------------------------" + addressBookRepository.findAll().size());
        Integer total = AddressBookServiceImpl.TOTAL_INSERT_DATA;
        int expectedlength = total += 2;

        return () -> addressBookRepository.findAll().size() == expectedlength; // The condition that must be fulfilled
    }

    @Test
    public void testSyncInsertDatas() throws ExecutionException, InterruptedException {

        await().atMost(Duration.of(30, SECONDS)).until(syncUserIsAdded());

    }

    private Callable<Boolean> asyncUserIsAdded() throws ExecutionException, InterruptedException {

        tasks.asyncInsertDatas();
        Thread.sleep(20000);
        return () -> true;
    }

    @Test
    public void testAsyncInsertDatas() throws ExecutionException, InterruptedException {
        await().atMost(Duration.of(30, SECONDS)).until(asyncUserIsAdded());

        int size = addressBookRepository.findAll().size();
        log.info("size:" + size);
        Integer total = AddressBookServiceImpl.TOTAL_INSERT_DATA;
        int expectedlength = total * 2 + 2;
        log.info("expectedlength:" + expectedlength);
        Assert.isTrue(size == expectedlength, "插入数据错误");

    }


}
