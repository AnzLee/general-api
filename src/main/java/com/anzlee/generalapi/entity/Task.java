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
import java.util.List;

@Entity
@Table(name = "gen_task")
@JsonIgnoreProperties(value={"taskApi"})
public class Task {
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
     * 执行时间
     */
    private String taskExTime;
    /**
     * 任务API
     */
    @OneToMany(targetEntity=API.class,mappedBy = "apiTask",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<API> taskApi = new ArrayList<API>();

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
}
