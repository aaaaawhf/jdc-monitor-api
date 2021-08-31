package com.g1335333249.jdc.monitor.api.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.g1335333249.jdc.monitor.api.entity.AccountDeviceList;
import com.g1335333249.jdc.monitor.api.entity.UserNotice;
import com.g1335333249.jdc.monitor.api.mapper.UserNoticeMapper;
import com.g1335333249.jdc.monitor.api.model.BarkRes;
import com.g1335333249.jdc.monitor.api.model.KeyObj;
import com.g1335333249.jdc.monitor.api.model.ServerJRes;
import com.g1335333249.jdc.monitor.api.service.IAccountDeviceListService;
import com.g1335333249.jdc.monitor.api.service.IUserNoticeService;
import com.g1335333249.jdc.monitor.api.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author guanpeng
 * @since 2021-08-17
 */
@Service
@Slf4j
public class UserNoticeServiceImpl extends ServiceImpl<UserNoticeMapper, UserNotice> implements IUserNoticeService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IAccountDeviceListService iAccountDeviceListService;

    private static final String BARK_SERVER_URL = "http://www.jsj1304.com:9990/push";
    private static final String SERVERJ_SERVER_URL = "https://sctapi.ftqq.com/{}.send";

    @Override
    public void sendNotice(Long userId) {
        CompletableFuture.runAsync(() -> {
            UserNotice userNotice = getOne(Wrappers.<UserNotice>lambdaQuery().eq(UserNotice::getUserId, userId).last("limit 1"));
            if (userNotice != null && userNotice.getIsEnabled()) {
                List<AccountDeviceList> accountDeviceLists = iAccountDeviceListService.list(Wrappers.<AccountDeviceList>lambdaQuery().eq(AccountDeviceList::getUserId, userId));
                int sum = accountDeviceLists.stream().mapToInt(AccountDeviceList::getTodayPointIncome).sum();
                switch (userNotice.getNoticeType()) {
                    // server酱
                    case 1:
                        KeyObj keyServer = JsonUtil.GSON.fromJson(userNotice.getConfigJson(), KeyObj.class);
                        sendByServerJ(LocalDate.now() + "积分已更新", "今日共收入" + sum + "积分", keyServer.getKey());
                        break;
                    // bark
                    case 2:
                        KeyObj keyBark = JsonUtil.GSON.fromJson(userNotice.getConfigJson(), KeyObj.class);
                        sendByBark(LocalDate.now() + "积分已更新", "今日共收入" + sum + "积分", keyBark.getKey());
                        break;
                    default:
                }
            }

        });
    }

    @Override
    public void sendByBark(String title, String body, String deviceKey) {
        MultiValueMap<String, String> httpBody = new LinkedMultiValueMap<>();
        httpBody.add("title", title);
        httpBody.add("body", body);
        httpBody.add("device_key", deviceKey);
        httpBody.add("group", "pointPush");
        httpBody.add("sound", "birdsong.caf");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        ParameterizedTypeReference<BarkRes> parameterizedTypeReference = new ParameterizedTypeReference<BarkRes>() {
        };
        ResponseEntity<BarkRes> exchange = restTemplate.exchange(BARK_SERVER_URL, HttpMethod.POST, new HttpEntity<>(httpBody, headers), parameterizedTypeReference);
        try {
            if (StringUtils.equals("success", exchange.getBody().getMessage())) {
                log.info("Bark消息发生成功！");
            }
        } catch (Exception e) {
            log.error("Bark消息发送失败！", e);
        }
    }

    @Override
    public void sendByServerJ(String title, String body, String deviceKey) {
        HttpHeaders headers = new HttpHeaders();
        //  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        //  也支持中文
        params.add("title", title);
        params.add("desp", body);
        ParameterizedTypeReference<ServerJRes> parameterizedTypeReference = new ParameterizedTypeReference<ServerJRes>() {
        };
        ResponseEntity<ServerJRes> exchange = restTemplate.exchange(MessageFormat.format(SERVERJ_SERVER_URL, deviceKey), HttpMethod.POST, new HttpEntity<>(params, headers), parameterizedTypeReference);
        try {
            if (exchange.getBody().getData().getErrno() == 0) {
                log.info("Server酱消息发生成功！");
            }
        } catch (Exception e) {
            log.error("Server酱消息发送失败！", e);
        }
    }
}
