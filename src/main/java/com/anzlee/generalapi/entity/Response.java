/**
 * 返回结果
 *
 * @author li.liangzhe
 * @create 2017-11-23 16:40
 **/
package com.anzlee.generalapi.entity;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "gen_response")
public class Response {
    public enum Type{
        /** 整型 */
        INT,
        /** 字符串 */
        STRING,
        /** 布尔型 */
        BOOLEAN,
        /** 浮点型 */
        FLOAT,
        /** JSON数组 */
        JSON
    }
    public enum EncryptType{
        SHA1,
        BASE64,
        MD5,
        MD5SHA1
    }
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    /**
     * 结果名
     */
    private String responseName;
    /**
     * 结果值
     */
    private String responseValue;
    /**
     * 结果
     */
    @Transient
    private Map<String,String> response;
    /**
     * 结果长度
     */
    private int responseSize;
    /**
     * 结果类型
     */
    private Param.Type responseType;
    /**
     * 结果是否加密
     */
    private boolean isResponseEncrypt;
    /**
     * 结果加密类型
     */
    private EncryptType responseEncryptType;
    /**
     * api
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="api_id")
    private API responseApi;

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
     * @return the responseName
     */
    public String getResponseName() {
        return responseName;
    }

    /**
     * @param $paramName the responseName to set
     */
    public void setResponseName(String responseName) {
        this.responseName = responseName;
    }

    /**
     * @return the responseValue
     */
    public String getResponseValue() {
        return responseValue;
    }

    /**
     * @param $paramName the responseValue to set
     */
    public void setResponseValue(String responseValue) {
        this.responseValue = responseValue;
    }

    /**
     * @return the response
     */
    public Map<String, String> getResponse() {
        return response;
    }

    /**
     * @param $paramName the response to set
     */
    public void setResponse(Map<String, String> response) {
        this.response = response;
    }

    /**
     * @return the responseSize
     */
    public int getResponseSize() {
        return responseSize;
    }

    /**
     * @param $paramName the responseSize to set
     */
    public void setResponseSize(int responseSize) {
        this.responseSize = responseSize;
    }

    /**
     * @return the responseType
     */
    public Param.Type getResponseType() {
        return responseType;
    }

    /**
     * @param $paramName the responseType to set
     */
    public void setResponseType(Param.Type responseType) {
        this.responseType = responseType;
    }

    /**
     * @return the isResponseEncrypt
     */
    public boolean isResponseEncrypt() {
        return isResponseEncrypt;
    }

    /**
     * @param $paramName the isResponseEncrypt to set
     */
    public void setResponseEncrypt(boolean responseEncrypt) {
        isResponseEncrypt = responseEncrypt;
    }

    /**
     * @return the responseEncryptType
     */
    public EncryptType getResponseEncryptType() {
        return responseEncryptType;
    }

    /**
     * @param $paramName the responseEncryptType to set
     */
    public void setResponseEncryptType(EncryptType responseEncryptType) {
        this.responseEncryptType = responseEncryptType;
    }

    /**
     * @return the responseApi
     */
    public API getResponseApi() {
        return responseApi;
    }

    /**
     * @param $paramName the responseApi to set
     */
    public void setResponseApi(API responseApi) {
        this.responseApi = responseApi;
    }
}
