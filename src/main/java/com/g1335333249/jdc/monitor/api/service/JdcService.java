package com.g1335333249.jdc.monitor.api.service;

import com.g1335333249.jdc.monitor.api.constant.AppUrlConstant;
import com.g1335333249.jdc.monitor.api.model.jdc.*;
import com.g1335333249.jdc.monitor.api.model.jdc.router.*;
import com.g1335333249.jdc.monitor.api.utils.HmacSha1Util;
import com.g1335333249.jdc.monitor.api.utils.JsonUtil;
import com.g1335333249.jdc.monitor.api.utils.MD5Util;
import com.g1335333249.jdc.monitor.api.utils.OkHttpUtil;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guanpeng
 */
@Service
@Slf4j
public class JdcService {
    public static final String DEVICE_KEY = "ios6.5.5iPhone11,813.7:%s";
    public static final String AUTHORIZATION_TEMPLATE = "smart %s:::%s:::%s";
    public static final String POST_JSON_BODY = "%spostjson_body%s%s%s%s";
    public static final String ACCESS_KEY = "b8f9c108c190a39760e1b4e373208af5cd75feb4";
    public static final String HMAC_KEY = "706390cef611241d57573ca601eb3c061e174948";
    @Autowired
    private RestTemplate restTemplate;

    public <T> AppResult<T> postRequest(String url, String body, String pin, String tgt) {
        LocalDateTime now = LocalDateTime.now();
        MultiValueMap<String, String> headers = new HttpHeaders();
        String nowStr = now + "Z";
        String authorization = encodeAuthorization(body, ACCESS_KEY, nowStr);
        headers.add("Authorization", authorization);
        headers.add("timestamp", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        headers.add("accesskey", ACCESS_KEY);
        headers.add("tgt", tgt);
        headers.add("User-Agent", "ios");
        headers.add("appkey", "996");
        headers.add("pin", pin);
        headers.add("Content-Type", "application/json");
        ParameterizedTypeReference<AppResult<T>> parameterizedTypeReference = new ParameterizedTypeReference<AppResult<T>>() {
        };
        ResponseEntity<AppResult<T>> exchange = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), parameterizedTypeReference);
        AppResult<T> body1 = exchange.getBody();
        return body1;
    }

    public <T> T routerAppApiGetRequest(String url, Class<T> clazz, String tgt) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("wskey", tgt);
        headers.add("User-Agent", "%E4%BA%AC%E4%B8%9C%E4%BA%91%E6%97%A0%E7%BA%BF%E5%AE%9D/1049 CFNetwork/1300.1 Darwin/21.0.0");
        headers.add("Content-Type", "application/json");
        ParameterizedTypeReference<AppRouterResult<Object>> parameterizedTypeReference = new ParameterizedTypeReference<AppRouterResult<Object>>() {
        };
        ResponseEntity<AppRouterResult<Object>> exchange = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), parameterizedTypeReference);
        AppRouterResult<Object> body = exchange.getBody();
        return JsonUtil.GSON.fromJson(JsonUtil.GSON.toJson(body.getResult()), clazz);
    }

    /**
     * 获取设备列表
     *
     * @return
     */
    public List<AppDeviceResult> listAllUserDevices(String pin, String tgt) {
        String body = "{\"appversion\":\"2.7.3\",\"appplatform\":\"iPhone11,8\",\"appplatformversion\":\"13.7\"}";
        Object s = postRequest(AppUrlConstant.LIST_ALL_USER_DEVICES, body, pin, tgt);
        System.out.println(JsonUtil.GSON.toJson(s));
        Type jsonType = new TypeToken<AppResult<List<AppDeviceResult>>>() {
        }.getType();
        AppResult<List<AppDeviceResult>> data = JsonUtil.GSON.fromJson(JsonUtil.GSON.toJson(s), jsonType);
        return data.getResult();
    }

    /**
     * 获取路由状态详情
     *
     * @param feedId
     * @return
     */
    public AppRouterStatusDetailInfo getRouterStatusDetail(String feedId, String pin, String tgt) {
        String body = "{\"feed_id\":\"" + feedId + "\",\"command\":[{\"stream_id\":\"SetParams\",\"current_value\":\"{\\n  \\\"cmd\\\" : \\\"get_router_status_detail\\\"\\n}\"}]}";
        LocalDateTime now = LocalDateTime.now();
        Map<String, String> headers = new HashMap<>();
        String nowStr = now + "Z";
        String authorization = encodeAuthorization(body, ACCESS_KEY, nowStr);
        headers.put("Authorization", authorization);
        headers.put("timestamp", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        headers.put("accesskey", ACCESS_KEY);
        headers.put("tgt", tgt);
        headers.put("User-Agent", "ios");
        headers.put("appkey", "996");
        headers.put("pin", pin);
        String s = OkHttpUtil.postJsonParams(AppUrlConstant.CONTROL_DEVICE, body, headers);
        AppResult appResult = JsonUtil.GSON.fromJson(s, AppResult.class);
        AppRouterStatusDetail appRouterStatusDetail = null;
        try {
            appRouterStatusDetail = JsonUtil.GSON.fromJson(appResult.getResult().toString(), AppRouterStatusDetail.class);
        } catch (Exception e) {
            log.error("获取路由状态报错{}", s);
        }
        return JsonUtil.GSON.fromJson(appRouterStatusDetail.getStreams().get(0).getCurrentValue(), AppRouterStatusDetailInfo.class);
    }

    /**
     * 重启
     *
     * @param feedId
     * @return
     */
    public boolean rebootSystem(String feedId, String pin, String tgt) {
        String body = "{\"feed_id\":\"" + feedId + "\",\"command\":[{\"stream_id\":\"SetParams\",\"current_value\":\"{\\n  \\\"cmd\\\" : \\\"reboot_system\\\"\\n}\"}]}";
        LocalDateTime now = LocalDateTime.now();
        Map<String, String> headers = new HashMap<>();
        String nowStr = now + "Z";
        String authorization = encodeAuthorization(body, ACCESS_KEY, nowStr);
        headers.put("Authorization", authorization);
        headers.put("timestamp", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        headers.put("accesskey", ACCESS_KEY);
        headers.put("tgt", tgt);
        headers.put("User-Agent", "ios");
        headers.put("appkey", "996");
        headers.put("pin", pin);
        String s = OkHttpUtil.postJsonParams(AppUrlConstant.CONTROL_DEVICE, body, headers);
        log.info("重启设备返回数据{}", s);
        AppResult appResult = JsonUtil.GSON.fromJson(s, AppResult.class);
        AppRouterStatusDetail appRouterStatusDetail = null;
        try {
            appRouterStatusDetail = JsonUtil.GSON.fromJson(appResult.getResult().toString(), AppRouterStatusDetail.class);
        } catch (Exception e) {
            log.error("获取路由状态报错{}", s);
        }
        long success = appRouterStatusDetail.getStreams().stream().filter(s1 -> StringUtils.containsIgnoreCase(s1.getCurrentValue(), "success")).count();
        return success > 0;
    }

    /**
     * 获取路由状态详情
     *
     * @param feedId
     * @return
     */
    public AppRouterPcdnStatus getPcdnStatus(String feedId, String pin, String tgt) {
        String body = "{\"feed_id\":\"" + feedId + "\",\"command\":[{\"stream_id\":\"SetParams\",\"current_value\":\"{\\n  \\\"cmd\\\" : \\\"plugin.jdcplugin_opt.get_pcdn_status\\\"\\n}\"}]}";
        LocalDateTime now = LocalDateTime.now();
        Map<String, String> headers = new HashMap<>();
        String nowStr = now + "Z";
        String authorization = encodeAuthorization(body, ACCESS_KEY, nowStr);
        headers.put("Authorization", authorization);
        headers.put("timestamp", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        headers.put("accesskey", ACCESS_KEY);
        headers.put("tgt", tgt);
        headers.put("User-Agent", "ios");
        headers.put("appkey", "996");
        headers.put("pin", pin);
        String s = OkHttpUtil.postJsonParams(AppUrlConstant.CONTROL_DEVICE, body, headers);
        AppResult appResult = JsonUtil.GSON.fromJson(s, AppResult.class);
        AppRouterStatusDetail appRouterStatusDetail = JsonUtil.GSON.fromJson(appResult.getResult().toString(), AppRouterStatusDetail.class);
        return JsonUtil.GSON.fromJson(appRouterStatusDetail.getStreams().get(0).getCurrentValue(), AppRouterPcdnStatus.class);
    }

    /**
     * 获取今日收益
     *
     * @return
     */
    public AppRouterTodayPointIncome todayPointIncome(String tgt) {
        return routerAppApiGetRequest(AppUrlConstant.TODAY_POINT_INCOME, AppRouterTodayPointIncome.class, tgt);
    }

    /**
     * 获取可兑换积分总数
     *
     * @return
     */
    public AppRouterPinTotalAvailPoint pinTotalAvailPoint(String tgt) {
        return routerAppApiGetRequest(AppUrlConstant.PIN_TOTAL_AVAIL_POINT, AppRouterPinTotalAvailPoint.class, tgt);
    }


    /**
     * 获取今日积分详情
     *
     * @return
     */
    public AppRouterTodayPointDetail todayPointDetail(String tgt) {
        AppRouterTodayPointDetail appRouterTodayPointDetail = routerAppApiGetRequest(String.format(AppUrlConstant.TODAY_POINT_DETAIL, 1, 15), AppRouterTodayPointDetail.class, tgt);
        appRouterTodayPointDetail.setPointInfos(allTodayPointDetail(1, 15, null, tgt));
        return appRouterTodayPointDetail;
    }

    public List<AppRouterTodayPointInfo> allTodayPointDetail(Integer page, Integer pageSize, List<AppRouterTodayPointInfo> result, String tgt) {
        if (result == null) {
            result = new ArrayList<>();
        }
        AppRouterTodayPointDetail appRouterTodayPointDetail = routerAppApiGetRequest(String.format(AppUrlConstant.TODAY_POINT_DETAIL, page, pageSize), AppRouterTodayPointDetail.class, tgt);
        AppRouterPageInfo pageInfo = appRouterTodayPointDetail.getPageInfo();
        result.addAll(appRouterTodayPointDetail.getPointInfos());
        if (pageInfo.getTotalPage() == page) {
            return result;
        } else {
            return allTodayPointDetail(page + 1, pageSize, result, tgt);
        }
    }

    /**
     * 获取设备积分详情
     *
     * @return
     */
    public AppRouterPointOperateRecords pointOperateRecords(String mac, String tgt) {
        if (StringUtils.isBlank(mac)) {
            throw new IllegalArgumentException("Mac不能为空");
        }
        return routerAppApiGetRequest(String.format(AppUrlConstant.POINT_OPERATE_RECORDS, mac), AppRouterPointOperateRecords.class, tgt);
    }

    /**
     * 获取设备账户详情
     *
     * @return
     */
    public AppRouterRouterAccountInfo routerAccountInfo(String mac, String tgt) {
        if (StringUtils.isBlank(mac)) {
            throw new IllegalArgumentException("Mac不能为空");
        }
        return routerAppApiGetRequest(String.format(AppUrlConstant.ROUTER_ACCOUNT_INFO, mac), AppRouterRouterAccountInfo.class, tgt);
    }


    public String encodeAuthorization(String body, String accessKey, String nowStr) {
        String deviceKey = MD5Util.getMD5Str(String.format(DEVICE_KEY, LocalDate.now().getDayOfYear()));
        String data = String.format(POST_JSON_BODY, deviceKey, body, nowStr, accessKey, deviceKey);
        String base64HmacSHA1Encrypt = HmacSha1Util.Base64HmacSHA1Encrypt(data, HMAC_KEY);
        return String.format(AUTHORIZATION_TEMPLATE, accessKey, base64HmacSHA1Encrypt, nowStr);
    }
}
