package cn.apple.controller;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.pojo.Attribute;
import cn.apple.pojo.Category;
import cn.apple.service.AttributeService;
import cn.apple.service.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttributeService attributeService;

    /**
     * 分页查询
     * @param type      待定
     * @param pageNum   当前页码
     * @param pageSize  每页的条数
     */
    @GetMapping
    public ResultInfo getCategoryList(@RequestParam(value = "type",defaultValue = "3") Integer type,
                                      @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){

        Page<Category> page = categoryService.getCategoryList(pageNum,pageSize);

        if(!CollectionUtils.isEmpty(page.getRecords())){
            return ResultInfo.ok(Constant.SELECT_SUCCESS,page);
        }

        return ResultInfo.notFound(Constant.SELECT_FAILED);
    }

    /**
     * 获取数据库中完整的树形结构分类实体列表
     */
    @GetMapping("/categoryList")
    public ResultInfo getCategoryList(@RequestParam(value = "type",required = false) Integer type){

        List<Category> list = categoryService.getCategoryListAsTree(type);

        if(!CollectionUtils.isEmpty(list)){
            return ResultInfo.ok(Constant.SELECT_SUCCESS,list);
        }

        return ResultInfo.notFound(Constant.SELECT_FAILED);
    }

    /**
     * 根据catId查询分类对象
     */
    @GetMapping("/{catId}")
    public ResultInfo selectById(@PathVariable("catId") Integer catId){

        Category category = categoryService.selectById(catId);

        if(category != null){
            return ResultInfo.ok(Constant.SELECT_SUCCESS,category);
        }

        return ResultInfo.notFound(Constant.SELECT_FAILED);
    }

    /**
     * 添加分类对象
     */
    @PostMapping
    public ResultInfo addCategory(Category category){

        boolean b = categoryService.addCategory(category);

        if(b){
            return ResultInfo.created(Constant.CREATED_SUCCESS);
        }

        return ResultInfo.serverError(Constant.CREATED_FAILED);
    }

    /**
     * 修改分类对象
     */
    @PutMapping
    public ResultInfo editCategory(Category category){

        boolean b = categoryService.editCategory(category);

        if(b){
            return ResultInfo.ok(Constant.UPDATE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    /**
     * 删除分类
     * 就是把isShow字段设为0即可,逻辑删除
     */
    @DeleteMapping("/{catId}")
    public ResultInfo deleteCategory(@PathVariable("catId") Integer catId){

        boolean b = categoryService.deleteCategoryCascaded(catId);

        if(b){
            return ResultInfo.ok(Constant.DELETE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    @GetMapping("/{catId}/attributes")
    public ResultInfo getAttributesByCatId(@PathVariable("catId") Integer catId,
                                           @RequestParam("sel") String sel){

        List<Attribute> list = attributeService.getAttributesByCatId(catId, sel);

        if(!CollectionUtils.isEmpty(list)){
            return ResultInfo.ok(Constant.SELECT_SUCCESS,list);
        }

        return ResultInfo.notFound(Constant.SELECT_FAILED);
    }

    @PostMapping("/{catId}/attributes")
    public ResultInfo addOneAttribute(Attribute attribute){

        boolean b = attributeService.insertOneAttribute(attribute);

        if(b){
            return ResultInfo.created(Constant.CREATED_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    @GetMapping("/{catId}/attributes/{attrId}")
    public ResultInfo getCategroyAttrById(@PathVariable("catId") Integer catId,
                                          @PathVariable("attrId") Integer attrId,
                                          @RequestParam("attrSel") String attrSel){

        Attribute attribute = attributeService.getAttributeById(catId, attrId, attrSel);

        if(attribute != null){
            return ResultInfo.ok(Constant.SELECT_SUCCESS,attribute);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    /**
     * 修改参数名称
     * @param attrId        参数id
     * @param attrName      名称
     */
    @PutMapping("/attributes/{attrId}")
    public ResultInfo editAttributeById(@PathVariable("attrId") Integer attrId,
                                        @RequestParam("attrName") String attrName){

        boolean b = attributeService.editAttributeById(attrId, attrName);

        if(b){
            return ResultInfo.ok(Constant.UPDATE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    /**
     * 编辑属性表
     * @param attrId    参数id
     * @param attrVal   可选参数
     */
    @PostMapping("/attributes/{attrId}")
    public ResultInfo editAttrValTagById(@PathVariable("attrId") Integer attrId,
                                         @RequestParam("attrVal") String attrVal){

        boolean b = attributeService.updateAttributeByAttrVal(attrId, attrVal);

        if(b){
            return ResultInfo.ok(Constant.UPDATE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

}
