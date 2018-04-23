package com.kneel.core.controller.gen;
 

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kneel.core.gen.config.support.DefaultBuildFactory;
import com.kneel.core.utils.StringUtils;

@Controller
public class BuildController {
	
	private static final Log logger = LogFactory.getLog(BuildController.class);
	
	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://usr//resources//csv//";
	
	@Autowired
	private DefaultBuildFactory defaultBuildFactory;
	
	@RequestMapping(value = "/build/index", method = RequestMethod.GET)
	public String index(Model model) {  
		return "gen/build";
	}

	@ResponseBody
	@RequestMapping(value = "/build/listcsv", method = RequestMethod.GET)
	public String listCsv(@RequestParam(required = true) String project, @RequestParam(required = true) String res,Model model) {  
		File file = new File(project+"/"+res+"/"+DefaultBuildFactory.CSV); 
		String[] files =file.list();
		StringBuffer sb = new StringBuffer("[");
		if(files != null){
			for(String filestr:files){
				if(filestr.contains(".")){
					sb.append("{\"txt\":\""+filestr+"\"},");
				} 
			} 
		} 
	    sb.deleteCharAt(sb.length()-1);
	    sb.append("]");
		return sb.toString();
	}
	 
	@ResponseBody
	@RequestMapping(value = "/build/genSql")
	public String genSql(@RequestParam(required = true) String project, @RequestParam(required = true) String res, @RequestParam(required = true) String csv){
		String filecontent = null; 
		try{ 
			defaultBuildFactory.buildCSVSql(project, res, csv);
			String tableName = csv.substring(0, csv.lastIndexOf(DefaultBuildFactory.DOT));
			String fileName = tableName.concat(DefaultBuildFactory.DOT).concat(DefaultBuildFactory.CSV_EXTEND);
			filecontent = StringUtils.getFromInputStream(new FileInputStream(project+"/"+res+"/"+DefaultBuildFactory.CSV+"/"+DefaultBuildFactory.CSV_EXTEND+"/"+fileName));
		}catch(Exception e){
			logger.error("generator sql failed:", e);
		} 
		return filecontent;
	}
	
	@RequestMapping(value="/build/uploadTemplate")
	public String updateTempalte(@RequestParam("csfile") MultipartFile file,Model model){
		if (file.isEmpty()) {
			model.addAttribute("error", "Please select a file to upload");
            return "gen/build";
        } 
	    try { 
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes(); 
            String originalFileName = file.getOriginalFilename();
            if(originalFileName.contains("\\")){
            	originalFileName = originalFileName.substring(originalFileName.lastIndexOf("\\")+1);
            }
            Path path = Paths.get(UPLOADED_FOLDER + originalFileName);
            Files.write(path, bytes);

            model.addAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
        	logger.error("upload file failed:", e);
        }
		return "gen/build";
	}
}
