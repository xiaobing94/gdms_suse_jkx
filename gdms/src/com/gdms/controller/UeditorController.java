package com.gdms.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baidu.ueditor.ActionEnter;



@Controller
@RequestMapping(value = "/ueditor")
public class UeditorController {

        @RequestMapping("/fileupload")
        public void config(HttpServletRequest request,  HttpServletResponse response, String action) throws Exception {
                System.out.println(request.getInputStream().available());
        		System.out.println("�ļ��ϴ��У�");
        		response.setContentType("application/json");               
                String rootPath = request.getSession().getServletContext().getRealPath("/");
                try {
                        String exec = new ActionEnter(request, rootPath).exec();
                        PrintWriter writer = response.getWriter();
                        writer.write(exec);
                        writer.flush();
                        writer.close();
                        System.out.println("ִ�й�����");
                } catch (IOException e) {
                        e.printStackTrace();
                } 
        }

}
