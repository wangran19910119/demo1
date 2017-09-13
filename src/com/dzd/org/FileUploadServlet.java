package com.dzd.org;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Created by dzd-technology01 on 2017/9/8.
 */
@WebServlet("/FileUploadServlet")
public class FileUploadServlet extends javax.servlet.http.HttpServlet {
    private static final long serialVersionUID = 1L;
    private String message;

    @Override
    public void init() throws ServletException {
        message = "Hello world, this message is from servlet!";
    }
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
       /* request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        //1、创建一个DiskFileItemFactory工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //2、创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解决上传文件名的中文乱码
        upload.setHeaderEncoding("UTF-8");
        factory.setSizeThreshold(1024 * 1024 * 50);//设置内存的临界值为500K
        File linshi = new File("E:\\uploadFile");//当超过500K的时候，存到一个临时文件夹中
        factory.setRepository(linshi);
        upload.setSizeMax(1024 * 1024 * 500);//设置上传的文件总的大小不能超过500M
        String message = "";
        try {
            // 1. 得到 FileItem 的集合 items
            List<FileItem> *//* FileItem *//*items = upload.parseRequest(request);

            // 2. 遍历 items:
            for (FileItem item : items) {
                // 若是一个一般的表单域, 打印信息
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("utf-8");
                    System.out.println(name + ": " + value);
                }
                // 若是文件域则把文件保存到 e:\\files 目录下.
                else {
                    String fileName = item.getName();
                    long sizeInBytes = item.getSize();
                    System.out.println(fileName);
                    System.out.println(sizeInBytes);

                    InputStream in = item.getInputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;

                    Random random = new Random(System.currentTimeMillis());
                    int iRandom = random.nextInt(1000)+1;

                    fileName = "e:\\uploadFile\\" + fileName + iRandom;//文件最终上传的位置
                    System.out.println(fileName);
                    OutputStream out = new FileOutputStream(fileName);

                    while ((len = in.read(buffer)) != -1) {
                        out.write(buffer, 0, len);
                    }
                    out.close();
                    in.close();
                }
            }
            message = "上传成功！";
        } catch (FileUploadException e) {
               message = "上传失败！";
          //   request.getRequestDispatcher("error.jsp").forward(request, response);
            e.printStackTrace();
        }
        response.sendRedirect("iframe.jsp?message="+message);
       // request.getRequestDispatcher("success.jsp").forward(request, response);*/


        request.setCharacterEncoding("UTF-8");
        String res="";
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List items = upload.parseRequest(request);
            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    System.out.println("表单参数名:" + item.getFieldName() + "，表单参数值:" + item.getString("UTF-8"));
                } else {
                    if (item.getName() != null && !item.getName().equals("")) {
                        System.out.println("上传文件的大小:" + (item.getSize()/1024)+"KB");
                        System.out.println("上传文件的类型:" + item.getContentType());
                        // item.getName()返回上传文件在客户端的完整路径名称
                        System.out.println("上传文件的名称:" + item.getName().substring(item.getName().lastIndexOf("\\")+1));
                        String path="E://uploadFile//";
                        String fileName = item.getName();
                        //有的浏览器会返回文件名，而有的浏览器会返回“路径”+“文件名”，针对后者我们需要通过“字符串截取”获取文件名：
                        //"\"是转义符，所以也是特殊意义的字符，它自己也要转义。所以要获得字符串"\"，需要写成"\\"，所以需要使用lastIndexOf("\\")才能找出字符串中最后一个\ 。
                        int index=fileName.lastIndexOf("\\");
                        if(index!=-1) {
                            fileName=fileName.substring(index+1);
                        }
                        String uploadFileName = fileName.substring(0, fileName.lastIndexOf("."));// 去除后缀的文件名
                        String suffix = fileName.substring(fileName.lastIndexOf("."));// 后缀
                        Random random = new Random(System.currentTimeMillis());
                        int iRandom = random.nextInt(1000)+1;
                        File file = new File(path+"//"+uploadFileName+ iRandom + suffix);
                        item.write(file);
                        System.out.println("文件上传成功");
                        res="文件上传成功";
                    }else{
                        System.out.println("没有选择上传文件");
                        res="没有选择上传失败";
                    }
                }
            }
        }catch(FileUploadException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件上传失败");
            res="文件上传失败";
        }finally{
            response.setContentType("text/html;charset=utf-8");
            //可以使用字符流也可以使用字节流，写入浏览器

            //字符流的写法
//            PrintWriter out = response.getWriter();
//            out.write("<script>parent.callback('"+res+"')</script>"); //iframe中的html解析js,加parent是调用上级页面定义的    callback方法
//            out.flush();
//            out.close();
            //字节流的写法
            String msg="<script>parent.callback('"+res+"')</script>"; //iframe中的html解析js,加parent是调用上级页面定义的    callback方法
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(msg.getBytes("utf-8"));
            outputStream.flush();
            outputStream.close();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.write("<h1>" +message+"</h1>");
    }
    @Override
    public void destroy() {
        super.destroy();
    }

}
