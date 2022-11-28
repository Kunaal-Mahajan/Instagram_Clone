/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db_conn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kunaal
 */
public class HashTags {

    static ResultSet rs;
    List< String> postdesc = new ArrayList();
    List< String> comment = new ArrayList();
    HashMap<String, String> hashtagsdetails = new HashMap<>();
    List<String> hashtags = new ArrayList();
    Db_Conn db_conn;

    public HashTags() {
        try {
            comment.clear();
            hashtags.clear();
            rs = null;
            db_conn = new Db_Conn();
            rs = db_conn.loadAllPostdesc();
            while (rs.next()) {
                try {
                    postdesc.add(rs.getString("desc"));
                } catch (SQLException ex) {
                    Logger.getLogger(HashTags.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HashTags.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getHashTags() {
        try {
            Db_Conn.stmt.execute("TRUNCATE TABLE `hashtags`;");
            postdesc.forEach(desc -> {
                String Commentstr = desc;
                String[] words = Commentstr.split(" ");
                settags(words);
            });
            for (String hashtag : hashtags) {
                int count = 0;
                for (String hashtag1 : hashtags) {
                    if (hashtag.equals(hashtag1)) {
                        count++;
                    }
                }
                if (!hashtagsdetails.containsKey(hashtag)) {
                    hashtagsdetails.put(hashtag, "" + count);
                }
            }

            for (Map.Entry<String, String> entry : hashtagsdetails.entrySet()) {
                Object key = entry.getKey();
                Object val = entry.getValue();
//                System.out.println("key :-"+key.toString()+"  val :-"+val.toString());
                db_conn.setTags(key.toString(), val.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(HashTags.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void settags(String[] words) {
        for (String word : words) {
            if (!word.isEmpty()) {
                if (word.charAt(0) == '#') {
                    hashtags.add(word);
//                    if (!hashtags.containsKey(word.toLowerCase())) {
//                        hashtags.put(word, "1");}
////                    } else {
//                        hashtags.put(word, "" + (Integer.parseInt(hashtags.get(word)) + 1));
//                    }
                }
            }
        }
    }

}
