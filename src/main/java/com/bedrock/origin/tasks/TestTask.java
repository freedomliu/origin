package com.bedrock.origin.tasks;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class TestTask {

	// 定义每过3秒执行任务
	// @Scheduled(fixedRate = 3000)
	// 网站有惊喜: http://cron.qqe2.com/
    public void reportCurrentTime() {
        System.out.println("现在时间：" + new Date());
    }
}
