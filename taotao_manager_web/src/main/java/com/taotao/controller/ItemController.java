package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ItemController {
    /**
     * 显示页面
     * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }

    @Autowired
    private ItemService itemService;

    /**
     * 商品类目选择
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;//http://192.168.25.133/

    /**
     * 上传图片
     * @param uploadFile
     * @return
     */
    @RequestMapping(value="/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        try {
            //1.获取文件扩展名
            String originalFilename = uploadFile.getOriginalFilename();//获得原始文件名
            String extName = originalFilename.substring(originalFilename.lastIndexOf("." )+1);//sub包左不包右,所以+1,截取文件后缀名
            //2.创建一个fastDFS的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastdfs_client.conf");
            //3.执行上传处理
            String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //4.拼接返回的url和IP地址,拼接完整的url
            String url = IMAGE_SERVER_URL + path;
            //5.返回map
            Map result = new HashMap<>();
            result.put("error" ,0);
            result.put("url",url);
            String s = JsonUtils.objectToJson(result);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            //5.返回map
            Map result = new HashMap<>();
            result.put("error" ,1);
            result.put("message" ,"图片上传失败");
            String s = JsonUtils.objectToJson(result);
            return s;
        }
    }

    @RequestMapping("/item/save")
    @ResponseBody
    public TaotaoResult saveItem(TbItem item,String desc){
        return itemService.saveItem(item,desc);
    }



}
