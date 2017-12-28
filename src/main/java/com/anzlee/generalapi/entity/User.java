/**
 * 用户
 *
 * @author li.liangzhe
 * @create 2017-11-23 13:47
 **/
package com.anzlee.generalapi.entity;

import javax.persistence.*;

@Entity
@Table(name = "gen_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String password;

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param $paramName the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param $paramName the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param $paramName the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}