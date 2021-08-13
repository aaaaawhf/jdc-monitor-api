package com.g1335333249.jdc.monitor.api.security.task;

import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import com.g1335333249.jdc.monitor.api.service.ISystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author guanpeng
 * @description
 * @date 2020/11/20 6:11 下午
 * @since
 */
@Component
public class InitUserTask {
    @Autowired
    private ISystemUserService iSystemUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        int count = iSystemUserService.count();
        if (count <= 0) {
            iSystemUserService.save(new SystemUser().setName("超级管理员").setUsername("admin").setPassword(passwordEncoder.encode("admin"))
                    .setAccountNonExpired(true).setAccountNonLocked(true)
                    .setCredentialsNonExpired(true).setEnabled(true)

            );
        }
    }
}
