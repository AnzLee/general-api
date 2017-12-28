/**
 * API参数
 *
 * @author li.liangzhe
 * @create 2017-11-23 15:56
 **/
package com.anzlee.generalapi.entity;

import javax.persistence.*;

@Entity
@Table(name = "gen_database")
public class Database {
    public enum Type{
        /** 甲骨文 */
        ORACLE,
        /** Apache */
        MYSQL,
        /** 微软 */
        SQLSERVER,
        /** Elasticsearch */
        ES,
        /** Redis */
        REDIS,
        /** DB2 */
        DB2,
        /** SyBase */
        SYBASE,
        /** PostgreSQL */
        POSTGRESQL,
        /** 达梦 */
        DM,
        /** 南大通用 */
        GBASE,
        /** 神通数据库*/
        ST,
        /** 人大金仓*/
        JC,
        /** 易鲸 */
        ESGYNDB,
        /** 巨杉数据库 */
        SEQUOIADB,
        /** 阿里巴巴 */
        OCEANBASE,
        /** 浪潮 */
        K_DB,
        /** 东软 */
        OPENBASE,
        /** 华易 */
        HUAYI
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
     * 数据库名
     */
    private String dataName;
    /**
     * 数据库地址
     */
    private String dataPath;
    /**
     * 用户
     */
    private String dataUsername;
    /**
     * 密码
     */
    private String dataPassword;
    /**
     * 数据库类型
     */
    private Type dataType;
    /**
     * api
     */
    @OneToOne(mappedBy="apiDatabase")
    private API databaseApi;

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
     * @return the dataName
     */
    public String getDataName() {
        return dataName;
    }

    /**
     * @param $paramName the dataName to set
     */
    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    /**
     * @return the dataPath
     */
    public String getDataPath() {
        return dataPath;
    }

    /**
     * @param $paramName the dataPath to set
     */
    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    /**
     * @return the dataUsername
     */
    public String getDataUsername() {
        return dataUsername;
    }

    /**
     * @param $paramName the dataUsername to set
     */
    public void setDataUsername(String dataUsername) {
        this.dataUsername = dataUsername;
    }

    /**
     * @return the dataPassword
     */
    public String getDataPassword() {
        return dataPassword;
    }

    /**
     * @param $paramName the dataPassword to set
     */
    public void setDataPassword(String dataPassword) {
        this.dataPassword = dataPassword;
    }

    /**
     * @return the dataType
     */
    public Type getDataType() {
        return dataType;
    }

    /**
     * @param $paramName the dataType to set
     */
    public void setDataType(Type dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the databaseApi
     */
    public API getDatabaseApi() {
        return databaseApi;
    }

    /**
     * @param $paramName the databaseApi to set
     */
    public void setDatabaseApi(API databaseApi) {
        this.databaseApi = databaseApi;
    }
}
