package cn.element.controller;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import cn.element.pojo.Goods;
import cn.element.service.GoodsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PreAuthorize("hasAuthority('goods:select')")
    @GetMapping
    public ResultInfo getGoodsList(@RequestParam(value = "query",defaultValue = "") String keyword,
                                   @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        Page<Goods> page = goodsService.getGoodsList(pageNum, pageSize, keyword);

        if(!CollectionUtils.isEmpty(page.getRecords())){
            return ResultInfo.ok(Constant.SELECT_SUCCESS,page);
        }

        return ResultInfo.notFound(Constant.SELECT_FAILED);
    }

    @PreAuthorize("hasAuthority('goods:delete')")
    @DeleteMapping("{goodsId}")
    public ResultInfo deleteGoodsById(@PathVariable("goodsId") Integer goodsId){

        boolean b = goodsService.deleteGoodsById(goodsId);

        if(b){
            return ResultInfo.ok(Constant.DELETE_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }

    /**
     * 添加商品的数据接口
     * @param goods     使用Goods类对象接收前端传入的json字符串
     *                  所以必须要使用@RequestBody注解
     *                  数据格式 Content-Type = application/json
     *                  和通常的 Content-Type = application/x-www-form-urlencoded数据格式不一样
     *                  不使用@RequestBody的话会报错
     */
    @PreAuthorize("hasAuthority('goods:insert')")
    @PutMapping
    public ResultInfo addGoods(@RequestBody Goods goods){

        /*
         * {
         * "goodsName":"asdasd",
         * "goodsPrice":0,
         * "goodsWeight":0,
         * "goodsNumber":0,
         * "goodsCat":[1,3,6],
         * "pics":[],
         * "goodsIntroduce":"<p>asdasd</p>",
         * "attrs":[
         * {"attrId":3077,"attrVal":"49吋4K超薄曲面 人工智能,55吋4K观影曲面 30核HDR,55吋4K超薄曲面 人工智能,65吋4K超薄曲面 人工智能"},
         * {"attrId":3805,"attrVal":"红色 绿色 蓝色 白色"},
         * {"attrId":3068,"attrVal":"TCL电视 55A950C"},
         * {"attrId":3069,"attrVal":"是"},
         * {"attrId":3070,"attrVal":"智能电视"},
         * {"attrId":3071,"attrVal":"支持"},
         * {"attrId":3072,"attrVal":"120瓦特"},
         * {"attrId":3073,"attrVal":"有"},
         * {"attrId":3074,"attrVal":"2个"},
         * {"attrId":3075,"attrVal":"支持"},
         * {"attrId":3076,"attrVal":"支持"}]
         * }
         */
        boolean b = goodsService.addGoods(goods);

        if(b){
            return ResultInfo.created(Constant.INSERT_SUCCESS);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }
}
