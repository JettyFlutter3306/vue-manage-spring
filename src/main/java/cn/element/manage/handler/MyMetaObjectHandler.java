package cn.element.manage.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入策略
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ... ... ");
        this.setFieldValByName("createTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ... ... ");
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
