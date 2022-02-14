package cn.element.service;

import cn.element.mapper.CategoryMapper;
import cn.element.pojo.common.Category;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取标准的分类实体列表
     * 分页查询
     */
    public Page<Category> getCategoryList(Integer pageNum,Integer pageSize){

        List<Category> list = this.getCategoryListAsTree(null); //这是处理成树形数据结构的分类列表,下面需要二次处理

        Page<Category> page = new Page<>(pageNum,pageSize); //直接使用mybatis-plus提供的分页对象,就不自己定义了

        page.setRecords(new ArrayList<>()); //给分页体对象的集合赋值,否则下面会报空指针异常

        for (int i = (pageNum - 1) * pageSize; i < pageNum * pageSize; i++) {
            page.getRecords().add(list.get(i)); //把树形list的元素加到分页的list中去
        }

        page.setTotal(list.size()); //设置总数

        return page;
    }

    /**
     * 获取分类实体的列表
     * 非分页查询
     */
    public List<Category> getCategoryList(){

        return categoryMapper.selectList(null);
    }

    /**
     * 获取所有分类并形成一个树形列表
     * 类似于这样的树形数据结构,递归算法
     *                       --> category1 --> children --> ...
     *
     * category --> children --> category2 --> children --> ...
     *
     *                       --> category3 --> children --> ...
     */
    public List<Category> getCategoryListAsTree(Integer type){

        List<Category> list = this.getCategoryList(); //获取所有的分类列表

        Map<Integer,Category> map = new HashMap<>(); //创建一个map提高查询效率

        list.forEach(r -> { //把list里面的元素放进map中去,老方法,这次需要做一个判断,因为只需要二级分类
            if(type == null){
                map.put(r.getCatId(),r);
            }else if(type == 2){
                if(r.getParentId() == 0){
                    map.put(r.getCatId(),r);
                }
            }
        }); //把所有分类对象放入map

        for (Category category : list) {  //遍历一次list

            if(map.containsKey(category.getParentId())){
                Category category1 = map.get(category.getParentId());

                if(category1.getChildren() == null){
                    category1.setChildren(new ArrayList<>());
                }

                category1.getChildren().add(category);
            }
        }

        //做一次过滤操作,仅留下parentId为0的元素
        return list.stream().filter(s -> s.getParentId() == 0).collect(Collectors.toList());
    }

    //添加分类
    @Transactional
    public boolean addCategory(Category category){

        category.setCatDeleted(0);

        try {
            categoryMapper.insert(category);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //根据id查询
    public Category selectById(Integer catId){

        return categoryMapper.selectById(catId);
    }

    //修改分类信息
    @Transactional
    public boolean editCategory(Category category){

        int i = categoryMapper.updateById(category);

        return i != -1;
    }

    //逻辑删除分类
    @Transactional
    public boolean deleteCategoryCascaded(Integer catId){

        List<Category> idList = this.getCategoryIdAndPIdList();

        List<Integer> list = this.getSubIdListByParentId(catId, new ArrayList<>(), idList);//获取要删除的id列表

        int i = categoryMapper.deleteBatchIds(list);

        return i != -1;
    }

    //获取分类表的id和parentId集合方便对比
    public List<Category> getCategoryIdAndPIdList(){

        QueryWrapper<Category> wrapper = new QueryWrapper<>();

        wrapper.select("cat_id","parent_id");

        return categoryMapper.selectList(wrapper);
    }

    /**
     * 根据分类对象的id获取下级所有的分类的id,不论是三级的还是二级的,用于级联删除
     * @param catId         分类对象的id
     * @param collector     收集器,负责收集下级分类的id
     * @param idList        用来对照的id列表,里面存放着分类对象的id和parentId
     * @return              collector
     */
    public List<Integer> getSubIdListByParentId(Integer catId,List<Integer> collector,List<Category> idList){

        if(!collector.contains(catId)){
            collector.add(catId);
        }

        for (Category category : idList) {
            if(category.getParentId().equals(catId)){
                collector.add(category.getCatId());

                if(this.childExists(category.getCatId(),idList)){ //判断是否含有子节点,如果有,那么就继续递归遍历
                    this.getSubIdListByParentId(category.getCatId(),collector,idList);
                }
            }
        }

        return collector;
    }

    //判断是否有子节点
    private boolean childExists(Integer catId,List<Category> list){

        for (Category category : list) {
            if(category.getParentId().equals(catId)){
                return true;
            }
        }

        return false;
    }






}
