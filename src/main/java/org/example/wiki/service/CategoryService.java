package org.example.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.wiki.domain.Category;
import org.example.wiki.domain.CategoryExample;
import org.example.wiki.mapper.CategoryMapper;
import org.example.wiki.req.CategoryQueryReq;
import org.example.wiki.resp.CategoryQueryResp;
import org.example.wiki.resp.PageResp;
import org.example.wiki.util.CopyUtil;
import org.example.wiki.util.SnowFlake;
import org.example.wiki.req.CategorySaveReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

//使用@Service 注解  将这个Service交给Spring来管理
@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req){

        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数 : {}", pageInfo.getTotal());
        LOG.info("总页数 : {}", pageInfo.getPages());

//        List<CategoryResp> respList = new ArrayList<>();
//        for (Category category : categoryList) {
//            CategoryResp categoryResp = new CategoryResp();
//            BeanUtils.copyProperties(category,categoryResp);
//            respList.add(categoryResp);
//        }
//
//        return respList;

        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /**
     * 保存
     */
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req,Category.class);
        if (ObjectUtils.isEmpty(req.getId())){
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            //更新
            categoryMapper.updateByPrimaryKey(category);
        }

    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
