/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kunaal
 */
public class Db_Conn {

    // data variables
    final static String USER = "root";
    final static String DB_NAME = "insta_hits_algo_8";
    final static String PASSWORD = "123456";
    public final static String FileDrive = "D:";
    public static String USER_ID, USER_NAME = "", POST_COUNT = "", PROFILE_URL = "", FOLLOWING = "", FOLLOWER = "";
//main variables
    public static Connection dbconn;
    public static Statement stmt;
    public static ResultSet rs;

    public static List<UserPostData> user_feed_url = new ArrayList();
    public static List<UserPostData> user_post_url = new ArrayList();
    public static List<userComment> postcomment = new ArrayList();

    static String query = "";

    public Db_Conn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbconn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME + "", "" + USER + "", "" + PASSWORD + "");
            stmt = dbconn.createStatement();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getUserProfUrl(String userID) {
        rs = null;
        try {
            query = "SELECT profileurl FROM `userdata` WHERE user_id = '" + userID + "';";
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public void loadUserPostData(String user_id) {
        user_feed_url.clear();
        UserPostData userpost;
        try {
            query = "SELECT * FROM post WHERE user_id='" + user_id + "';";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                userpost = new UserPostData();
                userpost.setPost_id(rs.getString("post_id"));
                userpost.setUser_id(rs.getString("user_id"));
                userpost.setUser_post_url(FileDrive + rs.getString("user_post_url"));
                userpost.setPost_desc(rs.getString("desc"));
                userpost.setHashtags(rs.getString("hashtags"));
                user_feed_url.add(userpost);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadFeedPostData() {
        user_post_url.clear();
        UserPostData userpost;
        try {
            query = "SELECT * FROM post;";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                userpost = new UserPostData();
                userpost.setPost_id(rs.getString("post_id"));
                userpost.setUser_id(rs.getString("user_id"));
                userpost.setUser_post_url(FileDrive + rs.getString("user_post_url"));
                userpost.setPost_desc(rs.getString("desc"));
                userpost.setHashtags(rs.getString("hashtags"));
                user_post_url.add(userpost);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkLogins(String userId, String userPassword) {
        boolean result = false;
        try {
            query = "SELECT * FROM `userlogins`";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("user_id").equals(userId) && rs.getString("user_password").equals(userPassword)) {
                    USER_ID = rs.getString("user_id");
                    loadUserPostData(USER_ID);
                    getPersonalData(USER_ID);
                    result = true;

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public void getpostData(String post_url) {
        post_url = post_url.replace("\\", "\\\\");
        String user_id = "", user_pro_img = "";
        try {
            query = "SELECT post_id FROM post WHERE user_post_url = '" + post_url + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                user_id = rs.getString("post_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
//        Insta_feedController.getData(user_id, user_pro_img);
    }

    private void getPersonalData(String user_id) {
        try {
            query = "select * from userdata where user_id = '" + user_id + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                USER_NAME = rs.getString("user_name");
                FOLLOWER = rs.getString("userfollowingcount");
                FOLLOWING = rs.getString("userfollowerount");
                POST_COUNT = rs.getString("postcount");
                PROFILE_URL = rs.getString("profileurl");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getPostData() throws SQLException {
        ResultSet temp;
        query = "SELECT * FROM `post` WHERE user_id='" + USER_ID + "'";
        temp = stmt.executeQuery(query);
        return temp;
    }

    public void getpostURL() throws SQLException {
//        post_url.clear();
        rs = getPostData();
        while (rs.next()) {
//            post_url.add(FileDrive + rs.getString("user_feed_url"));
        }
    }

    public boolean setRegistation(String user_id, String user_name, String user_pass) {
        try {
            query = "INSERT INTO `userlogins`(`user_id`, `user_password`, `user_name`)VALUES('" + user_id + "', '" + user_pass + "', '" + user_name + "');";

            stmt.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String getpostID(String postURL) {
        String postid = "";
        postURL = postURL.replace("\\", "\\\\");
        try {
            query = "SELECT post_id FROM post WHERE user_post_url = '" + postURL + "'";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                postid = rs.getString("post_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return postid;
    }

    public boolean setpostComment(String user_id, String post_id, String comment) {
        try {
            query = "INSERT INTO `comment` \n"
                    + "	( \n"
                    + "	`user_id`, \n"
                    + "	`post_id`, \n"
                    + "	`comment`\n"
                    + "	)\n"
                    + "	VALUES\n"
                    + "	('" + user_id + "', \n"
                    + "	'" + post_id + "', \n"
                    + "	'" + comment + "'\n"
                    + "	)";
            stmt.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void getComment(String post_id) {
        postcomment.clear();
        userComment usercomment;
        try {
            query = "SELECT * FROM COMMENT WHERE post_id=" + post_id;
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                usercomment = new userComment();
                usercomment.setPost_id(rs.getString("post_id"));
                usercomment.setUser_id(rs.getString("user_id"));
                usercomment.setComment(rs.getString("comment"));
                postcomment.add(usercomment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void upLoadPost(String USER_ID, String FILEPATH, String hashtags, String desctext) {
        try {
            FILEPATH = FILEPATH.substring(2).replace("\\", "\\\\");
            System.out.println(FILEPATH);
            query = "INSERT INTO `post` \n"
                    + "	(`user_id`, \n"
                    + "	`user_post_url`, \n"
                    + "	`hashtags`, \n"
                    + "	`desc`\n"
                    + "	)\n"
                    + "	VALUES\n"
                    + "	('" + USER_ID + "', \n"
                    + "	'" + FILEPATH + "', \n"
                    + "	'" + hashtags + "', \n"
                    + "	'" + desctext + "'\n"
                    + "	);";
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet loadAllComment() {
        try {
            query = "SELECT * FROM COMMENT;";
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public ResultSet loadAllPostdesc() {
        try {
            query = "SELECT * FROM post;";
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public boolean checkTags(String tag) {

        boolean check = false;
        try {
            tag = tag.toLowerCase();
            query = "SELECT * FROM `hashtags` ;";
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                check = rs.getString("hashtags").equals(tag);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    public ResultSet getAllTags() {
        try {
            query = "select * from hashtags;";
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    public ResultSet getTag() {
        try {
            query = "SELECT * FROM hashtags ORDER BY tags_count DESC LIMIT 0,20;";
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public void updateTagCount(String tag, String count) {
        try {
            query = "UPDATE `hashtags` SET `tags_count` = '" + count + "' WHERE` hashtags` = '" + tag + "' ;";
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTags(String tag, String count) {
        try {
            query = "INSERT INTO `hashtags` (`hashtags`,`tags_count`) VALUES ('" + tag + "','" + count + "');";
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setpostData(String userid, String userposturl, String hashtags, String postdesc) {
        try {
            query = "INSERT INTO `post`(`user_id`,`user_post_url`,`hashtags`,`desc`) VALUES ('" + userid + "','" + userposturl + "','" + hashtags + "','" + postdesc + "');";
            stmt.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(Db_Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
