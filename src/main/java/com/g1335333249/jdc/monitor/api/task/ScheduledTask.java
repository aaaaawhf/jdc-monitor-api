package com.g1335333249.jdc.monitor.api.task;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceList;
import com.g1335333249.jdc.monitor.api.entity.UserAccount;
import com.g1335333249.jdc.monitor.api.model.jdc.AppDeviceResult;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListService;
import com.g1335333249.jdc.monitor.api.service.IUserAccountService;
import com.g1335333249.jdc.monitor.api.service.IUserNoticeService;
import com.g1335333249.jdc.monitor.api.service.JdcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: guanpeng
 * @Date: Create at 2021/8/16 15:55
 * @Description:
 * @Modified By:
 */
@Component
@Slf4j
public class ScheduledTask {

    @Autowired
    private IUserAccountService iUserAccountService;
    @Autowired
    private IAccountDeviceListService iAccountDeviceListService;
    @Autowired
    private JdcService jdcService;
    @Autowired
    private IUserNoticeService iUserNoticeService;

    private static final Map<Long, UserAccount> USER_ACCOUNT_MAP = new HashMap<>();

    @Scheduled(cron = "0 30 7 * * *")
    public void updatePoint() {
        USER_ACCOUNT_MAP.clear();
        List<UserAccount> userAccountList = iUserAccountService.list(Wrappers.<UserAccount>lambdaQuery().eq(UserAccount::getIsValid, true));
        userAccountList.forEach(s -> USER_ACCOUNT_MAP.put(s.getId(), s));
        userAccountList.stream().collect(Collectors.groupingBy(UserAccount::getUserId)).forEach((k, v) -> {
            v.forEach(s -> {
                try {
                    List<AppDeviceResult> appDeviceResults = jdcService.listAllUserDevices(s.getPin(), s.getTgt());
                    iUserAccountService.updateAccountDeviceList(appDeviceResults, -1L, s);
                } catch (Exception e) {
                    log.error("获取积分错误{}", s.getId(), e);
                }
            });
            try {
                iUserNoticeService.sendNotice(k);
            } catch (Exception ignored) {

            }
        });
    }

    @PostConstruct
    public void init() {
        USER_ACCOUNT_MAP.clear();
        List<UserAccount> userAccountList = iUserAccountService.list(Wrappers.<UserAccount>lambdaQuery().eq(UserAccount::getIsValid, true));
        userAccountList.forEach(s -> USER_ACCOUNT_MAP.put(s.getId(), s));
        log.info("USER_ACCOUNT_MAP初始化完成，共{}条", USER_ACCOUNT_MAP.size());
    }

    @Scheduled(cron = "0 0/5 * * * *")
    public void updateMonitor() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        List<AccountDeviceList> list = iAccountDeviceListService.list(Wrappers.<AccountDeviceList>lambdaQuery().eq(AccountDeviceList::getIsValid, true));
        list.parallelStream().forEach(s -> {
            try {
                UserAccount userAccount = USER_ACCOUNT_MAP.get(s.getUserAccountId());
                if (userAccount == null) {
                    userAccount = iUserAccountService.getById(s.getUserAccountId());
                    if (userAccount == null) {
                        return;
                    }
                    USER_ACCOUNT_MAP.put(s.getUserAccountId(), userAccount);
                }
                iAccountDeviceListService.monitor(s, userAccount.getPin(), userAccount.getTgt(), -1L, now);
            } catch (Exception e) {
                log.error("获取速度错误{}", s.getId(), e);
            }
        });


    }
}
