package cn.element.controller;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import cn.element.pojo.Attribute;
import cn.element.pojo.Category;
import cn.element.service.AttributeService;
import cn.element.service.CategoryService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('category:select')")
    @GetMapping
    public ResultInfo getCategoryList(@RequestParam(value = "type",defaultValue = "3") Integer type,
                                      @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize) {

        Page<Category> page = categoryService.getCategoryList(pageNum,pageSize);

        if (!CollectionUtils.isEmpty(page.getRecords())) {
            return ResultInfo.ok(Constant.SELECT_SUCCESS, page);
        }

        return ResultInfo.notFound(Constant.SELECT_FAILED);
    }

    /**
     * 获取数据库中完整的树形结构分类实体列表
     */
    @PreAuthorize("hasAuthority('category:select')")
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
    @PreAuthorize("hasAuthority('category:select')")
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
    @PreAuthorize("hasAuthority('category:insert')")
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
    @PreAuthorize("hasAuthority('category:update')")
    @PutMapping
    public ResultInfo editCategory(Category category) {
        boolean b = categoryService.editCategory(category);

        if (b) {
            return ResultInfo.ok(Constant.UPDATE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    /**
     * 删除分类
     * 就是把isShow字段设为0即可,逻辑删除
     */
    @PreAuthorize("hasAuthority('category:delete')")
    @DeleteMapping("/{catId}")
    public ResultInfo deleteCategory(@PathVariable("catId") Integer catId) {
        boolean b = categoryService.deleteCategoryCascaded(catId);

        if (b) {
            return ResultInfo.ok(Constant.DELETE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    @PreAuthorize("hasAuthority('category:select')")
    @GetMapping("/{catId}/attributes")
    public ResultInfo getAttributesByCatId(@PathVariable("catId") Integer catId,
                                           @RequestParam("sel") String sel) {
        List<Attribute> list = attributeService.getAttributesByCatId(catId, sel);

        if (!CollectionUtils.isEmpty(list)) {
            return ResultInfo.ok(Constant.SELECT_SUCCESS,list);
        }

        return ResultInfo.notFound(Constant.SELECT_FAILED);
    }

    @PreAuthorize("hasAuthority('category:insert')")
    @PostMapping("/{catId}/attributes")
    public ResultInfo addOneAttribute(Attribute attribute) {
        boolean b = attributeService.insertOneAttribute(attribute);

        if (b) {
            return ResultInfo.created(Constant.CREATED_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    @PreAuthorize("hasAuthority('category:select')")
    @GetMapping("/{catId}/attributes/{attrId}")
    public ResultInfo getCategoryAttrById(@PathVariable("catId") Integer catId,
                                          @PathVariable("attrId") Integer attrId,
                                          @RequestParam("attrSel") String attrSel) {
        Attribute attribute = attributeService.getAttributeById(catId, attrId, attrSel);

        if (attribute != null) {
            return ResultInfo.ok(Constant.SELECT_SUCCESS,attribute);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    /**
     * 修改参数名称
     * @param attrId        参数id
     * @param attrName      名称
     */
    @PreAuthorize("hasAuthority('category:update')")
    @PutMapping("/attributes/{attrId}")
    public ResultInfo editAttributeById(@PathVariable("attrId") Integer attrId,
                                        @RequestParam("attrName") String attrName) {
        boolean b = attributeService.editAttributeById(attrId, attrName);

        if (b) {
            return ResultInfo.ok(Constant.UPDATE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    /**
     * 编辑属性表
     * @param attrId    参数id
     * @param attrVal   可选参数
     */
    @PreAuthorize("hasAuthority('category:update')")
    @PostMapping("/attributes/{attrId}")
    public ResultInfo editAttrValTagById(@PathVariable("attrId") Integer attrId,
                                         @RequestParam("attrVal") String attrVal) {
        boolean b = attributeService.updateAttributeByAttrVal(attrId, attrVal);

        if (b) {
            return ResultInfo.ok(Constant.UPDATE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

}
