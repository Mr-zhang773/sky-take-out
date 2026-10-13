package com.sky.service.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.dto.admin.CategoryDTO;
import com.sky.vo.admin.CategoryVO;

import java.util.List;


public interface CategoryService {


     /**
      * 分页查询分类
      */
     Page<CategoryVO> selectPage(
             String categoryName,
             Integer categoryType,
             Integer currentPage,
             Integer pageSize
     );



     /**
      * 添加分类
      */
     void categoryAdd(CategoryDTO categoryDTO);



     /**
      * 修改分类
      */
     void categoryUpdate(CategoryDTO categoryDTO);



     /**
      * 删除分类
      */
     void categoryDelete(Integer id);



     /**
      * 查询全部分类
      */
     List<CategoryVO> getCategoryList();


}