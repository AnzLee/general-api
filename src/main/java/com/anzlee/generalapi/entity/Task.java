/**
 * 任务
 *
 * @author li.liangzhe
 * @create 2017-11-23 16:16
 **/
package com.anzlee.generalapi.entity;

import javax.persistence.*;

@Entity
@Table(name = "gen_task")
public class Task {
    public enum Type{
        /** 前置任务 */
        BEFORE,
        /** 主任务 */
        CENTRE,
        /** 后置任务 */
        AFTER
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
    private String taskName;
    /**
     * 任务描述
     */
    private String taskDescription;
    /**
     * 执行时间
     */
    private String taskExTime;
    /**
     * 任务类型
     */
    private Type taskType = Type.CENTRE;
    /**
     * 任务API
     */
    @OneToOne(targetEntity = API.class)
    @JoinColumn(name = "api_id",referencedColumnName = "id")
    private API taskApi;
    /**
     * 关联任务
     */
    @OneToOne(targetEntity = Task.class)
    @JoinColumn(name = "task_id",referencedColumnName = "id")
    private Task linkTask;

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
     * @return the taskApi
     */
    public API getTaskApi() {
        return taskApi;
    }

    /**
     * @param $paramName the taskApi to set
     */
    public void setTaskApi(API taskApi) {
        this.taskApi = taskApi;
    }

    /**
     * @return the linkTask
     */
    public Task getLinkTask() {
        return linkTask;
    }

    /**
     * @param $paramName the linkTask to set
     */
    public void setLinkTask(Task linkTask) {
        this.linkTask = linkTask;
    }
}
