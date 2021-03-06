package com.alqsoft.service.msgpush;

import java.util.List;
import java.util.Map;

import org.alqframework.easyui.EasyuiResult;
import org.alqframework.orm.BaseService;

import com.alqsoft.entity.msgpush.PushContentLog;
import com.alqsoft.utils.easyuiweb.EasyuiWebResult;

/**
 * 
 * @Title: PushContentLogService.java
 * @Description: TODO
 * @author chenzhenbing
 * @e-mail chenzb@jinledou.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午6:09:10
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public interface PushContentLogService extends BaseService<PushContentLog>{
	public EasyuiResult<List<PushContentLog>> getPushContentLogPage(Map<String, Object> map,Integer page,Integer rows);
	
	public PushContentLog getPushContentLogByUniqueKey(String uniqueKey);
	/**
	 * 客户端接口使用的分页方法
	 * @Title: getPushContentLogPageByMobile
	 * @Description: TODO
	 * @param: @param map
	 * @param: @param page
	 * @param: @param rows
	 * @param: @return
	 * @return: EasyuiWebResult<List<PushContentLog>>
	 * @throws
	 */
	public EasyuiWebResult<List<PushContentLog>> getPushContentLogPageByMobile(Map<String, Object> map,Integer page,Integer rows);
}
