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

    public ArrayList<String> getAno(String prefixo) {
        DB con = new DB();
        String sql = "SELECT ano_ini, ano_fim FROM `posto` where prefixo='" + prefixo + "';";

        ArrayList<String> ano = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    ano.add(rs.getString("ano_ini"));
                    ano.add(rs.getString("ano_fim"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.desconecta();
        }

        return ano;
    }

    public ArrayList<String> getNome(String prefixo1, String prefixo2, String prefixo3) {
        DB con = new DB();
        String sql = "SELECT nome FROM `posto` where prefixo IN('" + prefixo1 + "', '" +
                prefixo2 + "', '" + prefixo3 + "');";

        ArrayList<String> nome = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    nome.add(rs.getString("nome"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.desconecta();
        }

        return nome;
    }

}
