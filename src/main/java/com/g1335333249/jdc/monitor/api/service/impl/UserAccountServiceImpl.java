package com.g1335333249.jdc.monitor.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceList;
import com.g1335333249.jdc.monitor.api.entity.UserAccount;
import com.g1335333249.jdc.monitor.api.mapper.UserAccountMapper;
import com.g1335333249.jdc.monitor.api.model.jdc.AppDeviceInfo;
import com.g1335333249.jdc.monitor.api.model.jdc.AppDeviceResult;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListService;
import com.g1335333249.jdc.monitor.api.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-15
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements IUserAccountService {
    @Autowired
    private IAccountDeviceListService iAccountDeviceListService;

    @Override
    public void updateAccountDeviceList(List<AppDeviceResult> appDeviceResults, Long updateUserId, UserAccount userAccount) {
        iAccountDeviceListService.update(Wrappers.<AccountDeviceList>lambdaUpdate().eq(AccountDeviceList::getUserAccountId, userAccount.getId()).set(AccountDeviceList::getIsValid, false));
        List<AppDeviceInfo> appDeviceInfos = appDeviceResults.get(0).getList().stream().filter(s -> "213400001".equals(s.getProductId()) || "169500020".equals(s.getProductId()) || "2216000003".equals(s.getProductId())).collect(Collectors.toList());
        for (AppDeviceInfo appDeviceInfo : appDeviceInfos) {
            AccountDeviceList accountDeviceList;
            List<AccountDeviceList> accountDeviceLists = iAccountDeviceListService.list(Wrappers.<AccountDeviceList>lambdaQuery().eq(AccountDeviceList::getFeedId, appDeviceInfo.getFeedId()).eq(AccountDeviceList::getUserAccountId, userAccount.getId()));
            if (CollectionUtils.isEmpty(accountDeviceLists)) {
                accountDeviceList = new AccountDeviceList();
                accountDeviceList.setUserId(userAccount.getUserId());
                accountDeviceList.setUserAccountId(userAccount.getId());
                accountDeviceList.setProductId(appDeviceInfo.getProductId());
                accountDeviceList.setDeviceId(appDeviceInfo.getDeviceId());
                accountDeviceList.setStatus(appDeviceInfo.getStatus());
                accountDeviceList.setConfigType(appDeviceInfo.getConfigType());
                accountDeviceList.setDeviceType(appDeviceInfo.getDeviceType());
                accountDeviceList.setCid(appDeviceInfo.getCid());
                accountDeviceList.setPImgUrl(appDeviceInfo.getPImgUrl());
                accountDeviceList.setDeviceName(appDeviceInfo.getDeviceName());
                accountDeviceList.setFeedId(appDeviceInfo.getFeedId());
                accountDeviceList.setVersion(appDeviceInfo.getVersion());
                accountDeviceList.setOwnFlag(appDeviceInfo.getOwnFlag());
                accountDeviceList.setDeviceAddTime(new Date(appDeviceInfo.getCreateTime()));
                accountDeviceList.setAccessKey(appDeviceInfo.getAccessKey());
                accountDeviceList.setDevicePageType(appDeviceInfo.getDevicePageType());
                accountDeviceList.setCname(appDeviceInfo.getCname());
                accountDeviceList.setIsValid(true);
                accountDeviceList.setCreateTime(new Date());
                accountDeviceList.setCreateUserId(updateUserId);
            } else {
                accountDeviceList = accountDeviceLists.get(0);
                accountDeviceList.setProductId(appDeviceInfo.getProductId());
                accountDeviceList.setDeviceId(appDeviceInfo.getDeviceId());
                accountDeviceList.setStatus(appDeviceInfo.getStatus());
                accountDeviceList.setConfigType(appDeviceInfo.getConfigType());
                accountDeviceList.setDeviceType(appDeviceInfo.getDeviceType());
                accountDeviceList.setCid(appDeviceInfo.getCid());
                accountDeviceList.setPImgUrl(appDeviceInfo.getPImgUrl());
                accountDeviceList.setDeviceName(appDeviceInfo.getDeviceName());
                accountDeviceList.setFeedId(appDeviceInfo.getFeedId());
                accountDeviceList.setVersion(appDeviceInfo.getVersion());
                accountDeviceList.setOwnFlag(appDeviceInfo.getOwnFlag());
                accountDeviceList.setDeviceAddTime(new Date(appDeviceInfo.getCreateTime()));
                accountDeviceList.setAccessKey(appDeviceInfo.getAccessKey());
                accountDeviceList.setDevicePageType(appDeviceInfo.getDevicePageType());
                accountDeviceList.setCname(appDeviceInfo.getCname());
                accountDeviceList.setIsValid(true);
                accountDeviceList.setUpdateTime(new Date());
                accountDeviceList.setUpdateUserId(updateUserId);
            }
            iAccountDeviceListService.saveOrUpdate(accountDeviceList);
        }
        iAccountDeviceListService.updatePoint(userAccount.getId(), userAccount.getTgt(), updateUserId);
    }
}
