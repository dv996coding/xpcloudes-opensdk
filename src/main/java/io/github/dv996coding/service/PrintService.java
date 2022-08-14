package io.github.dv996coding.service;

import com.alibaba.fastjson.JSON;
import io.github.dv996coding.util.HttpClientUtil;
import io.github.dv996coding.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 电子面单云打印相关接口封装类
 *
 * @author 984199774@qq.com
 */
@Component
public class PrintService {
    private final static String BASE_URL = "https://open-barcode.xpyun.net";
    private String user;
    private String userKey;
    private Boolean debug;

    public PrintService(String user, String userKey, Boolean debug) {
        this.user = user;
        this.userKey = userKey;
        this.debug = debug;
    }

    public PrintService() {
    }

    /**
     * 1.验证打印机 /api/openapi/sprinter/verifyPrinter
     * 验证打印机编号的有效性，请严格参照请求格式说明。
     *
     * @param restRequest 请求参数
     * @return 返回布尔类型：true 表示验证成功, false 表示验证失败
     */
    public ObjectRestResponse<Boolean> verifyPrinter(PrinterVoRequest restRequest) {
        ObjectRestResponse<Boolean> restResponse = new ObjectRestResponse<>();
        if (StringUtils.isEmpty(restRequest.getSn().trim()) || restRequest.getSn().length() != 15) {
            restResponse.setCode(10000);
            restResponse.setMsg("The printer SN is invalid");
            restResponse.setData(false);
            return restResponse;
        }
        restRequest.setUser(this.user);
        restRequest.setUserKey(this.userKey);
        String url = String.format("%s/api/openapi/sprinter/verifyPrinter", BASE_URL);
        String param = restRequest.toString();
        if (this.debug) {
            System.out.println("Request Url: " + url);
            System.out.println("Request Param: " + param);
        }
        String json = HttpClientUtil.doPostJSON(url, param);
        return JSON.parseObject(json, ObjectRestResponse.class);
    }


    /**
     * 2.打印订单 /api/openapi/sprinter/print
     * 发送用户需要打印的订单内容给芯烨电子面单打印机
     *
     * @param restRequest 请求参数
     * @return 正确返回订单编号
     */
    public ObjectRestResponse<String> print(PrintOrderRequest restRequest) {
        ObjectRestResponse<String> restResponse = new ObjectRestResponse<>();
        if (StringUtils.isEmpty(restRequest.getSn().trim()) || restRequest.getSn().length() != 15) {
            restResponse.setCode(10000);
            restResponse.setMsg("The printer SN is invalid");
            return restResponse;
        }
        restRequest.setUser(this.user);
        restRequest.setUserKey(this.userKey);
        String url = String.format("%s/api/openapi/sprinter/print", BASE_URL);
        String param = restRequest.toString();
        if (this.debug) {
            System.out.println("Request Url: " + url);
            System.out.println("Request Param: " + param);
        }
        String json = HttpClientUtil.doPostJSON(url, param);
        return JSON.parseObject(json, ObjectRestResponse.class);
    }

    /**
     * 2.图片内容打印订单 /api/openapi/sprinter/print
     * 发送用户需要打印的订单内容给芯烨电子面单打印机
     *
     * @param restRequest 请求参数
     * @return 正确返回订单编号
     */
    public ObjectRestResponse<String> printImage(PrintOrderRequest restRequest) {
        ObjectRestResponse<String> restResponse = new ObjectRestResponse<>();
        if (StringUtils.isEmpty(restRequest.getSn().trim()) || restRequest.getSn().length() != 15) {
            restResponse.setCode(10000);
            restResponse.setMsg("The printer SN is invalid");
            return restResponse;
        }
        restRequest.setUser(this.user);
        restRequest.setUserKey(this.userKey);
        String url = String.format("%s/api/openapi/sprinter/printImage", BASE_URL);
        String param = restRequest.toString();

        if (this.debug) {
            System.out.println("Request Url: " + url);
            System.out.println("Request Param: " + param);
        }
        String json = HttpClientUtil.doPostJSON(url, param);
        return JSON.parseObject(json, ObjectRestResponse.class);
    }

    /**
     * 3.清空待打印队列 /api/openapi/sprinter/delPrinterQueue
     * 清空指定打印机的待打印任务队列
     *
     * @param restRequest 请求参数
     * @return 返回布尔类型：true 表示成功 false 表示失败
     */
    public ObjectRestResponse<Boolean> delPrinterQueue(PrinterVoRequest restRequest) {
        ObjectRestResponse<Boolean> restResponse = new ObjectRestResponse<>();
        if (StringUtils.isEmpty(restRequest.getSn().trim()) || restRequest.getSn().length() != 15) {
            restResponse.setCode(10000);
            restResponse.setMsg("The printer SN is invalid");
            return restResponse;
        }
        restRequest.setUser(this.user);
        restRequest.setUserKey(this.userKey);
        String url = String.format("%s/api/openapi/sprinter/delPrinterQueue", BASE_URL);
        String param = restRequest.toString();
        if (this.debug) {
            System.out.println("Request Url: " + url);
            System.out.println("Request Param: " + param);
        }
        String json = HttpClientUtil.doPostJSON(url, param);
        return JSON.parseObject(json, ObjectRestResponse.class);
    }

