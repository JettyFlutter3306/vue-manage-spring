package cn.element.manage.service;

import cn.element.manage.pojo.common.GoodsPicture;
import cn.element.manage.mapper.GoodsAttrMapper;
import cn.element.manage.mapper.GoodsMapper;
import cn.element.manage.mapper.GoodsPicsMapper;
import cn.element.manage.pojo.common.Goods;
import cn.element.manage.pojo.common.GoodsAttribute;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsAttrMapper goodsAttrMapper;

    @Autowired
    private GoodsPicsMapper goodsPicsMapper;

    /**
     * 分页查询商品列表
     * @param pageNum           当前页码
     * @param pageSize          每页大小
     * @param keyword           关键字
     * @return                  分页对象
     */
    public Page<Goods> getGoodsList(Integer pageNum, Integer pageSize, String keyword){

        Page<Goods> page = new Page<>(pageNum,pageSize);

        goodsMapper.selectPage(page,new QueryWrapper<Goods>().like("goods_name",keyword));

        return page;
    }

    /**
     * 根据id删除商品对象
     * @param goodsId       商品的id
     */
    public boolean deleteGoodsById(Integer goodsId){

        Goods goods = new Goods();
        goods.setGoodsId(goodsId);

        try {
            goodsMapper.deleteById(goodsId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 添加商品
     */
    @Transactional
    public boolean addGoods(Goods goods){

        Integer id = goodsMapper.selectAutoIncrement();//查询商品表的自增长的id,方便下面注入对象

        List<Integer> goodsCatList = goods.getGoodsCat();//获取三级分类的id集合
        goods.setCatId(goodsCatList.get(2));
        goods.setCatOneId(goodsCatList.get(0));
        goods.setCatTwoId(goodsCatList.get(1));
        goods.setCatThreeId(goodsCatList.get(2));

        List<Map<String, String>> attrsList = goods.getAttrs();//获取attrId和attrVal的关系映射的集合
        List<GoodsAttribute> gaList = new ArrayList<>();//创建一个装GoodsAttribute的集合

        for (Map<String, String> map : attrsList) {//遍历List<Map<String,String>>
            GoodsAttribute  ga = new GoodsAttribute();
            ga.setGoodsId(id);

            for (Map.Entry<String, String> entry : map.entrySet()) {
                switch (entry.getKey()){
                    case "attrId": ga.setAttrId(Integer.valueOf(entry.getValue()));break;
                    case "attrVal": ga.setAttrValue(entry.getValue());break;
                }
            }

            gaList.add(ga);
        }

        List<String> pics = goods.getPics();
        List<GoodsPicture> gpList = new ArrayList<>();

        for (String pic : pics) {//遍历商品图片列表
            GoodsPicture gp = new GoodsPicture();
            gp.setGoodsId(id);
            gp.setPicsBig(pic);

            gpList.add(gp);
        }

        try {
            goodsMapper.insert(goods);//添加到商品表
            goodsAttrMapper.insertBatch(gaList);//添加到商品参数关联表
            goodsPicsMapper.insertBatch(gpList);//添加到商品图片关联表

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
