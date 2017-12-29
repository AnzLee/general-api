/**
 * 通用API接口
 *
 * @author li.liangzhe
 * @create 2017-11-23 15:47
 **/
package com.anzlee.generalapi.entity;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "gen_api")
@ApiModel(value="API对象",description="接口对象api")
public class API {
    public enum Method{
        get,
        post,
        put,
        delete
    }
    public enum Format{
        json,
        xml,
        file,
        def
    }
    public enum Protocol{
        http,
        https
    }
    public enum Encode{
        UTF8,
        GBK,
        GB2312,
        UNICODE,
        GB18030,
        ASCII
    }
    public enum Type{
        /** 推送 */
        PUSH,
        /** 接收 */
        PULL
    }
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    /**
     * 名字
     */
    private String apiName;
    /**
     * 协议
     */
    private Protocol apiProtocol = Protocol.http;
    /**
     * 地址
     */
    private String apiUrl;
    /**
     * API类型
     */
    private Type apiType;
    /**
     * API请求方法
     */
    private Method apiMethod = Method.get;
    /**
     * 数据库
     */
    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER,optional=false)
    @JoinColumn(name="database_id")
    private Database apiDatabase;
    /**
     * 内容
     */
    private String apiContent;
    /**
     * 动态嵌入代码
     */
    private String apiCode;
    /**
     * 格式
     */
    private Format apiFormat = Format.json;
    /**
     * 编码
     */
    private Encode apiEncode = Encode.UTF8;
    /**
     * 参数
     */
    @OneToMany(targetEntity=Param.class,mappedBy="paramApi",fetch=FetchType.LAZY)
    private List<Param> apiParams;
    /**
     * 返回结果
     */
    @OneToMany(targetEntity=Response.class,mappedBy="responseApi",fetch=FetchType.LAZY)
    private List<Response> apiResponses;

    /**
     * @return the ID
     */
    public Long getID() {
        return ID;
    }

    /**
     * @param $paramName the ID to set
     */
    public void setID(Long ID) {
        this.ID = ID;
    }

    /**
     * @return the apiName
     */
    public String getApiName() {
        return apiName;
    }

    /**
     * @param $paramName the apiName to set
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    /**
     * @return the apiProtocol
     */
    public Protocol getApiProtocol() {
        return apiProtocol;
    }

    /**
     * @param $paramName the apiProtocol to set
     */
    public void setApiProtocol(Protocol apiProtocol) {
        this.apiProtocol = apiProtocol;
    }

    /**
     * @return the apiUrl
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * @param $paramName the apiUrl to set
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * @return the apiType
     */
    public Type getApiType() {
        return apiType;
    }

    /**
     * @param $paramName the apiType to set
     */
    public void setApiType(Type apiType) {
        this.apiType = apiType;
    }

    /**
     * @return the apiMethod
     */
    public Method getApiMethod() {
        return apiMethod;
    }

    /**
     * @param $paramName the apiMethod to set
     */
    public void setApiMethod(Method apiMethod) {
        this.apiMethod = apiMethod;
    }

    /**
     * @return the apiDatabase
     */
    public Database getApiDatabase() {
        return apiDatabase;
    }

    /**
     * @param $paramName the apiDatabase to set
     */
    public void setApiDatabase(Database apiDatabase) {
        this.apiDatabase = apiDatabase;
    }

    /**
     * @return the apiContent
     */
    public String getApiContent() {
        return apiContent;
    }

    /**
     * @param $paramName the apiContent to set
     */
    public void setApiContent(String apiContent) {
        this.apiContent = apiContent;
    }

    /**
     * @return the apiCode
     */
    public String getApiCode() {
        return apiCode;
    }

    /**
     * @param $paramName the apiCode to set
     */
    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    /**
     * @return the apiFormat
     */
    public Format getApiFormat() {
        return apiFormat;
    }

    /**
     * @param $paramName the apiFormat to set
     */
    public void setApiFormat(Format apiFormat) {
        this.apiFormat = apiFormat;
    }

    /**
     * @return the apiEncode
     */
    public Encode getApiEncode() {
        return apiEncode;
    }

    /**
     * @param $paramName the apiEncode to set
     */
    public void setApiEncode(Encode apiEncode) {
        this.apiEncode = apiEncode;
    }

    /**
     * @return the apiParams
     */
    public List<Param> getApiParams() {
        return apiParams;
    }

    /**
     * @param $paramName the apiParams to set
     */
    public void setApiParams(List<Param> apiParams) {
        this.apiParams = apiParams;
    }

    /**
     * @return the apiResponses
     */
    public List<Response> getApiResponses() {
        return apiResponses;
    }

    /**
     * @param $paramName the apiResponses to set
     */
    public void setApiResponses(List<Response> apiResponses) {
        this.apiResponses = apiResponses;
    }
}
