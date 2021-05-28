package cn.element.controller;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import cn.element.pojo.Attribute;
import cn.element.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attribute")
public class AttributeController {

    @Autowired
    private AttributeService attributeService;

    /**
     * 根据id删除参数
     * @param attrId    参数的id
     */
    @PreAuthorize("hasAuthority('attribute:delete')")
    @DeleteMapping("/{attrId}")
    public ResultInfo deleteAttributeById(@PathVariable("attrId") Integer attrId){

        boolean b = attributeService.deleteAttributeById(attrId);

        if(b){
            return ResultInfo.ok(Constant.DELETE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    /**
     * 根据参数的id获取参数的可选值
     */
    @PreAuthorize("hasAuthority('attribute:select')")
    @GetMapping("/{attrId}")
    public ResultInfo getAttributeValById(@PathVariable("attrId") Integer attrId){

        Attribute attribute = attributeService.getAttrValById(attrId);

        if(!StringUtils.isEmpty(attribute.getAttrVal())){
            return ResultInfo.notFound(Constant.SELECT_FAILED);
        }

        return ResultInfo.ok(Constant.SELECT_SUCCESS,attribute.getAttrVal());
    }

}
