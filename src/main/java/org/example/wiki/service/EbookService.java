package org.example.wiki.service;

import org.example.wiki.domain.Ebook;
import org.example.wiki.mapper.EbookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

//使用@Service 注解  将这个Service交给Spring来管理
@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<Ebook> list(){
        return ebookMapper.selectByExample(null);
    }
}
