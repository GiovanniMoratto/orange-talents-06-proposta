package br.com.zupacademy.giovannimoratto.desafioproposta.Bloqueio;

import br.com.zupacademy.giovannimoratto.desafioproposta.cartao.CartaoModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @Author giovanni.moratto
 */
public class BloqueioRequest {

    private final String ipCliente;
    private final String userAgent;

    public BloqueioRequest(String ipCliente, String userAgent) {
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public BloqueioModel toModel(Optional <CartaoModel> cartao, HttpServletRequest request) {
        return new BloqueioModel(cartao,request.getRemoteAddr(), request.getHeader("User-Agent"));
    }
}
