package org.ledgerty.dao;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Héctor on 05/06/2017.
 */
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", nullable = false, unique = true)
    private Long id;

    @NaturalId
    @Column(name = "Guid", nullable = false, unique = true, updatable = false, length = 50)
    private String guid;

    @Column(name = "DateAdded", nullable = false, updatable = false)
    private Date dateAdded;

    @Column(name = "CreationIP", nullable = true, updatable = false, length = 20)
    private String creationIP;

    @Column(name = "Active", nullable = false)
    private Boolean active = true;

    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 100)
    private String lastName;

    @Column(name = "Email", nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "PhoneNumber", nullable = true, length = 20)
    private String phoneNumber;

    @Column(name = "StoragePath", nullable = false, updatable = false, length = 255)
    private String storagePath;

    @Column(name = "StorageMegaBytesUsed", nullable = false)
    private Float storageMegaBytesUsed = 0F;

    @Column(name = "Experience", nullable = false)
    private Integer experience = 0;

    @Column(name = "Coins", nullable = false)
    private Integer coins = 0;

    public User() {
    }

    public User(String guid) {
        this.guid = guid;
    }

    public User(String guid, Date dateAdded, String creationIP, Boolean active, String firstName, String lastName, String email, String password, String phoneNumber, String storagePath, Float storageMegaBytesUsed, Integer experience, Integer coins) {
        this.guid = guid;
        this.dateAdded = dateAdded;
        this.creationIP = creationIP;
        this.active = active;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.storagePath = storagePath;
        this.storageMegaBytesUsed = storageMegaBytesUsed;
        this.experience = experience;
        this.coins = coins;
    }

    public Long getId() {
        return id;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getCreationIP() {
        return creationIP;
    }

    public void setCreationIP(String creationIP) {
        this.creationIP = creationIP;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public Float getStorageMegaBytesUsed() {
        return storageMegaBytesUsed;
    }

    public void setStorageMegaBytesUsed(Float storageMegaBytesUsed) {
        this.storageMegaBytesUsed = storageMegaBytesUsed;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public String getGuid() {
        return guid;
    }
}
