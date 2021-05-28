package cn.element.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface MyMapper<T> extends BaseMapper<T> {

    /**
     * 批量添加
     */
    default void insertBatch(List<T> list){

        for (T t : list) {
            this.insert(t);
        }
    }

}
