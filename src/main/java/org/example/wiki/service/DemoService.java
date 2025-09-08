package org.example.wiki.service;

import org.example.wiki.domain.Demo;
import org.example.wiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//使用@Service 注解  将这个Service交给Spring来管理
@Service
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list(){
        return demoMapper.selectByExample(null);
    }
}
