package com.rimi.ctibet.web.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.DateUtil;
import com.rimi.ctibet.common.util.ListUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.domain.Activity;
import com.rimi.ctibet.domain.EnrollForm;
import com.rimi.ctibet.domain.MemberEnrollDetail;
import com.rimi.ctibet.web.controller.vo.MemberEnrollDetailVO;
import com.rimi.ctibet.web.service.IActivityService;
import com.rimi.ctibet.web.service.IEnrollFormService;
import com.rimi.ctibet.web.service.IMemberEnrollDetailService;

@Controller
@RequestMapping("web/memberEnrollDetailController")
public class MemberEnrollDetailController extends BaseController {
	
	@Resource IMemberEnrollDetailService memberEnrollDetailService;
	@Resource IActivityService activityService;
	@Resource IEnrollFormService enrollFormService;
	
	/**
	 * 活动管理页面 报名列表
	 * web/memberEnrollDetailController/showActManageMemberEnrollDetailList
	 */
	@RequestMapping(value="showActManageMemberEnrollDetailList", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String showActManageMemberEnrollDetailList(Pager pager, String activityCode, String orderChannelSourceCode){
		pager = memberEnrollDetailService.getNewEnrollMemberByActivityCode(pager, activityCode, orderChannelSourceCode, false);
		return obj2json(pager);
	}
	
	/**
	 * 活动管理页面 获取会员的报名详细信息
	 * @return
	 */
	@RequestMapping(value="getEnrollDetail", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String getEnrollDetail(String activityCode, String memberCode){
		List<MemberEnrollDetailVO> listMemberEnrollDetailVO = memberEnrollDetailService.getMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
		return new Gson().toJson(listMemberEnrollDetailVO);	
	}
	
	/**
	 * 置顶会员报名
	 * @return
	 */
	@RequestMapping(value="topMemberEnrollDetail", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String topMemberEnrollDetail(String activityCode, String memberCode){
		try {
			memberEnrollDetailService.updateMemberEnrollDetailIsTop(activityCode, memberCode, 1);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 活动管理页面 置顶会员报名
	 * @return
	 */
	@RequestMapping("topManageMemberEnrollDetail")
	public String topManageMemberEnrollDetail(Model model, String activityCode, String memberCode, Integer actProductPage, Integer enrollFormPage){
		memberEnrollDetailService.updateMemberEnrollDetailIsTop(activityCode, memberCode, 1);
		model.addAttribute("activityCode", activityCode);
		model.addAttribute("actProductPage", (actProductPage==null)?1:actProductPage);
		model.addAttribute("enrollFormPage", (enrollFormPage==null)?1:enrollFormPage);
		return redirect("/web/activityController/forActivityManage");
	}
	/**
	 * 活动管理页面 取消置顶会员报名
	 * @return
	 */
	@RequestMapping("unTopManageMemberEnrollDetail")
	public String unTopManageMemberEnrollDetail(Model model, String activityCode, String memberCode, Integer actProductPage, Integer enrollFormPage){
	    memberEnrollDetailService.updateMemberEnrollDetailIsTop(activityCode, memberCode, 0);
	    model.addAttribute("activityCode", activityCode);
	    model.addAttribute("actProductPage", (actProductPage==null)?1:actProductPage);
	    model.addAttribute("enrollFormPage", (enrollFormPage==null)?1:enrollFormPage);
	    return redirect("/web/activityController/forActivityManage");
	}
	/**
	 * 删除会员报名
	 * @return
	 */
	@RequestMapping(value="deleteMemberEnrollDetail", produces = "text/html; charset=utf-8")
	@ResponseBody
	public String deleteMemberEnrollDetail(String activityCode, String memberCode){
		try {
			memberEnrollDetailService.deleteMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	/**
	 * 活动管理页面 删除会员报名
	 * @return
	 */
	@RequestMapping("deleteManageMemberEnrollDetail")
	public String deleteManageMemberEnrollDetail(Model model, String activityCode, String memberCode, Integer actProductPage, Integer enrollFormPage){
		memberEnrollDetailService.deleteMemberEnrollDetailByActCodeMemberCode(activityCode, memberCode);
		model.addAttribute("actProductPage", (actProductPage==null)?1:actProductPage);
		model.addAttribute("enrollFormPage", (enrollFormPage==null)?1:enrollFormPage);
		model.addAttribute("activityCode", activityCode);
		return redirect("/web/activityController/forActivityManage");
	}
	
	@RequestMapping("exportMemberEnrollDetailExcel")
	public void exportMemberEnrollDetailExcel(HttpServletResponse response, String activityCode, String orderChannelSourceCode){
	    String activityName = "";
	    Activity activity = activityService.findByCode(activityCode);
	    if(activity!=null){
	        activityName = activity.getName();
	    }
	    List<MemberEnrollDetailVO> dataList = memberEnrollDetailService.getNewEnrollMemberByActivityCode(new Pager(-1), activityCode, orderChannelSourceCode, true).getDataList();
	    if(ListUtil.isNotNull(dataList)){
	        
	        for (MemberEnrollDetailVO vo : dataList) {
	            if(vo!=null){
	                List<MemberEnrollDetailVO> list = memberEnrollDetailService.getMemberEnrollDetailByActCodeMemberCode(vo.getActivityCode(), vo.getMemberCode());
	                if(ListUtil.isNotNull(list)){
	                    StringBuffer res = new StringBuffer();
	                    for (int i = 0; i < list.size(); i++){
	                        MemberEnrollDetailVO enroll = list.get(i);
	                        if(enroll!=null){
	                            String property = enroll.getProperty();
	                            String propertyValue = enroll.getPropertyValue();
	                            String kv = property + ":" + propertyValue;
	                            res.append(kv);
	                            if(i < list.size()-1){
	                                res.append(",");
	                            }
	                        }
	                    }
	                    vo.setDetail(res.toString());
	                }
	            }
	        }
	        
	    }
	    String[] titles = {"用户","报名付费","支付状态","报名渠道","报名时间","详情"};
        String[] properties = {"memberName","isEnrollPayName","payStateName","orderChannelSourceName","enrollTime","detail"};
        String format = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getCurrentDate());
        String fileName = format + "《" + activityName + "》报名表单.xls";
	    exportExcel(response, fileName, titles, properties, dataList, "yyyy-MM-dd HH:mm:ss");
	}
	
	@RequestMapping("testExportMemberEnrollDetailExcel")
	public void test(HttpServletResponse response, String activityCode, String orderChannelSourceCode){
	    String activityName = "";
        Activity activity = activityService.findByCode(activityCode);
        if(activity!=null){
            activityName = activity.getName();
        }
	    String format = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getCurrentDate());
        String fileName = format + "《" + activityName + "》报名表单.xls";
	    
        List<MemberEnrollDetailVO> dataList = memberEnrollDetailService.getNewEnrollMemberByActivityCode(new Pager(-1), activityCode, orderChannelSourceCode, true).getDataList();
        
        
        WritableWorkbook book = null;
        try {
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setContentType("application/octet-stream; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // 打开文件
            book = Workbook.createWorkbook(response.getOutputStream());
            // 生成工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet(fileName, 0);
            WritableFont titleFont = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
            WritableCellFormat titleFormat = new WritableCellFormat(titleFont);
            titleFormat.setAlignment(jxl.format.Alignment.CENTRE);
            titleFormat.setBackground(jxl.format.Colour.GRAY_25);
            
            List<EnrollForm> listEnrollForm = enrollFormService.getEnrollFormByActivityCode(activityCode);
            
            String[] titles = {"用户","报名付费","支付状态","报名渠道","报名时间"};
            int col = 0;
            for (String t : titles) {
                Label label = new Label(col, 0, t, titleFormat);
                sheet.addCell(label);
                sheet.setColumnView(col, 25);
                col++;
            }
            for (EnrollForm enrollForm : listEnrollForm) {
                Label label = new Label(col, 0, enrollForm.getProperty(), titleFormat);
                sheet.addCell(label);
                sheet.setColumnView(col, 25);
                col++;
            }
            
            int row = 1;
            for (MemberEnrollDetailVO vo : dataList) {
                
                Label label = null;
                label = new Label(0, row, vo.getMemberName());
                sheet.addCell(label);
                label = new Label(1, row, vo.getIsEnrollPayName());
                sheet.addCell(label);
                label = new Label(2, row, vo.getPayStateName());
                sheet.addCell(label);
                label = new Label(3, row, vo.getOrderChannelSourceName());
                sheet.addCell(label);
                label = new Label(4, row, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vo.getEnrollTime()));
                sheet.addCell(label);
                
                col = titles.length;
                for (EnrollForm enrollForm : listEnrollForm) {
                    MemberEnrollDetail med = memberEnrollDetailService.getMemberEnrollDetailByActCodePropertyCodeMemberCode(activityCode, enrollForm.getCode(), vo.getMemberCode());
                    label = new Label(col, row, med!=null?med.getPropertyValue():"");
                    sheet.addCell(label);
                    col++;
                }
                
                row++;
            }
            
            // 写入数据并关闭文件
            book.write();
            book.close();
        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_NO_CONTENT, "文件不存在");
                e.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
	
}
