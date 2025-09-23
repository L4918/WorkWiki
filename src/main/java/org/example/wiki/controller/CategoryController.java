package org.example.wiki.controller;

import org.example.wiki.req.CategoryQueryReq;
import org.example.wiki.resp.CommonResp;
import org.example.wiki.resp.CategoryQueryResp;
import org.example.wiki.resp.PageResp;
import org.example.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import org.example.wiki.req.CategorySaveReq;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq req){  //这里加上这个注解之后，表示这组参数要开启校验规则。对应的规则就是PageReq里的注解
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }
}
