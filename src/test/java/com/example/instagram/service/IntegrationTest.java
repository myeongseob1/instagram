package com.example.instagram.service;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//Test 환경에서 Transactional 은 commit이 되지 않게 해줌
@Transactional
@RunWith(SpringRunner.class)
@Ignore
public class IntegrationTest {

}
