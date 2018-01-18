package wallyson.lima.mobivitool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import wallyson.lima.mobivitool.factory_method.DB;
import wallyson.lima.mobivitool.model.Posto;

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

    public String getPrefixoMunicipio(String municipio) {
        DB con = new DB();
        String sql = "SELECT DISTINCT prefixo FROM `posto` where municipio='" + municipio + "' ;";

        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    return rs.getString("prefixo");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.desconecta();
        }

        return null;
    }

    public String getMunicipioPrefixo(String prefixo) {
        DB con = new DB();
        String sql = "SELECT DISTINCT municipio FROM `posto` where prefixo='" + prefixo + "' ;";

        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    return rs.getString("municipio");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.desconecta();
        }

        return null;
    }

    public ArrayList<String> getMunicipio() {
        DB con = new DB();
        String sql = "SELECT DISTINCT municipio FROM `posto`;";

        ArrayList<String> pre = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    pre.add(rs.getString("municipio"));
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

    public ArrayList<Posto> getInfoPosto() {
        DB con = new DB();
        String sql = "SELECT prefixo, municipio, bacia, latitude, longitude FROM `posto` WHERE prefixo IN (" +
                "'D4-004', 'B7-047', " +
                "'C5-008', 'D7-020', 'E3-074', 'D5-080', 'B5-002', 'D6-001', 'D5-019', " +
                "'D3-002', 'E5-047', 'E3-038', 'C8-043', 'B4-001', 'E3-002', 'E4-135', 'E4-023', 'C5-025', " +
                "'B7-008', 'E3-025.dat', 'D4-064', 'D6-010', 'E3-264', 'D4-002', 'D8-003', " +
                "'F4-004', 'C4-034.dat', 'E3-041', 'C4-019', 'C3-031', 'B6-014', 'D2-021', 'E3-003', 'E2-045', " +
                "'E4-056', 'E2-022', 'B6-032') " + "GROUP BY prefixo;";

        ArrayList<Posto> postos = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = con.select(sql);

            if ( rs != null ) {
                while( rs.next() ) {
                    postos.add( new Posto(rs.getString("prefixo"),
                            rs.getString("municipio"), rs.getString("bacia"),
                            rs.getString("latitude"), rs.getString("longitude") ) );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.desconecta();
        }

        return postos;
    }

}
