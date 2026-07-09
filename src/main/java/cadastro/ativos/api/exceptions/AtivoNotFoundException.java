package cadastro.ativos.api.exceptions;

public class AtivoNotFoundException extends RuntimeException {
    public AtivoNotFoundException(String message) {
        super(message);
    }
}
