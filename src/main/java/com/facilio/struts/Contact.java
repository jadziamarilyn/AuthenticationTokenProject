package com.facilio.struts;

public class Contact {
    private Integer contact_id;
    private String name;
    private String age;
    private String mobileno;
    private String mailid;

    public Integer getContact_id() {
        return contact_id;
    }

    public void setContact_id(Integer personid) {
        this.contact_id = contact_id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getMailid() {
        return mailid;
    }

    public void setMailid(String mail) {
        this.mailid = mailid;
    }
    public Contact(Integer contact_id,String name, String age, String mobileno, String mailid){
        this.contact_id=contact_id;
        this.name = name;
        this.age = age;
        this.mobileno = mobileno;
        this.mailid = mailid;
    }
}
