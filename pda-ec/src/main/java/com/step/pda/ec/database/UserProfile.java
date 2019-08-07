package com.step.pda.ec.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by user on 2019-08-02.
 */
@Entity(nameInDb = "user_profile")
public class UserProfile {
    @Id
    private Long userId;
    private String name =null;
    private String avatar=null;
    private String gender=null;
    @Generated(hash = 1553490858)
    public UserProfile(Long userId, String name, String avatar, String gender) {
        this.userId = userId;
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    


}
