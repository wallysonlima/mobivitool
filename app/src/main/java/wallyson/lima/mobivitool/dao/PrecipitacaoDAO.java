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
                "d29 + d30 + d31) / 31), 2) as media FROM `precipitacao` WHERE prefixo='" + prefixo + ".dat' group by ano, mes";

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
                "d29 + d30 + d31) / 31), 2) as media FROM `precipitacao` WHERE prefixo='" + prefixo + ".dat' " +
                "and ano= '" + ano + "' group by mes;";

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

    public ArrayList<Float> getMediaChuvaPorMes(String ano, String mes) {
        DB con = new DB();
        String sql = "SELECT " +
                "truncate(((d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8 + d9 + d10 + d11 + d12 + d13 + d14 + " +
                "d15 + d16 + d17 + d18 + d19 + d20 + d21 + d22 + d23 + d24 + d25 + d26 + d27 + d28 + " +
                "d29 + d30 + d31) / 31), 2) as media FROM `precipitacao` WHERE prefixo IN (" +
                "'D4-004.dat', 'B7-047.dat', " +
                "'C5-008.dat', 'D7-020.dat', 'E3-074.dat', 'D5-080.dat', 'B5-002.dat', 'D6-001.dat', 'D5-019.dat', " +
                "'D3-002.dat', 'E5-047.dat', 'E3-038.dat', 'C8-043.dat', 'B4-001.dat', 'E3-002.dat', 'E4-135.dat', 'E4-023.dat', 'C5-025.dat', " +
                "'B7-008.dat', 'E3-025.dat', 'D4-064.dat', 'D6-010.dat', 'E3-264.dat', 'D4-002.dat', 'D8-003.dat', " +
                "'F4-004.dat', 'C4-034.dat', 'E3-041.dat', 'C4-019.dat', 'C3-031.dat', 'B6-014.dat', 'D2-021.dat', 'E3-003.dat', 'E2-045.dat', " +
                "'E4-056.dat', 'E2-022.dat', 'B6-032.dat') " +
                "and ano= '" + ano + "' and mes='" + mes + "';" ;

        ArrayList<Float> pre = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    pre.add(rs.getFloat("media"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.desconecta();
        }

        return pre;
    }

    public int getMes(String prefixo, String ano) {
        DB con = new DB();
        String sql = "SELECT COUNT(mes) as qtde FROM `precipitacao` WHERE prefixo='" + prefixo + "'" +
                " and ano='" + ano + "';";

        int mes = 0;
        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    mes = rs.getInt("qtde");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.desconecta();
        }

        return mes;
    }
}
