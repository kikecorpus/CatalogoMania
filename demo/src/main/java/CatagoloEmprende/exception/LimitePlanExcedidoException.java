package CatagoloEmprende.exception;

/**
 * Se lanza cuando una tienda intenta agregar más productos de los que
 * su plan actual permite (regla de negocio del MVP: 15 productos gratis).
 */
public class LimitePlanExcedidoException extends RuntimeException {

    public LimitePlanExcedidoException(String mensaje) {
        super(mensaje);
    }
}
