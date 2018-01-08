package wallyson.lima.mobivitool.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by wlima on 1/8/18.
 */

public class DB extends _Default implements Runnable {
    private Connection conn;
    //private String host = "192.168.1.111";
    private String host = "192.168.0.120";
    private String db = "mobivitool";
    private int port = 3306;
    private String user = "root";
    private String password = "Unicamp2011&";
    private String url = "jdbc:mysql://%s:%d/%s";

    public DB() {
        super();
        this.url = String.format(this.url, this.host, this.port, this.db);

        this.conecta();
        this.desconecta();
    }

    @Override
    public void run() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.url, this.user, this.password);
        } catch(Exception e) {
            this._mensagem = e.getMessage();
            this._status = false;
        }
    }

    public void conecta() {
        Thread thread = new Thread(this);
        thread.start();

        try {
            thread.join();
        } catch(Exception e) {
            this._mensagem = e.getMessage();
            this._status = false;
        }

    }

    public void desconecta() {
        if ( this.conn != null ) {
            try {
                this.conn.close();
            } catch(Exception e) {

            } finally {
                this.conn = null;
            }
        }
    }

    public ResultSet select(String query){
        this.conecta();
        ResultSet resultSet = null;

        try {
            resultSet = new ExecuteDB(this.conn, query).execute().get();
        } catch(Exception e) {
            this._status = false;
            this._mensagem = e.getMessage();
        }

        return resultSet;
    }

    public void execute(String query){
        this.conecta();

        try {
            new ExecuteDB(this.conn, query).execute_Update();
        } catch(Exception e) {
            this._status = false;
            this._mensagem = e.getMessage();
        }
    }

    public Connection getConn() {
        return conn;
    }
}
