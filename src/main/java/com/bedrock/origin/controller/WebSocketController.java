package com.bedrock.origin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;

import com.bedrock.origin.entity.CarInfo;
import com.bedrock.origin.mapper.slaver.SlaverCarInfoMapper;
import com.bedrock.origin.service.RawService;

@Controller
public class WebSocketController {
	
	@Autowired
	RawService processingService;

	@Autowired
	SlaverCarInfoMapper slaverCarInfoMapper;

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@GetMapping("websocket.html")
	@Transactional(rollbackFor = Exception.class, transactionManager = "slaverTransactionManager")
	public String websocket(ModelMap map)
	{
		/*CarInfo carInfo = new CarInfo();
		carInfo.setId("311111111");
		slaverCarInfoMapper.insertSelective(carInfo);*/
		ListenableFuture send = kafkaTemplate.send("carInfo", "test");
		return "websocket";
	}
    
}