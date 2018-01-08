package wallyson.lima.mobivitool.factory_method;

/**
 * Created by wlima on 1/8/18.
 */

public class _Default {
    protected String _mensagem;
    protected boolean _status;

    public _Default() {
        this._mensagem = "";
        this._status = true;
    }

    public boolean is_status() {
        return _status;
    }

    public void set_status(boolean _status) {
        this._status = _status;
    }

    public String get_mensagem() {
        return _mensagem;
    }

    public void set_mensagem(String _mensagem) {
        this._mensagem = _mensagem;
    }
}
