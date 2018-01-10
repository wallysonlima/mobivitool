package wallyson.lima.mobivitool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import wallyson.lima.mobivitool.factory_method.DB;

/**
 * Created by wlima on 1/8/18.
 */

public class PostoDAO {
    public ArrayList<String> getPrefixo() {
        DB con = new DB();
        String sql = "SELECT DISTINCT prefixo FROM `posto`;";

        ArrayList<String> pre = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    pre.add(rs.getString("prefixo"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.desconecta();
        }

        return pre;
    }
}
