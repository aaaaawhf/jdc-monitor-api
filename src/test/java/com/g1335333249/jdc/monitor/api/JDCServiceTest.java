package com.g1335333249.jdc.monitor.api;

import com.g1335333249.jdc.monitor.api.model.jdc.AppRouterPcdnStatus;
import com.g1335333249.jdc.monitor.api.service.JdcService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: guanpeng
 * @Date: Create at 2021/8/25 11:23
 * @Description:
 * @Modified By:
 */
@SpringBootTest(classes = JdcMonitorApiApplication.class)
public class JDCServiceTest {
    @Autowired
    private JdcService jdcService;

    @Test
    public void getPcdnStatus() {
        AppRouterPcdnStatus pcdnStatus = jdcService.getPcdnStatus("976141626094777805", "jd_4bbd5a960a729", "AAJg3QvfAEDbDRUlT5B1erRz2R_avNMd31NyVcWmQ-XDxguoks5y9H5mUdATpKV-4nBoARw0mwd6iQwEZtuyoiWq0wfKPHRR");
        System.out.println(pcdnStatus);
    }
}
