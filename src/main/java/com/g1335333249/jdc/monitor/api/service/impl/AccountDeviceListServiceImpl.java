package com.g1335333249.jdc.monitor.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceList;
import com.g1335333249.jdc.monitor.api.mapper.AccountDeviceListMapper;
import com.g1335333249.jdc.monitor.api.model.jdc.AppRouterPcdnStatus;
import com.g1335333249.jdc.monitor.api.model.jdc.AppRouterStatusDetailInfo;
import com.g1335333249.jdc.monitor.api.model.jdc.router.AppRouterTodayPointDetail;
import com.g1335333249.jdc.monitor.api.model.jdc.router.AppRouterTodayPointInfo;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListService;
import com.g1335333249.jdc.monitor.api.service.JdcService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-15
 */
@Service
public class AccountDeviceListServiceImpl extends ServiceImpl<AccountDeviceListMapper, AccountDeviceList> implements IAccountDeviceListService {
    @Autowired
    private JdcService jdcService;

    @Override
    public void monitor(Long feedId, String pin, String tgt, Long updateUserId) {
        AppRouterPcdnStatus pcdnStatus = jdcService.getPcdnStatus(feedId + "", pin, tgt);
        AppRouterStatusDetailInfo routerStatusDetail = jdcService.getRouterStatusDetail(feedId + "", pin, tgt);
    }

    @Override
    public void updatePoint(Long userAccountId, String tgt, Long updateUserId) {
        // 今日收益
//        AppRouterTodayPointIncome appRouterTodayPointIncome = jdcService.todayPointIncome(tgt);
        // 可兑换积分总数
//        AppRouterPinTotalAvailPoint appRouterPinTotalAvailPoint = jdcService.pinTotalAvailPoint(tgt);
        List<AccountDeviceList> accountDeviceLists = list(Wrappers.<AccountDeviceList>lambdaQuery().eq(AccountDeviceList::getUserAccountId, userAccountId));
        // 今日所有设备收益
        AppRouterTodayPointDetail appRouterTodayPointDetail = jdcService.todayPointDetail(tgt);
        if (appRouterTodayPointDetail != null && !CollectionUtils.isEmpty(appRouterTodayPointDetail.getPointInfos())) {
            for (AppRouterTodayPointInfo pointInfo : appRouterTodayPointDetail.getPointInfos()) {
                accountDeviceLists.stream().filter(s -> StringUtils.equals(s.getDeviceId(), pointInfo.getMac())).forEach(s -> {
                    s.setTodayPointIncome(pointInfo.getTodayPointIncome());
                    s.setAllPointIncome(pointInfo.getAllPointIncome());
                    s.setUpdateTime(new Date());
                    s.setUpdateUserId(updateUserId);
                });
            }
        }
        if (!CollectionUtils.isEmpty(accountDeviceLists)) {
            saveOrUpdateBatch(accountDeviceLists);
        }

    }
}
