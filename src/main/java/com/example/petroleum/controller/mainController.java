package com.example.petroleum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;

@RestController
public class mainController {

    @GetMapping("/test")
    public Map<String,Object> hello(){
        Map<String, Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","success");
        map.put("value","hello world !");
        return map;
    }



    @GetMapping("/test1")
    public Map<String,Object> test1(String[] args) {
        double Weiin[] = {0.11,0.1,0.08,0.07,0.06,0.04,0.03,0.02,0.01,0.01,0.01,0.02,0.04,0.07,0.11,0.15,0.19,0.24,0.29,0.35,0.4,0.45,0.51,0.56,0.61,0.67,0.72,0.78,0.83,0.89,0.95,1,1.06,1.11,1.17,1.22,1.28,1.33,1.39,1.44,1.5,1.56,1.62,1.68,1.74,1.8,1.86,1.91,1.96,2.02,2.06,2.11,2.16,2.2,2.25,2.29,2.34,2.38,2.42,2.46,2.51,2.54,2.58,2.61,2.64,2.67,2.69,2.72,2.74,2.77,2.8,2.82,2.85,2.88,2.91,2.93,2.95,2.97,2.98,2.99,2.99,2.99,2.98,2.96,2.95,2.92,2.9,2.87,2.84,2.81,2.78,2.74,2.71,2.67,2.63,2.58,2.53,2.48,2.42,2.37,2.31,2.24,2.18,2.11,2.05,1.99,1.93,1.87,1.82,1.77,1.72,1.68,1.63,1.58,1.54,1.48,1.43,1.37,1.31,1.24,1.17,1.1,1.03,0.96,0.9,0.84,0.78,0.73,0.68,0.64,0.59,0.55,0.5,0.46,0.41,0.37,0.33,0.29,0.25,0.22,0.19,0.16,0.14,0.13};
        double Loadin[] = {-8.68,-7.61,-6.3,-4.71,-2.84,-0.71,1.59,3.98,6.34,8.54,10.49,12.08,13.26,14.03,14.4,14.44,14.23,13.88,13.48,13.12,12.87,12.75,12.78,12.92,13.14,13.39,13.61,13.75,13.78,13.7,13.52,13.25,12.95,12.64,12.37,12.17,12.06,12.03,12.07,12.17,12.29,12.42,12.53,12.6,12.62,12.61,12.57,12.5,12.43,12.35,12.28,12.2,12.12,12.03,11.91,11.78,11.61,11.44,11.26,11.09,10.96,10.86,10.8,10.78,10.78,10.77,10.72,10.59,10.34,9.95,9.4,8.67,7.8,6.81,5.75,4.66,3.6,2.64,1.8,1.14,0.65,0.35,0.22,0.22,0.32,0.47,0.64,0.77,0.84,0.83,0.73,0.54,0.29,0,-0.3,-0.57,-0.79,-0.94,-1.01,-1.01,-0.96,-0.91,-0.9,-1,-1.28,-1.79,-2.57,-3.65,-5.04,-6.68,-8.53,-10.5,-12.49,-14.39,-16.09,-17.5,-18.56,-19.24,-19.54,-19.51,-19.21,-18.73,-18.18,-17.63,-17.18,-16.87,-16.71,-16.71,-16.8,-16.95,-17.07,-17.11,-17,-16.73,-16.28,-15.67,-14.95,-14.15,-13.34,-12.55,-11.8,-11.08,-10.38,-9.62};
        //计算位移最大最小值
        Double newWeiin[] = new Double[Weiin.length];
        for(int i=0;i<Weiin.length;i++) {
            newWeiin[i] = (Double)Weiin[i];
        }
        double WeiMax = Collections.max(Arrays.asList(newWeiin));
        double WeiMin = Collections.min(Arrays.asList(newWeiin));
        //计算载荷最大最小值
        Double newLoadin[] = new Double[Loadin.length];
        for(int i=0;i<Loadin.length;i++) {
            newLoadin[i] = (Double)Loadin[i];
        }
        double LoadMax = Collections.max(Arrays.asList(newLoadin));
        double LoadMin = Collections.min(Arrays.asList(newLoadin));
        //计算归一化周长面积
        double mianji=Cal_MianJi(Weiin,Loadin,WeiMax,WeiMin, LoadMax, LoadMin,Weiin.length);
        double zhouchang=Cal_ZhouChang(Weiin,Loadin,WeiMax,WeiMin, LoadMax, LoadMin,Weiin.length);
//        System.out.println("面积= "+mianji);
//        System.out.println("周长= "+zhouchang);
        Map<String, Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","success");
        map.put("mianji",mianji);
        map.put("zhouchang",zhouchang);
        return map;

    }
    /*
       定义方法,Cal_ZhouChang计算示功图归一化周长
       返回值: double
       方法参数:
    */
    public static double Cal_MianJi(double[] Weiin,double[] Loadin,double WeiMax,double WeiMin,double LoadMax,double LoadMin,int num_point){

        //找到上下冲程分界点
        int UpEnd=0;
        int DownStart=0;
        for(int i=0;i<num_point;i++){
            if (Weiin[i]==WeiMax){
                UpEnd=i;
                DownStart=i;
                break;
            }
        }
        //上冲程面积
        double UpSequare=0;
        int UpStart=0;
        for(int i=UpStart;i<UpEnd; i++){
            UpSequare=UpSequare+(Loadin[i+1]+Loadin[i])*(Weiin[i+1]-Weiin[i])/2;
        }
        //下冲程面积
        double DownSequare=0;
        for(int i=DownStart;i<num_point-1;i++){
            DownSequare=DownSequare+(Loadin[i+1]+Loadin[i])*(Weiin[i]-Weiin[i+1])/2;
        }
        //功图面积
        double MianJi=UpSequare-DownSequare;
        //功图面积归一化处理
        MianJi=MianJi/((LoadMax-LoadMin)*(WeiMax-WeiMin));
        return MianJi;
    }

    /*
       定义方法,Cal_ZhouChang计算示功图归一化周长
       返回值: double
       方法参数:
    */
    public static double Cal_ZhouChang(double[] Weiin,double[] Loadin,double WeiMax,double WeiMin,double LoadMax,double LoadMin,int num_point){

        //计算周长
        double dbZhouC=0;
        for(int i=0;i<num_point-1;i++){
            dbZhouC=dbZhouC+Math.sqrt(Math.pow(Weiin[i+1]-Weiin[i], 2)+Math.pow(Loadin[i+1]-Loadin[i], 2));
        }
        dbZhouC=dbZhouC/((WeiMax-WeiMin)*2+(LoadMax-LoadMin)*2);
        return dbZhouC;
    }


}


