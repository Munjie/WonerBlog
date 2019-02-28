package com.mwj.personweb.schedual;

import com.mwj.personweb.service.IMailService;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.management.ManagementFactory;

/**
 * @Author: 母哥 @Date: 2019-02-28 13:13 @Version 1.0
 */
@Service
public class MailSchedualer {

    @Resource
    private IMailService mailService;

    @Value("${spring.mail.username}")
    private String mailTo;

    @Scheduled(fixedRate = 86400000)
    public void process() {
        StringBuffer result = new StringBuffer();
        long totalMemory = Runtime.getRuntime().totalMemory();
        result.append("使用的总内存为：" + totalMemory / (1024 * 1024) + "MB").append("\n");
        result.append("内存使用率为：" + getMemery()).append("\n");
        mailService.sendSimpleEmail(mailTo, "博客系统运行情况", result.toString());
    }

    public static String getMemery() {

        OperatingSystemMXBean osmxb = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        long totalvirtualMemory = osmxb.getTotalSwapSpaceSize(); // 剩余的物理内存
        long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
        Double compare = (Double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
        String str = compare.intValue() + "%";
        return str;
    }
}
