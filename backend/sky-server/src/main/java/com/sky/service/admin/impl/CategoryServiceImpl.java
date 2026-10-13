package com.sky.service.admin.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.admin.CategoryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.admin.CategoryService;
import com.sky.vo.admin.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;



    /**
     * 分页查询
     */
    @Override
    public Page<CategoryVO> selectPage(
            String categoryName,
            Integer categoryType,
            Integer currentPage,
            Integer pageSize
    ){

        Page<Category> page =
                new Page<>(currentPage,pageSize);


        LambdaQueryWrapper<Category> wrapper =
                new LambdaQueryWrapper<>();


        wrapper.orderByAsc(Category::getId);



        if(categoryName != null
                && !categoryName.trim().isEmpty()){

            wrapper.like(
                    Category::getCategoryName,
                    categoryName
            );

        }



        if(categoryType != null){

            wrapper.eq(
                    Category::getCategoryType,
                    categoryType
            );

        }



        Page<Category> result =
                categoryMapper.selectPage(page,wrapper);



        Page<CategoryVO> voPage =
                new Page<>();

        voPage.setCurrent(result.getCurrent());
        voPage.setSize(result.getSize());
        voPage.setTotal(result.getTotal());


        List<CategoryVO> list =
                result.getRecords()
                        .stream()
                        .map(category -> {

                            CategoryVO vo =
                                    new CategoryVO();

                            BeanUtils.copyProperties(
                                    category,
                                    vo
                            );

                            return vo;

                        })
                        .collect(Collectors.toList());


        voPage.setRecords(list);


        return voPage;

    }





    /**
     * 添加分类
     */
    @Override
    public void categoryAdd(CategoryDTO categoryDTO){


        Category category =
                new Category();


        BeanUtils.copyProperties(
                categoryDTO,
                category
        );


        categoryMapper.insert(category);

    }





    /**
     * 修改分类
     */
    @Override
    public void categoryUpdate(CategoryDTO categoryDTO){


        Category category =
                new Category();


        BeanUtils.copyProperties(
                categoryDTO,
                category
        );


        categoryMapper.updateById(category);

    }





    /**
     * 删除分类
     */
    @Override
    public void categoryDelete(Integer id){

        categoryMapper.deleteById(id);

    }





    /**
     * 查询全部分类
     */
    @Override
    public List<CategoryVO> getCategoryList(){


        LambdaQueryWrapper<Category> wrapper =
                new LambdaQueryWrapper<>();

        wrapper.orderByAsc(Category::getId);



        List<Category> categories =
                categoryMapper.selectList(wrapper);



        return categories.stream()
                .map(category -> {

                    CategoryVO vo =
                            new CategoryVO();

                    BeanUtils.copyProperties(
                            category,
                            vo
                    );

                    return vo;

                })
                .collect(Collectors.toList());

    }

}