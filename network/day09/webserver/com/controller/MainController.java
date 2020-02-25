package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {
   
   String id=null;
   String txt=null;
   

   @RequestMapping("data.mc")
   public void data( HttpServletRequest request, HttpServletResponse response){
      System.out.println("request&response");
      
         try {
            PrintWriter out =response.getWriter();
             String reid = request.getParameter("id");
             String retxt = request.getParameter("txt");
            
//             if(reid.equals(id) && retxt.equals(txt)) {
//                
//             }
             
             if(id == null || txt == null) {
                id= reid;
                txt=retxt;
             }
             else if(id.equals(reid)) {
                txt=retxt;
             }
         
         JSONArray ja = new JSONArray();
         JSONObject jo = new JSONObject();
         jo.put("id", id);
         jo.put("txt",txt);
         
         ja.add(jo);
         out.print(ja.toJSONString());
         
         
         } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         
   }
   @RequestMapping("main.mc")
   public ModelAndView main(ModelAndView mv) {
      
      mv.setViewName("main");
      return mv;
      
   }
}
