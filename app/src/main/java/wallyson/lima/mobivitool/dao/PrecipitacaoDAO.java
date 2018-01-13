package wallyson.lima.mobivitool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import wallyson.lima.mobivitool.factory_method.DB;
import wallyson.lima.mobivitool.model.Precipitacao;

/**
 * Created by wlima on 1/8/18.
 */

public class PrecipitacaoDAO {

    public ArrayList<Precipitacao> getMediaChuvaMes(String prefixo) {
        DB con = new DB();
        String sql = "SELECT ano, mes, " +
                "truncate(((d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8 + d9 + d10 + d11 + d12 + d13 + d14 + " +
                "d15 + d16 + d17 + d18 + d19 + d20 + d21 + d22 + d23 + d24 + d25 + d26 + d27 + d28 + " +
                "d29 + d30 + d31) / 31), 2) as media FROM `precipitacao` WHERE prefixo=" + prefixo + " group by ano, mes";

        ArrayList<Precipitacao> pre = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    Precipitacao obj = new Precipitacao(prefixo,
                            rs.getString("ano"), rs.getString("mes"), rs.getFloat("media"));

                    pre.add(obj);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.desconecta();
        }

        return pre;
    }

    public ArrayList<Precipitacao> getMediaChuvaAno(String prefixo, String ano) {
        DB con = new DB();
        String sql = "SELECT ano, mes, " +
                "truncate(((d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8 + d9 + d10 + d11 + d12 + d13 + d14 + " +
                "d15 + d16 + d17 + d18 + d19 + d20 + d21 + d22 + d23 + d24 + d25 + d26 + d27 + d28 + " +
                "d29 + d30 + d31) / 31), 2) as media FROM `precipitacao` WHERE prefixo=" + prefixo + "" +
                "and ano= " + ano + " group by ano, mes";

        ArrayList<Precipitacao> pre = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    Precipitacao obj = new Precipitacao(prefixo,
                            ano, rs.getString("mes"), rs.getFloat("media"));

                    pre.add(obj);
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
