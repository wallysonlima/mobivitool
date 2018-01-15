package wallyson.lima.mobivitool.model;

/**
 * Created by wlima on 1/10/18.
 */

public class Precipitacao {
    String prefixo, ano, mes;
    Float media;

    public Precipitacao(String prefixo, String ano, String mes, Float media) {
        this.prefixo = prefixo;
        this.ano = ano;
        this.mes = mes;
        this.media = media;
    }

    public Precipitacao(Float media) {
        this.media = media;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(String prefixo) {
        this.prefixo = prefixo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Float getMedia() {
        return media;
    }

    public void setMedia(Float media) {
        this.media = media;
    }
}
