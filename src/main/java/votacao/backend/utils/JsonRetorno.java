package votacao.backend.utils;

import java.util.Map;

public class JsonRetorno {

    public static Object mensagem(String mensagem){
        return Map.of("mensagem", mensagem);
    }
}
