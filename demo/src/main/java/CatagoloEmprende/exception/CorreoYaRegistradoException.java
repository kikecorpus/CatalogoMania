package CatagoloEmprende.exception;

/**
 * Se lanza al intentar registrar una tienda con un correo que ya existe.
 */
public class CorreoYaRegistradoException extends RuntimeException {

    public CorreoYaRegistradoException(String mensaje) {
        super(mensaje);
    }
}
