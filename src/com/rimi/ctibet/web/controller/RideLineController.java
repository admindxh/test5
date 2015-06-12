package com.rimi.ctibet.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.rimi.ctibet.common.controller.BaseController;
import com.rimi.ctibet.common.util.CodeFactory;
import com.rimi.ctibet.common.util.FileOperate;
import com.rimi.ctibet.common.util.FileUtil;
import com.rimi.ctibet.common.util.Pager;
import com.rimi.ctibet.common.util.ReadSettingProperties;
import com.rimi.ctibet.common.util.StringUtil;
import com.rimi.ctibet.domain.RideLine;
import com.rimi.ctibet.web.service.IRideLineService;

@Controller
@RequestMapping("web/rideLine")
public class RideLineController extends BaseController implements ServletContextAware {
    
	 public static final  Logger LOGGER = Logger.getLogger(RideLineController.class);
    
    @Resource IRideLineService rideLineService;
    
    ServletContext application;
    
    final String tempPath = "/temp/";
    
    
    /**
     * 跳转到骑行路线列表页面
     */
    @RequestMapping("forRideLineList")
    public String forRideLineList(Model model){
        return "manage/html/ride/ride_line_list";
    }
    /**
     * 加载骑行路线列表数据
     */
    @RequestMapping(value="loadRideLineList", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String loadRideLineList(Pager pager, String name){
        pager = rideLineService.searchRideLineByName(pager, name);
        return obj2json(pager);
    }

    /**
     * 跳转到骑行路线添加页面
     */
    @RequestMapping("forAddRideLine")
    public String forAddRideLine(Model model){
        model.addAttribute("code", CodeFactory.createCode("RL"));
        return "manage/html/ride/ride_line_create";
    }
    /**
     * 添加骑行路线
     */
    @RequestMapping("addRideLine")
    public String addRideLine(RideLine rideLine){
        if(StringUtil.isNotNull(rideLine.getBannerImg())){
            String basePath = application.getRealPath("/");
            String newBannerPath = rideLine.getBannerImg().replace(tempPath, "");
            FileUtil.moveFile(basePath + rideLine.getBannerImg(), basePath+newBannerPath);
            rideLine.setBannerImg(newBannerPath);
        }
        rideLine.setLink(RideLine.DEFUALT_LINK + rideLine.getCode());
        rideLineService.save(rideLine);
        return redirect("forRideLineList");
    }
    
    /**
     * 跳转到骑行路线编辑页面
     */
    @RequestMapping("forEditRideLine")
    public String forEditRideLine(Model model, String code){
        RideLine rideLine = rideLineService.findByCode(code);
        model.addAttribute("rideLine", rideLine);
        return "manage/html/ride/ride_line_edit";
    }
    /**
     * 编辑骑行路线
     */
    @RequestMapping("editRideLine")
    public String editRideLine(RideLine rideLine){
        RideLine oldRideLine = rideLineService.findByCode(rideLine.getCode());
        if(StringUtil.isNotNull(rideLine.getBannerImg())){
            String basePath = application.getRealPath("/");
            String newBannerPath = rideLine.getBannerImg().replace(tempPath, "");
            FileUtil.moveFile(basePath + rideLine.getBannerImg(), basePath+newBannerPath);
            rideLine.setBannerImg(newBannerPath);
        }
        rideLine.setLink(RideLine.DEFUALT_LINK + rideLine.getCode());
        rideLine.setAvaliable(oldRideLine.getAvaliable());
        rideLine.setCreateTime(oldRideLine.getCreateTime());
        rideLineService.updateLogical(rideLine);
        return redirect("forRideLineList");
    }
    
    /**
     * 删除骑行路线
     */
    @RequestMapping("deleteRideLine")
    @ResponseBody
    public String deleteRideLine(String[] codes){
        try {
            rideLineService.deleteRideLines(codes);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }
    
    /**
     * 上传文件
     */
    @RequestMapping(value="fileUpload", produces = "text/html; charset=utf-8")
    @ResponseBody
    public String uploadFile(String code, String floderName, @RequestParam MultipartFile[] files){
        Map<String, String> msgMap = new HashMap<String, String>();
        if(floderName==null || floderName.equals("")){
            LOGGER.info("需要传一个文件夹名字。");
        }
        if(files!=null && files.length>0){
            Date currentDate = new Date(System.currentTimeMillis());
            String month = new SimpleDateFormat("yyyyMM").format(currentDate);
            String basePath = application.getRealPath("/");
            String path = ReadSettingProperties.getValue("upload") + "/img/rideline/" + tempPath + month + "/" + floderName + "/";
            for (int i = 0; i<files.length; i++) {
                MultipartFile multipartFile = files[i];
                String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(currentDate) + i;
                String ext = FileOperate.getFileExt(multipartFile.getOriginalFilename(), true);
                String fileName = code + "-" + time + ext;
                try {
                    FileUtil.uploadFile(multipartFile, basePath + path, fileName);
                    msgMap.put("msg", multipartFile.getOriginalFilename() + " 上传成功！");
                    msgMap.put("filePath", path + fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                    msgMap.put("msg", multipartFile.getOriginalFilename() + "上传失败！");
                }
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(msgMap);
        return json;
    }
    
    /********************************************
     * Setter Getter
     ********************************************/
    @Override
    public void setServletContext(ServletContext arg0) {
        this.application = arg0;
    }
    
}
