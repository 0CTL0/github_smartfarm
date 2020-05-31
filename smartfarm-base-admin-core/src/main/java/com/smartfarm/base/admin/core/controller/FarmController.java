package com.smartfarm.base.admin.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smartfarm.base.admin.core.entity.FarmInfo;
import com.smartfarm.base.admin.core.manager.FarmManager;
import com.smartfarm.base.util.RandomUtil;
import com.smartfarm.base.util.SessionUtil;
import com.smartfarm.base.util.UploadUtil;
import com.smartfarm.base.farm.core.entity.FarmBaseInfo;
import com.smartfarm.base.farm.core.entity.FarmCropsInfo;
import com.smartfarm.base.farm.core.entity.FarmRentLandInfo;
import com.smartfarm.base.farm.core.entity.FarmTunnelInfo;

@Controller
@RequestMapping("/farmInfo")
public class FarmController {

    private Logger log = Logger.getLogger(FarmController.class);
    @Resource
    private FarmManager farmManager;

    /**
     * 查询所有农场
     * @return
     */
    @RequestMapping("/queryAll")
    @ResponseBody
    public Object queryAll(){
        Map<String,Object> map=new HashMap<String,Object>();
       try{
           log.info("[FarmController:queryAll]query all farm info.");
           map.put("list", farmManager.getAllFarm());
           map.put("success", true);
       }
       catch(Exception e){
           map.put("success", false);
           log.error("[FarmController:queryAll]false to query all farm info.", e);
       }
        return map;
    }
    
