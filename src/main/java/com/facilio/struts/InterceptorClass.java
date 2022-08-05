package com.facilio.struts;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Properties;

public class InterceptorClass extends AbstractInterceptor {
    public String url;
    public String uname;
    public String password;
    static  boolean res;
    static int userid;
    static String value;
    public String intercept(ActionInvocation invocation) throws Exception {
        String key;
        HttpServletRequest request = ServletActionContext.getRequest();
        Enumeration headerName = request.getHeaderNames();
        key= (String) headerName.nextElement();
        value = request.getHeader(key);
        res = validatetoken();
        String result = invocation.invoke();
        return result;
    }
    public boolean validatetoken() throws Exception {
        boolean status= false;
        properties();
        Connection con = DriverManager.getConnection(url, uname, password);
        String query = "SELECT * FROM token_id WHERE token=?";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1, value);
        ResultSet rs = st.executeQuery();
        status= rs.next();
        if (status){
            userid = rs.getInt(1);
        }
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
}
