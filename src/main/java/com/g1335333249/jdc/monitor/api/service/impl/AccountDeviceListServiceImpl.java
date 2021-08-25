package com.g1335333249.jdc.monitor.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceList;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceListSpeedMonitor;
import com.g1335333249.jdc.monitor.api.mapper.AccountDeviceListMapper;
import com.g1335333249.jdc.monitor.api.model.Dashboard;
import com.g1335333249.jdc.monitor.api.model.jdc.AppRouterPcdnStatus;
import com.g1335333249.jdc.monitor.api.model.jdc.AppRouterStatusDetailInfo;
import com.g1335333249.jdc.monitor.api.model.jdc.router.AppRouterTodayPointDetail;
import com.g1335333249.jdc.monitor.api.model.jdc.router.AppRouterTodayPointInfo;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListService;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListSpeedMonitorService;
import com.g1335333249.jdc.monitor.api.service.JdcService;
import com.g1335333249.jdc.monitor.api.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
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
    @Autowired
    private IAccountDeviceListSpeedMonitorService iAccountDeviceListSpeedMonitorService;

    @Override
    public void monitor(AccountDeviceList accountDeviceList, String pin, String tgt, Long updateUserId, Calendar now) {
        try {
            AccountDeviceList deviceList = getById(accountDeviceList.getId());
            AppRouterPcdnStatus pcdnStatus = jdcService.getPcdnStatus(accountDeviceList.getFeedId() + "", pin, tgt);
            log.error(JsonUtil.GSON.toJson(pcdnStatus));
            List<AppRouterPcdnStatus.DataBean.PcdnListBean> pcdnList = pcdnStatus.getData().getPcdnList();
            for (int i = 0; i < pcdnList.size(); i++) {
                AppRouterPcdnStatus.DataBean.PcdnListBean pcdnListBean = pcdnList.get(i);
                if (i == 0) {
                    deviceList.setPluginOneName(pcdnListBean.getName());
                    deviceList.setPluginOneCacheSize(pcdnListBean.getCacheSize());
                    deviceList.setPluginOneIsExt(pcdnListBean.getPluginIsext());
                    deviceList.setPluginOneRunPos(pcdnListBean.getPluginRunpos());
                    deviceList.setPluginOneStatus(pcdnListBean.getStatus());
                } else if (i == 1) {
                    deviceList.setPluginTwoName(pcdnListBean.getName());
                    deviceList.setPluginTwoCacheSize(pcdnListBean.getCacheSize());
                    deviceList.setPluginTwoIsExt(pcdnListBean.getPluginIsext());
                    deviceList.setPluginTwoRunPos(pcdnListBean.getPluginRunpos());
                    deviceList.setPluginTwoStatus(pcdnListBean.getStatus());
                }
            }
            saveOrUpdate(deviceList);
        } catch (Exception e) {
            log.error("获取插件异常！", e);
        }
        AppRouterStatusDetailInfo routerStatusDetail = jdcService.getRouterStatusDetail(accountDeviceList.getFeedId() + "", pin, tgt);
        if (routerStatusDetail.getData() != null) {
            AppRouterStatusDetailInfo.DataBean dataBean = routerStatusDetail.getData();
            try {
                AccountDeviceListSpeedMonitor accountDeviceListSpeedMonitor = new AccountDeviceListSpeedMonitor();
                accountDeviceListSpeedMonitor.setAccountDeviceListId(accountDeviceList.getId());
                accountDeviceListSpeedMonitor.setUpload(Long.parseLong(dataBean.getUpload()));
                accountDeviceListSpeedMonitor.setDownload(Long.parseLong(dataBean.getDownload()));
                accountDeviceListSpeedMonitor.setCpu(Double.parseDouble(dataBean.getCpu()));
                accountDeviceListSpeedMonitor.setMem(Integer.parseInt(dataBean.getMem()));
                accountDeviceListSpeedMonitor.setEventTime(now.getTime());
                accountDeviceListSpeedMonitor.setCreateTime(new Date());
                accountDeviceListSpeedMonitor.setWanip(dataBean.getWanip());
                iAccountDeviceListSpeedMonitorService.save(accountDeviceListSpeedMonitor);

                AccountDeviceList deviceList = getById(accountDeviceList.getId());
                if (deviceList != null) {
                    deviceList.setRom(dataBean.getRom());
                    deviceList.setSn(dataBean.getSn());
                    deviceList.setModel(dataBean.getModel());
                    deviceList.setModelName(dataBean.getModelName());
                    deviceList.setApMode(dataBean.getApMode());
                    deviceList.setOnlineTime(Long.parseLong(dataBean.getOnlineTime()));
                    deviceList.setInternetIp(dataBean.getWanip());
                    saveOrUpdate(deviceList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    @Override
    public Page<AccountDeviceList> pageWithSpeed(Page<AccountDeviceList> page, Wrapper<AccountDeviceList> wrapper) {
        return getBaseMapper().pageWithSpeed(page, wrapper);
    }

    @Override
    public Dashboard dashboard(Long userId) {
        return getBaseMapper().dashboard(userId);
    }
}
