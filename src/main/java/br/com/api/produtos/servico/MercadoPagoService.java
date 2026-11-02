package br.com.api.produtos.servico;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MercadoPagoService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    @PostConstruct
    public void init() {
        // Inicializa o SDK do Mercado Pago com o Token de Teste
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    public String criarPreferenciaPagamento(Map<String, Object> dadosPedido) throws MPException, MPApiException {
        // Cria os itens da preferência baseados no pedido
        List<PreferenceItemRequest> items = new ArrayList<>();
        
        // Exemplo básico de item para o Checkout Pro do Mercado Pago
        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .title("Pedido Vitrine Delas")
                .quantity(1)
                .unitPrice(new BigDecimal(dadosPedido.get("valorTotal").toString()))
                .currencyId("BRL")
                .build();

        items.add(itemRequest);

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        // Retorna o link de pagamento (initPoint) para onde o cliente será redirecionado
        return preference.getInitPoint();
    }
}