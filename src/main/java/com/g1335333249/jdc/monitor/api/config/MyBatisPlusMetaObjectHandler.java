package com.g1335333249.jdc.monitor.api.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import com.g1335333249.jdc.monitor.api.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author guanpeng
 * @date 2019-12-10 09:46
 */
@Component
@Slf4j
public class MyBatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert ....");
//        Object createTime = this.getFieldValByName("createTime", metaObject);
//        if (createTime == null) {
//            this.setFieldValByName("createTime", new Date(), metaObject);
//        }
//        Object createId = this.getFieldValByName("createUserId", metaObject);
//        if (createId == null) {
//            try {
//                SystemUser currentUser = SecurityUtils.getCurrentUser();
//                this.setFieldValByName("createUserId", currentUser.getUserId(), metaObject);
//            } catch (Exception e) {
//                this.setFieldValByName("createUserId", -1L, metaObject);
//            }
//        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update ....");
//        Object createTime = this.getFieldValByName("updateTime", metaObject);
//        if (createTime == null) {
//            this.setFieldValByName("updateTime", new Date(), metaObject);
//        }
//        Object createId = this.getFieldValByName("updateUserId", metaObject);
//        if (createId == null) {
//            try {
//                SystemUser currentUser = (SystemUser) SecurityUtils.getCurrentUser();
//                this.setFieldValByName("updateUserId", currentUser.getUserId(), metaObject);
//            } catch (Exception e) {
//                this.setFieldValByName("updateUserId", -1L, metaObject);
//            }
//        }
    }
}
