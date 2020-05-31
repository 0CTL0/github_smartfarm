package com.smartfarm.base.service.monitor.timer;

import com.smartfarm.base.monitor.core.entity.vo.MonitorRealDataVo;
import com.smartfarm.base.monitor.core.entity.vo.MonitorSoilRealDataVo;
import com.smartfarm.base.monitor.core.manager.MonitorRealDataManager;
import com.smartfarm.base.service.monitor.led.HexUtils;
import com.smartfarm.base.service.monitor.led.LedUpdateCommandMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

@Service("ledJob")
public class LEDJob {
	protected static Logger log = Logger.getLogger(LEDJob.class);
	
	@Autowired
	private MonitorRealDataManager monitorRealDataManager;
	
	
	public void doPostTestOne() {
		java.util.Map<String, Object> data = monitorRealDataManager.queryLogicalData(12L);
		StringBuilder text = new StringBuilder();
		List<MonitorRealDataVo> meteorologicalList = (List<MonitorRealDataVo>) data.get("meteorologicalList");
		List<MonitorRealDataVo> avgSoilList = (List<MonitorRealDataVo>) data.get("avgSoilList");
		List<MonitorRealDataVo> liquidList = (List<MonitorRealDataVo>) data.get("liquidList");
		for(MonitorRealDataVo vo:meteorologicalList) {
			for(MonitorSoilRealDataVo dataVo:vo.getRealDataList()) {
				text.append(dataVo.getName() + ":" + dataVo.getRealValue() + dataVo.getUnit() + " ");
			}
		}
		for(MonitorRealDataVo vo:avgSoilList) {
			for(MonitorSoilRealDataVo dataVo:vo.getRealDataList()) {
				text.append(dataVo.getName() + ":" + dataVo.getRealValue() + dataVo.getUnit() + " ");
			}
		}
		for(MonitorRealDataVo vo:liquidList) {
			for(MonitorSoilRealDataVo dataVo:vo.getRealDataList()) {
				text.append(dataVo.getName() + ":" + dataVo.getRealValue() + dataVo.getUnit() + " ");
			}
		}
//		String content = text.toString();
		String content = "1.把人民群众生命安全和身体健康放在第一位，把疫情防控工作作为当前最重要的工作来抓。\\n2.疫情就是命令，防控就是责任。\\n3.坚定信心、同舟共济、科学防治、精准施策，坚决打赢疫情防控阻击战。\\n4.做好疫情监测、排查、预警等工作，做到早发现、早报告、早隔离、早治疗。\\n5.全力遏制疫情扩散蔓延，维护人民生命安全和身体健康。\\n6.全省动员、全民行动，集中力量坚决打赢疫情防控这场硬仗。";
		try {
			Socket socket = new Socket("119.146.131.66", 58258);
			OutputStream os = socket.getOutputStream();
			new Thread(() -> {
				InputStream s = null;
				try {
					s = socket.getInputStream();
					byte[] buf = new byte[1024];
		            int len = 0;
		            while ((len = s.read(buf)) != -1) {
		                System.out.println(HexUtils.HexByte2String(buf, len));
		                break;
		            }
				} catch (Exception e) {
					e.printStackTrace();
				}
                try {
                	socket.shutdownInput();
				} catch (Exception e) {
					e.printStackTrace();
				}
                try {
                	socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
                try {
                	s.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
                try {
                	os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
			
			LedUpdateCommandMessage m= new LedUpdateCommandMessage(content, 2);
			os.write(m.toByte());
            os.flush();
            socket.shutdownOutput();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