    /**
     * 分页查询
     * @param name
     * @param status
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @RequestMapping("/getFarmList")
    @ResponseBody
    public Object getFarmList(String name, Short status, Integer pageSize, Integer pageNumber){
        Map<String,Object> map=new HashMap<String,Object>();
       try{
           log.info("[FarmController:getFarmList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
           map.put("farmInfoList", farmManager.getFarmInfoList(name,status,(pageNumber - 1) * pageSize, pageSize));
           map.put("total", farmManager.countFarmTotal(name,status));
           map.put("success", true);
       }
       catch(Exception e){
           map.put("success", false);
           log.error("[FarmController:getFarmList]false", e);
       }
        return map;
    }

    @RequestMapping("/addFarm")
    @ResponseBody
    public Object addFarm(FarmInfo farmInfo, @RequestParam(value = "file", required = false) MultipartFile  file){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:addFarm]{farmInfo:" + farmInfo +"}.");
            String pic= UploadUtil.uploadFile(file, "/upload/", RandomUtil.generateNumber(10));
            farmInfo.setPic(pic);
            farmInfo.setStatus(FarmInfo.STATUS_ENALBE);
            farmManager.addFarmInfo(farmInfo);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:addFarm]false", e);
        }
        return map;
    }

    @RequestMapping("/getFarm")
    @ResponseBody
    public Object getFarm(HttpServletRequest request,Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:getFarm]{enter}.");
            Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            map.put("farm",farmManager.getFarmInfo(farmId));
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarm]false", e);
        }
        return map;
    }

    @RequestMapping("/editFarm")
    @ResponseBody
    public Object editFarm(FarmInfo farmInfo,@RequestParam(value = "file", required = false) MultipartFile  file){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:editFarm]{farmInfo:" + farmInfo +"}.");
            if(file!=null){
                String pic= UploadUtil.uploadFile(file, "/upload/", RandomUtil.generateNumber(10));
                farmInfo.setPic(pic);
            }
            farmManager.editFarmInfo(farmInfo);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:editFarm]false", e);
        }
        return map;
    }

    @RequestMapping("/changeFarmStatus")
    @ResponseBody
    public Object changeFarmStatus(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:changeFarmStatus]{id:" + id +"}.");
            farmManager.changeFarmStatus(id);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:changeFarmStatus]false", e);
        }
        return map;
    }


    @RequestMapping("/editFarmContract")
    @ResponseBody
    public Object editFarmContract(HttpServletRequest request,@RequestParam(value = "file", required = false) MultipartFile  file){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:editFarmContract]enter");
                FarmInfo farmInfo=new FarmInfo();
                Long farmId = (Long)SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
                farmInfo.setId(farmId);
                String contractPicUrl= UploadUtil.uploadFile(file, "/upload/", RandomUtil.generateNumber(10));
                farmInfo.setContractUrl(contractPicUrl);
                farmManager.editFarmInfo(farmInfo);
                map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:editFarmContract]false", e);
        }
        return map;
    }

    @RequestMapping("/getFarmBaseList")
    @ResponseBody
    public Object getFarmBaseList(HttpServletRequest request, String name, Short status, Integer pageSize, Integer pageNumber){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
        	Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            log.info("[FarmController:getFarmBaseList]{"+"farmId:"+farmId+" name:"+name+" status:"+status+" pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
            map.put("farmBaseInfoList", farmManager.getFarmBaseList(farmId,name,status,(pageNumber - 1) * pageSize, pageSize));
            map.put("total", farmManager.countBaseTotal(farmId,name,status));
            map.put("farmList",farmManager.getAllFarm());
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarmBaseList]false", e);
        }
        return map;
    }

    @RequestMapping("/addFarmBase")
    @ResponseBody
    public Object addFarmBase(HttpServletRequest request,FarmBaseInfo farmBaseInfo,@RequestParam(value = "file", required = false) MultipartFile  file){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:addFarmBase]{farmBaseInfo:" + farmBaseInfo +"}.");
            Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            String pic= UploadUtil.uploadFile(file, "/upload/", RandomUtil.generateNumber(8));
            farmBaseInfo.setPic(pic);
            farmBaseInfo.setFarmId(farmId);
            farmManager.addFarmBaseInfo(farmBaseInfo);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:addFarmBase]false", e);
        }
        return map;
    }

    @RequestMapping("/getFarmBase")
    @ResponseBody
    public Object getFarmBase(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:getFarmBase]{id:" + id +"}.");
            map.put("farmBaseInfo",farmManager.getFarmBaseInfo(id));
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarmBase]false", e);
        }
        return map;
    }

    @RequestMapping("/editFarmBase")
    @ResponseBody
    public Object editFarmBase(FarmBaseInfo farmBaseInfo,@RequestParam(value = "file", required = false) MultipartFile  file){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:editFarmBase]{farmBaseInfo:" + farmBaseInfo +"}.");
            if(file!=null){
                String pic= UploadUtil.uploadFile(file, "/upload/", RandomUtil.generateNumber(10));
                farmBaseInfo.setPic(pic);
            }
            farmManager.editFarmBaseInfo(farmBaseInfo);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:editFarmBase]false", e);
        }
        return map;
    }

    @RequestMapping("/changeFarmBaseStatus")
    @ResponseBody
    public Object changeFarmBaseStatus(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:changeFarmBaseStatus]{id:" + id +"}.");
            farmManager.changeFarmBaseStatus(id);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:changeFarmBaseStatus]false", e);
        }
        return map;
    }


    @RequestMapping("/getFarmTunnelList")
    @ResponseBody
    public Object getFarmTunnelList(HttpServletRequest request,Long baseId, String name, Short status, Integer pageSize, Integer pageNumber){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[FarmController:getFarmTunnelList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
            Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            map.put("farmTunnelList", farmManager.getFarmTunnelList(baseId,farmId,name,status,(pageNumber - 1) * pageSize, pageSize));
            map.put("total", farmManager.countTunnelTotal(baseId,farmId,name,status));
            map.put("farmBaseList",farmManager.getALLFarmBase(farmId));
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarmTunnelList]false", e);
        }
        return map;
    }

    @RequestMapping("/addFarmTunnel")
    @ResponseBody
    public Object addFarmTunnel(FarmTunnelInfo farmTunnelInfo,@RequestParam(value = "file",
            required = false) MultipartFile file,@RequestParam(value = "files", required = false) MultipartFile[] files){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:addFarmTunnel]{farmTunnelInfo:" + farmTunnelInfo +"}.");
            log.info("[FarmController:addFarmRentLand]{ MultipartFile:" + file);
            log.info("[FarmController:addFarmRentLand]{MultipartFiles[]:" + files.length );
            String mainPic=UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(10));
            String detailPicStr="";
            for(MultipartFile f :files){
                String path = UploadUtil.uploadFile(f, "/upload/",RandomUtil.generateNumber(30));
                detailPicStr=detailPicStr+path+";";
            }
            detailPicStr=detailPicStr.substring(0, detailPicStr.length()-1);
            farmTunnelInfo.setMainPic(mainPic);
            farmTunnelInfo.setDetailPic(detailPicStr);
            farmManager.addFarmTunnelInfo(farmTunnelInfo);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:addFarmTunnel]false", e);
        }
        return map;
    }

    @RequestMapping("/getFarmTunnel")
    @ResponseBody
    public Object getFarmTunnel(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:getFarmTunnel]{id:" + id +"}.");
            map.put("farmTunnelInfo",farmManager.getFarmTunnelInfo(id));
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarmTunnel]false", e);
        }
        return map;
    }

    @RequestMapping("/editFarmTunnel")
    @ResponseBody
    public Object editFarmTunnel(FarmTunnelInfo farmTunnelInfo,@RequestParam(value = "file",
            required = false) MultipartFile file,@RequestParam(value = "files", required = false) MultipartFile[] files){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:editFarmTunnel]{farmTunnelInfo:" + farmTunnelInfo +"}.");
            log.info("[FarmController:addFarmRentLand]{ MultipartFile:" + file);
            log.info("[FarmController:addFarmRentLand]{MultipartFiles[]:" + files.length );
            if(file!=null){
                String mainPic=UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(10));
                farmTunnelInfo.setMainPic(mainPic);
            }
            if(files.length!=0){
                String detailPicStr="";
                for(MultipartFile f :files){
                    String path = UploadUtil.uploadFile(f, "/upload/",RandomUtil.generateNumber(30));
                    detailPicStr=detailPicStr+path+";";
                    detailPicStr=detailPicStr.substring(0, detailPicStr.length()-1);
                    farmTunnelInfo.setDetailPic(detailPicStr);
                }
            }
            farmManager.editFarmTunnelInfo(farmTunnelInfo);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:editFarmTunnel]false", e);
        }
        return map;
    }

    @RequestMapping("/changeFarmTunnelStatus")
    @ResponseBody
    public Object changeFarmTunnelStatus(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:changeFarmTunnelStatus]{id:" + id +"}.");
            farmManager.changeFarmTunnelStatus(id);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:changeFarmTunnelStatus]false", e);
        }
        return map;
    }


    @RequestMapping("/getFarmRentLandList")
    @ResponseBody
    public Object getFarmRentLandList(Long tunnelId, String name, Short status, Integer pageSize, Integer pageNumber){
        Map<String,Object> map=new HashMap<String,Object>();
        try{
            log.info("[FarmController:getFarmRentLandList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + ",tunnelId:" +tunnelId+ "}.");
            map.put("farmBaseInfoList", farmManager.getFarmRentLandList(tunnelId,name,status,(pageNumber - 1) * pageSize, pageSize));
            map.put("total", farmManager.countRentLandTotal(tunnelId,name,status));
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarmRentLandList]false", e);
        }
        return map;
    }

    @RequestMapping("/addFarmRentLand")
    @ResponseBody
    public Object addFarmRentLand(FarmRentLandInfo farmRentLandInfo){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:addFarmRentLand]{farmRentLandInfo:" + farmRentLandInfo +"}.");
            farmManager.addFarmRentLandInfo(farmRentLandInfo);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:addFarmRentLand]false", e);
        }
        return map;
    }

    @RequestMapping("/getFarmRentLand")
    @ResponseBody
    public Object getFarmRentLand(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:getFarmRentLand]{id:" + id +"}.");
            map.put("rentLand",farmManager.getFarmRentLandInfo(id));
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarmRentLand]false", e);
        }
        return map;
    }

    @RequestMapping("/editFarmRentLand")
    @ResponseBody
    public Object editFarmRentLand(FarmRentLandInfo farmRentLandInfo){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:editFarmRentLand]{farmRentLandInfo:" + farmRentLandInfo +"}.");
            farmManager.editFarmRentLandInfo(farmRentLandInfo);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:editFarmRentLand]false", e);
        }
        return map;
    }

    @RequestMapping("/changeFarmRentLandStatus")
    @ResponseBody
    public Object changeFarmRentLandStatus(Long id){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            log.info("[FarmController:changeFarmRentLandStatus]{id:" + id +"}.");
            farmManager.changeFarmRentLandStatus(id);
            map.put("success", true);
        }
        catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:changeFarmRentLandStatus]false", e);
        }
        return map;
    }

    /**
     * 获得农作物列表及查询
     * @param status
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @RequestMapping("getFarmCropsList")
    @ResponseBody
    public Object getFarmCropsList(HttpServletRequest request,String name, Short status, Integer pageSize, Integer pageNumber) {
        Map<String, Object>map = new HashMap<String, Object>();
        try{
            log.info("[FarmController:getFarmCropsList]{pageSize:" + pageSize + ",pageNumber:" + pageNumber + "}.");
            Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            List<FarmCropsInfo> cropsInfoList = farmManager.getCropsInfoPageList(farmId,name, status, (pageNumber - 1) * pageSize, pageSize);
            map.put("cropsInfoList", cropsInfoList);
            map.put("total", farmManager.countCropsInfoPageList(farmId,name,status));
            map.put("success", true);
        }catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarmCropsList]false to query seedInfo list.", e);
        }

        return map;
    }

    /**
     * 改变农作物的状态
     * @param request
     * @return
     */
    @RequestMapping("changeStatus")
    @ResponseBody
    public Object changeStatus(HttpServletRequest request,@RequestParam("id")Long id) {
        Map<String, Object>map = new HashMap<String, Object>();
        try{
            log.info("[FarmController:changeStatus]{seedInfo id:" + id + "}.");
            FarmCropsInfo farmCropsInfo=farmManager.getFarmCropsInfo(id);
            if(farmCropsInfo.getStatus()==FarmCropsInfo.STATUS_UNABLE){
                farmCropsInfo.setStatus(FarmCropsInfo.STATUS_ABLE);
            }
            else{
                farmCropsInfo.setStatus(FarmCropsInfo.STATUS_UNABLE);
            }
            farmManager.editCropsInfo(farmCropsInfo);
            map.put("success", true);
        }catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:changeStatus]false", e);
        }
        return map;
    }

    /**
     * 新增农作物信息
     * @param file
     * @param files
     * @return
     */
    @RequestMapping("/addFarmCropsInfo")
    @ResponseBody
    public Object addFarmCropsInfo(HttpServletRequest request,FarmCropsInfo farmCropsInfo,@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "files", required = false) MultipartFile[] files) {
        Map<String, Object>map = new HashMap<String, Object>();
        try{
            log.info("[FarmController:addSeedInfo]{ farmCropsInfo:" + farmCropsInfo);
            Long farmId = (Long) SessionUtil.getAttribute(request, SessionUtil.FARM_ID);
            String coverStr=UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(10));
            String carouselListStr="";
            for(MultipartFile f :files){
                String path = UploadUtil.uploadFile(f, "/upload/",RandomUtil.generateNumber(12));
                carouselListStr=carouselListStr+path+";";
            }
            carouselListStr=carouselListStr.substring(0, carouselListStr.length()-1);
            Short status=FarmCropsInfo.STATUS_ABLE;
            farmCropsInfo.setCover(coverStr);
            farmCropsInfo.setStatus(status);
            farmCropsInfo.setCarousel(carouselListStr);
            farmCropsInfo.setFarmId(farmId);
            farmManager.addCropsInfo(farmCropsInfo);
            map.put("success", true);
        }catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:addStepAndGetId]false ", e);
        }
        return map;
    }

    /**
     * 获得种子信息
     * @return
     */
    @RequestMapping("getFarmCropsInfo")
    @ResponseBody
    public Object getFarmCropsInfo(@RequestParam("id")Long id) {
        Map<String, Object>map = new HashMap<String, Object>();
        try{
            log.info("[FarmController:getFarmCropsInfo]{id:" + id + "}.");
            FarmCropsInfo farmCropsInfo=farmManager.getFarmCropsInfo(id);
            map.put("farmCropsInfo", farmCropsInfo);
            map.put("success", true);
        }catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:getFarmCropsInfo", e);
        }
        return map;
    }

    /**
     * 编辑种子信息
     * @param file
     * @param files
     * @return
     */
    @RequestMapping("/editFarmCropsInfo")
    @ResponseBody
    public Object editFarmCropsInfo(FarmCropsInfo farmCropsInfo,@RequestParam(value = "file", required = false) MultipartFile file,@RequestParam(value = "files", required = false) MultipartFile[] files) {
        Map<String, Object>map = new HashMap<String, Object>();
        try{
            log.info("[FarmController:editSeedInfo]{ farmCropsInfo:" + farmCropsInfo);
            if(file!=null){
                String coverStr=UploadUtil.uploadFile(file, "/upload/",RandomUtil.generateNumber(10));
                farmCropsInfo.setCover(coverStr);
            }
            if(files.length!=0){
                String carouselListStr="";
                for(MultipartFile f :files){
                    String path = UploadUtil.uploadFile(f, "/upload/",RandomUtil.generateNumber(12));
                    carouselListStr=carouselListStr+path+";";
                }
                carouselListStr=carouselListStr.substring(0, carouselListStr.length()-1);
                farmCropsInfo.setCarousel(carouselListStr);
                log.info("[FarmController:editFarmCropsInfo]{MultipartFile[]:" + carouselListStr );
            }
            farmManager.editCropsInfo(farmCropsInfo);
            map.put("success", true);
        }catch(Exception e){
            map.put("success", false);
            log.error("[FarmController:editFarmCropsInfo]false ", e);
        }
        return map;
    }

}
