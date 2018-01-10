package wallyson.lima.mobivitool.model;

/**
 * Created by wlima on 1/10/18.
 */

public class Posto {
    String prefixo, nome, municipio, bacia, altitude, latitude, longitude, ano_ini, ano_fim, intervalo;

    public Posto(String prefixo, String nome, String municipio, String bacia, String altitude, String latitude, String longitude, String ano_ini, String ano_fim, String intervalo) {
        this.prefixo = prefixo;
        this.nome = nome;
        this.municipio = municipio;
        this.bacia = bacia;
        this.altitude = altitude;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ano_ini = ano_ini;
        this.ano_fim = ano_fim;
        this.intervalo = intervalo;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getBacia() {
        return bacia;
    }

    public void setBacia(String bacia) {
        this.bacia = bacia;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAno_ini() {
        return ano_ini;
    }

    public void setAno_ini(String ano_ini) {
        this.ano_ini = ano_ini;
    }

    public String getAno_fim() {
        return ano_fim;
    }

    public void setAno_fim(String ano_fim) {
        this.ano_fim = ano_fim;
    }

    public String getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(String intervalo) {
        this.intervalo = intervalo;
    }
}
