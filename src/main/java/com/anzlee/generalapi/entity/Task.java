/**
 * 任务
 *
 * @author li.liangzhe
 * @create 2017-11-23 16:16
 **/
package com.anzlee.generalapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "gen_task")
@JsonIgnoreProperties(value={"taskApi"})
public class Task {
    public enum Type{
        /** 推送 */
        PUSH,
        /** 接收 */
        PULL
    }
    public enum Status{
        /** 未启动 */
        Stop,
        /** 已启动 */
        Started,
        /** 上次执行失败 */
        Failed,
        /** 上次执行成功 */
        Success
    }
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    /**
     * 任务名
     */
    @Column(unique=true, nullable=false)
    private String taskName;
    /**
     * 任务描述
     */
    private String taskDescription;
    /**
     * 类型
     */
    private Type taskType;
    /**
     * 执行时间
     */
    private String taskExTime;
    /**
     * 任务API
     */
    @OneToMany(targetEntity=API.class,mappedBy = "apiTask",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<API> taskApi = new ArrayList<API>();
    /**
     * 上次执行时间
     */
    private Date lastExTime;
    /**
     * 任务状态
     */
    private Status taskStatus = Status.Stop;

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
     * @return the taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param $paramName the taskName to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return the taskDescription
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * @param $paramName the taskDescription to set
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * @return the taskType
     */
    public Type getTaskType() {
        return taskType;
    }

    /**
     * @param $paramName the taskType to set
     */
    public void setTaskType(Type taskType) {
        this.taskType = taskType;
    }

    /**
     * @return the taskExTime
     */
    public String getTaskExTime() {
        return taskExTime;
    }

    /**
     * @param $paramName the taskExTime to set
     */
    public void setTaskExTime(String taskExTime) {
        this.taskExTime = taskExTime;
    }

    /**
     * @return the taskApi
     */
    public List<API> getTaskApi() {
        return taskApi;
    }

    /**
     * @param $paramName the taskApi to set
     */
    public void setTaskApi(List<API> taskApi) {
        this.taskApi = taskApi;
    }

    /**
     * @return the lastExTime
     */
    public Date getLastExTime() {
        return lastExTime;
    }

    /**
     * @param $paramName the lastExTime to set
     */
    public void setLastExTime(Date lastExTime) {
        this.lastExTime = lastExTime;
    }

    /**
     * @return the taskStatus
     */
    public Status getTaskStatus() {
        return taskStatus;
    }

    /**
     * @param $paramName the taskStatus to set
     */
    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }
}
