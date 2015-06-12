package com.rimi.ctibet.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Role;
import com.rimi.ctibet.domain.RoleAccess;
import com.rimi.ctibet.web.service.IRoleService;

@Controller
@RequestMapping("web/role")
public class RoleController {

	@Resource
	private IRoleService roleService; 
	
	/**权限列表
	 * @param pager
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("roleList")
	public ModelAndView roleList(Pager pager,HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("manage/html/settings/roleList");
		String keyWord = request.getParameter("keyWord");
		Pager roles = roleService.getAllRolesInfo(pager,keyWord);
		return view.addObject("roles",roles).addObject("keyWord", keyWord);
	}
	
	@RequestMapping("saveOrUpdateRole")
	public ModelAndView saveOrUpdateRole(HttpServletRequest request,HttpServletResponse response,String[] value_text,Role role){
		ModelAndView view = new ModelAndView("redirect:/web/role/roleList");
		//编辑
		if(StringUtils.isNotBlank(role.getCode())){
			roleService.updateRole(role);
			if(null!=value_text){
				for (String vt : value_text) {
					String[] vts = vt.split("_");
					RoleAccess rs = new RoleAccess();
					rs.setAccessName(vts[1]);
					rs.setAccessUrl(vts[0]);
					rs.setRoleCode(role.getCode());
					roleService.saveAccessForRole(rs);
				}
			}else{
				RoleAccess rs = new RoleAccess();
				rs.setRoleCode(role.getCode());
				roleService.saveAccessForRole(rs);
			}
		}
		//增加
		else{
			role.setCode(CodeFactory.create("role"));
			if(null!=value_text){
				for (String vt : value_text) {
					String[] vts = vt.split("_");
					RoleAccess rs = new RoleAccess();
					rs.setAccessName(vts[1]);
					rs.setAccessUrl(vts[0]);
					rs.setRoleCode(role.getCode());
					roleService.saveAccessForRole(rs);
				}
			}else{
				RoleAccess rs = new RoleAccess();
				rs.setRoleCode(role.getCode());
				roleService.saveAccessForRole(rs);
			}
			roleService.saveRole(role);
		}
		return view;
	}
	@RequestMapping("gotoSaveOrUpdateRole")
	public ModelAndView gotoSaveOrUpdateRole(HttpServletRequest request,HttpServletResponse response){
        ModelAndView view = new ModelAndView("manage/html/settings/RoleAdd");		
        String code = request.getParameter("code");
        Role role = roleService.getRoleBuCode(code);
		if(role!=null){
	        List<Map<String,Object>> access = roleService.getAllAccessByRoleCode(code);
			String accessString = "";
			for (Map<String, Object> map : access) {
				accessString += (String)map.get("access");
				accessString +=",";
			}
			view.addObject("accessString",accessString);
		}
        return view.addObject("role", role);
	}
	@RequestMapping("deleteRole")
	public ModelAndView deleteRole(HttpServletRequest request,HttpServletResponse response){
		ModelAndView view = new ModelAndView("redirect:/web/role/roleList");
		String code = request.getParameter("code");
		String[] codes = code.split(",");
		for (String scode : codes) {
			Role role = roleService.getRoleBuCode(scode);
			roleService.deleteRole(role);
		}
		return view;
	}
	@RequestMapping("isRoleNameRepeat")
	public @ResponseBody boolean isRoleNameRepeat(String roleName,String code){
		return roleService.isRoleNameRepeat(roleName,code);
	}
}
