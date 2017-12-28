/**
 * API参数
 *
 * @author li.liangzhe
 * @create 2017-11-23 15:56
 **/
package com.anzlee.generalapi.entity;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "gen_param")
public class Param {
    public enum Type{
        INT,
        STRING,
        BOOLEAN,
        FLOAT,
        TIME
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
     * 参数名
     */
    private String paramName;
    /**
     * 参数值
     */
    private String paramValue;
    /**
     * 参数
     */
    @Transient
    private Map<String,String> param;
    /**
     * 参数长度
     */
    private int paramSize;
    /**
     * 参数类型
     */
    private Type paramType;
    /**
     * 参数是否加密
     */
    private boolean isParamEncrypt;
    /**
     * 加密类型
     */
    private EncryptType paramEncryptType;
    /**
     * api
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name="api_id")
    private API paramApi;

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
     * @return the paramName
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * @param $paramName the paramName to set
     */
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * @return the paramValue
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * @param $paramName the paramValue to set
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    /**
     * @return the param
     */
    public Map<String, String> getParam() {
        return param;
    }

    /**
     * @param $paramName the param to set
     */
    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    /**
     * @return the paramSize
     */
    public int getParamSize() {
        return paramSize;
    }

    /**
     * @param $paramName the paramSize to set
     */
    public void setParamSize(int paramSize) {
        this.paramSize = paramSize;
    }

    /**
     * @return the paramType
     */
    public Type getParamType() {
        return paramType;
    }

    /**
     * @param $paramName the paramType to set
     */
    public void setParamType(Type paramType) {
        this.paramType = paramType;
    }

    /**
     * @return the isParamEncrypt
     */
    public boolean isParamEncrypt() {
        return isParamEncrypt;
    }

    /**
     * @param $paramName the isParamEncrypt to set
     */
    public void setParamEncrypt(boolean paramEncrypt) {
        isParamEncrypt = paramEncrypt;
    }

    /**
     * @return the paramEncryptType
     */
    public EncryptType getParamEncryptType() {
        return paramEncryptType;
    }

    /**
     * @param $paramName the paramEncryptType to set
     */
    public void setParamEncryptType(EncryptType paramEncryptType) {
        this.paramEncryptType = paramEncryptType;
    }

    /**
     * @return the paramApi
     */
    public API getParamApi() {
        return paramApi;
    }

    /**
     * @param $paramName the paramApi to set
     */
    public void setParamApi(API paramApi) {
        this.paramApi = paramApi;
    }
}