    /**
     * 4.查询订单是否打印成功 /api/openapi/sprinter/queryOrderState
     * 根据订单编号查询订单是否打印成功，订单编号由“打印订单”接口返回
     *
     * @param restRequest 请求参数
     * @return 已打印返回true, 未打印返回false
     */
    public ObjectRestResponse<Boolean> queryOrderState(PrintOrderStatusRequest restRequest) {
        ObjectRestResponse<Boolean> restResponse = new ObjectRestResponse<>();
        if (StringUtils.isEmpty(restRequest.getOrderId().trim())) {
            restResponse.setCode(10000);
            restResponse.setMsg("Invalid print order number");
            return restResponse;
        }
        restRequest.setUser(this.user);
        restRequest.setUserKey(this.userKey);
        String url = String.format("%s/api/openapi/sprinter/queryOrderState", BASE_URL);
        String param = restRequest.toString();
        if (this.debug) {
            System.out.println("Request Url: " + url);
            System.out.println("Request Param: " + param);
        }
        String json = HttpClientUtil.doPostJSON(url, param);
        return JSON.parseObject(json, ObjectRestResponse.class);
    }

    /**
     * 5.查询打印机状态 /api/openapi/sprinter/queryPrinterStatus
     * 查询指定打印机状态，返回该打印机在线或离线，正常或异常的状态信息。
     *
     * @param restRequest 请求参数
     * @return 返回打印机状态status和固件版本version
     * 状态status值共三种：
     * 0 表示离线
     * 1 表示在线正常
     * 2 表示在线异常
     * 备注：异常一般情况是缺纸，离线的判断是打印机与服务器失去联系超过 30 秒
     */
    public ObjectRestResponse<PrinterStatusResponse> queryPrinterStatus(PrinterVoRequest restRequest) {
        ObjectRestResponse<PrinterStatusResponse> restResponse = new ObjectRestResponse<>();
        if (StringUtils.isEmpty(restRequest.getSn().trim()) || restRequest.getSn().length() != 15) {
            restResponse.setCode(10000);
            restResponse.setMsg("The printer SN is invalid");
            return restResponse;
        }
        restRequest.setUser(this.user);
        restRequest.setUserKey(this.userKey);
        String url = String.format("%s/api/openapi/sprinter/queryPrinterStatus", BASE_URL);
        String param = restRequest.toString();
        if (this.debug) {
            System.out.println("Request Url: " + url);
            System.out.println("Request Param: " + param);
        }
        String json = HttpClientUtil.doPostJSON(url, param);
        return JSON.parseObject(json, ObjectRestResponse.class);
    }

    /**
     * 6.获取待打印任务数 /api/openapi/sprinter/getAwaitTasks
     * 获取指定打印机打印队列中待打印任务数。
     *
     * @param restRequest 请求参数
     * @return 返回指定打印机打印队列中待打印任务数
     */
    public ObjectRestResponse<Integer> getAwaitTasks(PrinterVoRequest restRequest) {
        ObjectRestResponse<Integer> restResponse = new ObjectRestResponse<>();
        if (StringUtils.isEmpty(restRequest.getSn().trim()) || restRequest.getSn().length() != 15) {
            restResponse.setCode(10000);
            restResponse.setMsg("The printer SN is invalid");
            restResponse.setData(-1);
            return restResponse;
        }
        restRequest.setUser(this.user);
        restRequest.setUserKey(this.userKey);
        String url = String.format("%s/api/openapi/sprinter/getAwaitTasks", BASE_URL);
        String param = restRequest.toString();
        if (this.debug) {
            System.out.println("Request Url: " + url);
            System.out.println("Request Param: " + param);
        }
        String json = HttpClientUtil.doPostJSON(url, param);
        return JSON.parseObject(json, ObjectRestResponse.class);
    }

    /**
     * 7.打印订单 /api/openapi/sprinter/print
     * 发送用户需要打印的订单内容给芯烨电子面单打印机
     *
     * @param restRequest 请求参数
     * @return 正确返回订单编号
     */
    public ObjectRestResponse<String> printLabel(PrintOrderRequest restRequest) {
        ObjectRestResponse<String> restResponse = new ObjectRestResponse<>();
        if (StringUtils.isEmpty(restRequest.getSn().trim()) || restRequest.getSn().length() != 15) {
            restResponse.setCode(10000);
            restResponse.setMsg("The printer SN is invalid");
            return restResponse;
        }
        restRequest.setUser(this.user);
        restRequest.setUserKey(this.userKey);
        String url = String.format("%s/api/openapi/sprinter/printLabel", BASE_URL);
        String param = restRequest.toString();
        if (this.debug) {
            System.out.println("Request Url: " + url);
            System.out.println("Request Param: " + param);
        }
        String json = HttpClientUtil.doPostJSON(url, param);
        return JSON.parseObject(json, ObjectRestResponse.class);
    }
}
