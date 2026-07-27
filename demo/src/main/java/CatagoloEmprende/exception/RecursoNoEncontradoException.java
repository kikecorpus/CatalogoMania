package CatagoloEmprende.exception;

/**
 * Se lanza cuando se busca una entidad por id (u otro criterio) y no existe.
 * La capturaremos más adelante en un @ControllerAdvice para devolver un 404
 * limpio en vez de un error 500 genérico.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
