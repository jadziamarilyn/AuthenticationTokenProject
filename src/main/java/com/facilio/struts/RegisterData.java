package com.facilio.struts;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import static com.facilio.struts.InterceptorClass.res;
import static com.facilio.struts.InterceptorClass.userid;
import static com.facilio.struts.InterceptorClass.value;
public class RegisterData extends ActionSupport {
    private String name;
    private String pass;
    private String status;
    private String age;
    private String mobileno;
    private String mailid;
    public String url;
    public String uname;
    public String password;

    public int getContact_id() {
        return contact_id;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    private int contact_id;
    private List<Contact> lst = new ArrayList<>();

    public List<Contact> getLst() {
        return lst;
    }

    public void setLst(List<Contact> lst) {
        this.lst = lst;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

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

    public void setMailid(String mailid) {
        this.mailid = mailid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


public String registration() throws Exception {
    properties();
    Connection con = DriverManager.getConnection(url, uname, password);
    String query = "INSERT INTO user(name,pass) VALUES(?,?)";
    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, name);
    st.setString(2, pass);
    st.executeUpdate();
    st.close();
    con.close();
    setStatus("Registered Successfully");
    return SUCCESS;
}
public String login() throws Exception {
    boolean res = validateUser();
    if (res){
        int userid;
        properties();
        Connection con = DriverManager.getConnection(url, uname, password);
        String query = "SELECT userid FROM user WHERE name=? and pass=?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, name);
        st.setString(2, pass);
        ResultSet rs = st.executeQuery();
        rs.next();
        userid = rs.getInt(1);
        st.close();
        token = usingRandomUUID();
        query = "INSERT INTO token_id(userid,token) VALUES(?,?)";
        st = con.prepareStatement(query);
        st.setInt(1,userid);
        st.setString(2,token);
        st.executeUpdate();
        st.close();
        con.close();
        setStatus("Login Successfull");
        return SUCCESS;
    }
    setStatus("invalid username password");
    return ERROR;
}
public boolean validateUser() throws Exception {
    boolean status=false;
    properties();
    Connection con = DriverManager.getConnection(url, uname, password);
    String query = "SELECT * FROM user WHERE name=? and pass=?";
    PreparedStatement st = con.prepareStatement(query);
    st.setString(1, name);
    st.setString(2, pass);
    ResultSet rs = st.executeQuery();
    status= rs.next();
    st.close();
    con.close();
    return status;
}
public void properties() throws Exception{
    Properties p = new Properties();
    InputStream is = new FileInputStream("C:\\Users\\Hp\\IdeaProjects\\AuthenticationTokenProject\\db.properties");
    p.load(is);
    url = p.getProperty("url");
    uname = p.getProperty("uname");
    password = p.getProperty("password");
    Class.forName("com.mysql.cj.jdbc.Driver");
}
static String usingRandomUUID() {

    UUID randomUUID = UUID.randomUUID();
    return randomUUID.toString().replaceAll("_", "");

    }
    public String CreateContact() throws Exception {
//        String key;
//        HttpServletRequest request = ServletActionContext.getRequest();
//        Enumeration headerName = request.getHeaderNames();
//        key= (String) headerName.nextElement();
//        value = request.getHeader(key);
//        boolean res = validatetoken();
        properties();
        if(res){
            String query = "INSERT INTO user_contact(userid,name,age,mobileno,mailid,token) VALUES(?,?,?,?,?,?)";
            Connection con = DriverManager.getConnection(url, uname, password);
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1,userid);
            st.setString(2, name);
            st.setString(3, age);
            st.setString(4, mobileno);
            st.setString(5, mailid);
            st.setString(6,value);
            st.executeUpdate();
            st.close();
            con.close();
            setStatus("Created Successfully");
            return SUCCESS;
        }
        setStatus("Token not found");
        return ERROR;
    }
//    public boolean validatetoken() throws Exception {
//        boolean status= false;
//        properties();
//        Connection con = DriverManager.getConnection(url, uname, password);
//        String query = "SELECT * FROM token_id WHERE token=?";
//        PreparedStatement st = con.prepareStatement(query);
//        st.setString(1, value);
//        ResultSet rs = st.executeQuery();
//        status= rs.next();
//        if (status){
//            userid = rs.getInt(1);
//        }
//        st.close();
//        con.close();
//        return status;
//    }
    public String UpdateContact() throws Exception{
//        String key;
//        HttpServletRequest request = ServletActionContext.getRequest();
//        Enumeration headerName = request.getHeaderNames();
//        key= (String) headerName.nextElement();
//        value = request.getHeader(key);
//        boolean res = validatetoken();
        properties();
        if(res){
            Connection con = DriverManager.getConnection(url, uname, password);
            if  (!name.equals("")){
                String query="UPDATE user_contact SET name=? WHERE contact_id=? and userid=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1,name);
                st.setInt(2, contact_id);
                st.setInt(3,userid );
                st.executeUpdate();
                st.close();
            }
            if  (!age.equals("")){
                String query="UPDATE user_contact SET age=? WHERE contact_id=? and userid=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1,age);
                st.setInt(2, contact_id);
                st.setInt(3,userid );
                st.executeUpdate();
                st.close();
            }
            if  (!mobileno.equals("")){
                String query="UPDATE user_contact SET mobileno=? WHERE contact_id=? and userid=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1,mobileno);
                st.setInt(2, contact_id);
                st.setInt(3,userid );
                st.executeUpdate();
                st.close();
            }
            if  (!mailid.equals("")){
                String query="UPDATE user_contact SET mailid=? WHERE contact_id=? and userid=?";
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1,mailid);
                st.setInt(2, contact_id);
                st.setInt(3,userid );
                st.executeUpdate();
                st.close();
            }
            con.close();
            setStatus("Updated Successfully");
            return"updated";
        }
        setStatus("Token not found");
        return ERROR;

    }
    public String DeleteContact() throws Exception{
//        String key;
//        HttpServletRequest request = ServletActionContext.getRequest();
//        Enumeration headerName = request.getHeaderNames();
//        key= (String) headerName.nextElement();
//        value = request.getHeader(key);
//        boolean res = validatetoken();
        properties();
        if(res){
            String query ="DELETE FROM user_contact WHERE contact_id=? and userid=?";
            Connection con = DriverManager.getConnection(url, uname, password);
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1,contact_id);
            st.setInt(2,userid);
            st.executeUpdate();
            st.close();
            con.close();
            setStatus("Deleted Successfully");
            return "delete";
        }
        setStatus("Token not found");
        return ERROR;
    }
    public String ListContact() throws Exception {
//        String key;
//        HttpServletRequest request = ServletActionContext.getRequest();
//        Enumeration headerName = request.getHeaderNames();
//        key= (String) headerName.nextElement();
//        value = request.getHeader(key);
//        boolean res = validatetoken();
        properties();
        if(res){
            String query = "SELECT * FROM user_contact WHERE userid=?";
            Connection con = DriverManager.getConnection(url, uname, password);
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1,userid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Integer contact_id = rs.getInt(2);
                String name = rs.getString(3);
                String age = rs.getString(4);
                String mobileno = rs.getString(5);
                String mailid = rs.getString(6);
                Contact c = new Contact(contact_id,name,age,mobileno,mailid);
                lst.add(c);
            }
        return "done";
        }
        setStatus("Token not found");
        return ERROR;
    }
}




//"name":"Sebastin","pass":"0987","mobileno":"123345678","mailid":"jjadzi@123"