package cn.element.service;

import cn.element.mapper.AttributeMapper;
import cn.element.pojo.Attribute;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttributeService {

    @Autowired
    private AttributeMapper attributeMapper;

    /**
     * 根据传入的分类id和sel查询参数
     * @param catId 分类的id
     * @param sel   参数的分类  many为动态参数 only为静态属性
     */
    public List<Attribute> getAttributesByCatId(Integer catId,String sel){

        QueryWrapper<Attribute> wrapper = new QueryWrapper<>();

        wrapper
                .select("attr_id","attr_name","cat_id","attr_write","attr_val")
                .eq("attr_sel",sel)
                .eq("cat_id",catId);

        return attributeMapper.selectList(wrapper);
    }

    /**
     * 添加参数对象
     */
    @Transactional
    public boolean insertOneAttribute(Attribute attribute){

        try {
            attributeMapper.insert(attribute);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 根据id和sel获取属性对象
     * @param sel       区别动态参数和静态属性
     */
    public Attribute getAttributeById(Integer catId,Integer attrId,String sel){

        QueryWrapper<Attribute> wrapper = new QueryWrapper<>();

        wrapper
                .select("attr_id","attr_name","cat_id")
                .eq("attr_id",attrId)
                .eq("cat_id",catId)
                .eq("attr_sel",sel);

        return attributeMapper.selectOne(wrapper);
    }

    /**
     * 根据id和sel修改属性
     */
    @Transactional
    public boolean editAttributeById(Integer attrId,String attrName){

        UpdateWrapper<Attribute> wrapper = new UpdateWrapper<>();

        wrapper
                .set("attr_name",attrName)
                .eq("attr_id",attrId);

        try {
            attributeMapper.update(new Attribute(),wrapper);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 根据属性的id删除属性
     * @param attrId       属性的id
     */
    public boolean deleteAttributeById(Integer attrId) {

        try {
            attributeMapper.deleteById(attrId);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 根据传过来的attrVal和attrId来更新attrVal字段
     * 动态参数的可选值列表信息,例如不同的颜色,尺寸
     */
    public boolean updateAttributeByAttrVal(Integer attrId,String attrVal){

        Attribute attribute = new Attribute();
        attribute.setAttrId(attrId);
        attribute.setAttrVal(attrVal);

        try {
            attributeMapper.updateById(attribute);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //获取参数attrVal(可选参数例如颜色: 白色 红色 绿色)
    public Attribute getAttrValById(Integer attrId){

        QueryWrapper<Attribute> wrapper = new QueryWrapper<>();

        wrapper
                .select("attr_val")
                .eq("attr_id",attrId);

        return attributeMapper.selectOne(wrapper);
    }

}
