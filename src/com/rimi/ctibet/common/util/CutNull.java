package com.rimi.ctibet.common.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
/**
 * ObjectMapper重写字符串为空处理
 * @author yuezx
 * @date 2014-3-14
 * @package com.rimi.medical.common.util
 * @copyright 成都睿峰科技有限公司
 * @version V1.0
 */
public class CutNull extends ObjectMapper {
	
	public CutNull() {
		super();
		// 空值处理为空串
		this.getSerializerProvider().setNullValueSerializer(
				new JsonSerializer<Object>() {
					@Override
					public void serialize(Object value, JsonGenerator jg,
							SerializerProvider sp) throws IOException,
							JsonProcessingException {
						if(value instanceof Number){
							jg.writeNumber(0);
						}
						else {
							jg.writeString("");
						}
					}
				});
	}

}
