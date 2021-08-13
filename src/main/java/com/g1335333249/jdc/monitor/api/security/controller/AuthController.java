package com.g1335333249.jdc.monitor.api.security.controller;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.g1335333249.jdc.monitor.api.constant.SystemConstant;
import com.g1335333249.jdc.monitor.api.entity.SystemUser;
import com.g1335333249.jdc.monitor.api.entity.VisitLog;
import com.g1335333249.jdc.monitor.api.exception.UsernameExistsException;
import com.g1335333249.jdc.monitor.api.model.request.LoginModel;
import com.g1335333249.jdc.monitor.api.security.annotation.AnonymousAccess;
import com.g1335333249.jdc.monitor.api.security.filter.TokenProvider;
import com.g1335333249.jdc.monitor.api.security.properties.SecurityProperties;
import com.g1335333249.jdc.monitor.api.security.service.OnlineUserService;
import com.g1335333249.jdc.monitor.api.security.util.SecurityUtils;
import com.g1335333249.jdc.monitor.api.security.util.StringUtils;
import com.g1335333249.jdc.monitor.api.service.ISystemUserService;
import com.g1335333249.jdc.monitor.api.service.IVisitLogService;
import com.g1335333249.jdc.monitor.api.utils.Result;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private ISystemUserService iSystemUserService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${loginCode.expiration}")
    private Long expiration;
    @Value("${rsa.private_key}")
    private String privateKey;
    @Value("${single.login:false}")
    private Boolean singleLogin;
    private final SecurityProperties properties;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private IVisitLogService iVisitLogService;

    @PostMapping("register")
    @AnonymousAccess
    public Result<String> register(@RequestBody @NotNull @Valid SystemUser user) {
        SystemUser dbUser = iSystemUserService.getOne(Wrappers.<SystemUser>lambdaQuery().eq(SystemUser::getUsername, user.getUsername()));
        if (dbUser != null) {
            throw new UsernameExistsException(user.getUsername());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true).setAccountNonLocked(true).setCredentialsNonExpired(true).setEnabled(true).setCreateTime(new Date()).setCreateId(-1L);
        return iSystemUserService.save(user) ? Result.success("注册成功") : Result.fail("注册失败");
    }

    @ApiOperation("登录授权")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public Result<Map<String, Object>> login(@Validated @RequestBody LoginModel loginModel, HttpServletRequest request) {
        if (StringUtils.isBlank(loginModel.getType())) {
            return Result.fail("参数错误");
        }
        String username = null, password = null;
        Map<String, Object> authInfo = new HashMap<>();
        switch (loginModel.getType().toLowerCase()) {
            case SystemConstant.LoginType.MINIAPP:
                if (StringUtils.isBlank(loginModel.getCode())) {
                    return Result.fail("code无效");
                }
//                try {
//                    WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaService.jsCode2SessionInfo(loginModel.getCode());
//                    log.info("miniapp result is {}", wxMaJscode2SessionResult);
//                    authInfo.put("sessionKey", wxMaJscode2SessionResult.getSessionKey());
//                    authInfo.put("openid", wxMaJscode2SessionResult.getOpenid());
//                    authInfo.put("unionid", wxMaJscode2SessionResult.getUnionid());
//                    SystemUserMiniapp systemUserMiniapp = iSystemUserMiniappService.getOne(Wrappers.<SystemUserMiniapp>lambdaQuery().eq(SystemUserMiniapp::getOpenId, wxMaJscode2SessionResult.getOpenid()));
//                    if (systemUserMiniapp == null) {
//                        username = SystemConstant.USERNAME_DEFAULT_PRE + wxMaJscode2SessionResult.getOpenid().substring(0, wxMaJscode2SessionResult.getOpenid().length() - 6);
//                        password = wxMaJscode2SessionResult.getOpenid();
//                        SystemUser systemUser = new SystemUser().setUsername(username).setPassword(passwordEncoder.encode(password)).setAccountNonExpired(true).setAccountNonLocked(true).setCredentialsNonExpired(true).setEnabled(true).setCreateTime(new Date()).setCreateId(-1L);
//                        boolean save = iSystemUserService.save(systemUser);
//                        if (!save) {
//                            return Result.fail("小程序登录失败，请稍后重试");
//                        } else {
//                            systemUserMiniapp = new SystemUserMiniapp().setOpenId(wxMaJscode2SessionResult.getOpenid()).setSystemUserId(systemUser.getId()).setUnionId(wxMaJscode2SessionResult.getUnionid())
//                                    .setCreateId(systemUser.getId()).setCreateTime(new Date());
//                            iSystemUserMiniappService.save(systemUserMiniapp);
//                        }
//                    } else {
//                        SystemUser systemUser = iSystemUserService.getById(systemUserMiniapp.getSystemUserId());
//                        username = systemUser.getUsername();
//                        password = systemUserMiniapp.getOpenId();
//                    }
//                } catch (WxErrorException e) {
//                    e.printStackTrace();
//                    return Result.fail(e.getMessage());
//                }
                break;
            case SystemConstant.LoginType.MP:
                break;
            case SystemConstant.LoginType.WEB:
                // 密码解密
                RSA rsa = new RSA(privateKey, null);
                username = loginModel.getUsername().length() > 30 ? new String(rsa.decrypt(loginModel.getUsername(), KeyType.PrivateKey)) : loginModel.getUsername();
                password = loginModel.getPassword().length() > 30 ? new String(rsa.decrypt(loginModel.getPassword(), KeyType.PrivateKey)) : loginModel.getPassword();
                break;
            default:
                return Result.fail("参数错误");
        }


//        // 查询验证码
//        String code = (String) redisUtils.get(authUser.getUuid());
//        // 清除验证码
//        redisUtils.del(authUser.getUuid());
//        if (StringUtils.isBlank(code)) {
//            throw new BadRequestException("验证码不存在或已过期");
//        }
//        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
//            throw new BadRequestException("验证码错误");
//        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final SystemUser systemUser = (SystemUser) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(systemUser, token, request);
        // 返回 token 与 用户信息
        authInfo.put("token", properties.getTokenStartWith() + token);
        authInfo.put("user", systemUser);
        if (singleLogin) {
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(loginModel.getUsername(), token);
        }
        try {
            iVisitLogService.save(new VisitLog().setRemark("用户登录").setVisitDate(new Date()).setVisitUri(request.getRequestURI()).setVisitUsername(SecurityUtils.getCurrentUsername())
                    .setVisitIp(StringUtils.getIp(request))
                    .setVisitAddress(StringUtils.getCityInfo(StringUtils.getIp(request)))
                    .setVisitBrowser(StringUtils.getBrowser(request))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success(authInfo);
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public Result<UserDetails> getUserInfo() {
        return Result.success(SecurityUtils.getCurrentUser());
    }

//    @AnonymousAccess
//    @ApiOperation("获取验证码")
//    @GetMapping(value = "/code")
//    public ResponseEntity<Object> getCode() {
//        // 算术类型 https://gitee.com/whvse/EasyCaptcha
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
//        // 几位数运算，默认是两位
//        captcha.setLen(2);
//        // 获取运算的结果
//        String result = captcha.text();
//        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
//        // 保存
//        redisUtils.set(uuid, result, expiration, TimeUnit.MINUTES);
//        // 验证码信息
//        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
//            put("img", captcha.toBase64());
//            put("uuid", uuid);
//        }};
//        return ResponseEntity.ok(imgResult);
//    }

    @ApiOperation("退出登录")
    @AnonymousAccess
    @GetMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request) {
        onlineUserService.logout(tokenProvider.getToken(request));
        return Result.success();
    }
}
