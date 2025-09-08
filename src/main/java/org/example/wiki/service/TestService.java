package org.example.wiki.service;

import org.example.wiki.domain.Test;
import org.example.wiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//使用@Service 注解  将这个Service交给Spring来管理
@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list(){
        return testMapper.list();
    }
}
