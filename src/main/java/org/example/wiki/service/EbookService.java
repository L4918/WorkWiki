package org.example.wiki.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.example.wiki.domain.Ebook;
import org.example.wiki.domain.EbookExample;
import org.example.wiki.mapper.EbookMapper;
import org.example.wiki.req.EbookReq;
import org.example.wiki.resp.EbookResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//使用@Service 注解  将这个Service交给Spring来管理
@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq req){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> respList = new ArrayList<>();
        for (Ebook ebook : ebookList) {
            EbookResp ebookResp = new EbookResp();
            BeanUtils.copyProperties(ebook,ebookResp);
            respList.add(ebookResp);
        }

        return respList;
    }
}
