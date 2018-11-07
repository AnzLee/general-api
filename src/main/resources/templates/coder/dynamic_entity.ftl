package com.anzlee.generalapi.third;

import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.util.List;
import com.anzlee.generalapi.base.entity.BaseEntity;

/**
* 描述：${tableName}实体层
* @author anzlee
* @date ${date}
*/
@Entity
@Table(name = "${tableName}")
@ApiModel(value="${apiName}",description="${apiDescription}")
public class _${tableName}Entity extends BaseEntity<Long> {

    private static final long serialVersionUID = -1L;

    <#list fieldList as field>
    /**
     *描述：字段${field.COLUMN_NAME}
     */
    <#if field.COLUMN_KEY == "PRI">
    @Id
    </#if>
    <#if field.EXTRA == "auto_increment">
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    @Column(<#if field.COLUMN_KEY == "UNI">unique=true,</#if><#if field.IS_NULLABLE == "NO">nullable=false<#elseif field.IS_NULLABLE == "YES">nullable=true</#if>)
    private <#switch field.DATA_TYPE>
        <#case "bigint">Long<#break>
        <#case "varchar">String<#break>
        <#case "int">int<#break>
        <#default>String<#break>
    </#switch> ${field.COLUMN_NAME};

    </#list>
    <#list fieldList as field>
    /**
    * @return the ${field.COLUMN_NAME}
    */
    public <#switch field.DATA_TYPE>
        <#case "bigint">Long<#break>
        <#case "varchar">String<#break>
        <#case "int">int<#break>
        <#default>String<#break>
    </#switch> get${field.COLUMN_NAME ? cap_first}() {
        return ${field.COLUMN_NAME};
    }

    /**
    * @param the ${field.COLUMN_NAME} to set
    */
    public void set${field.COLUMN_NAME ? cap_first}(<#switch field.DATA_TYPE>
        <#case "bigint">Long<#break>
        <#case "varchar">String<#break>
        <#case "int">int<#break>
        <#default>String<#break>
    </#switch> ${field.COLUMN_NAME}) {
        this.${field.COLUMN_NAME} = ${field.COLUMN_NAME};
    }

    </#list>

    public void validate(){
        System.out.println("_${tableName}Entity has been Loaded");
    }

}