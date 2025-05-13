package org.spdgrupo.lab4tp45api.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import jakarta.annotation.PostConstruct;
import org.spdgrupo.lab4tp45api.model.PreferenceMP;
import org.spdgrupo.lab4tp45api.model.entity.DetallePedido;
import org.spdgrupo.lab4tp45api.model.entity.Instrumento;
import org.spdgrupo.lab4tp45api.model.entity.Pedido;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    private String testToken = "APP_USR-2825980193305398-051118-5a14ef889bb1615f3eb307c0afe56eea-2432010473";

    private String sucessUrl = "https://i.pinimg.com/736x/34/0d/5e/340d5eecf76224e9440d4240afa16efc.jpg";
    private String pendingUrl = "https://i.pinimg.com/736x/1b/79/10/1b7910e231d6fc2c13621dd3d58a30e7.jpg";
    private String failureUrl = "https://i.pinimg.com/736x/ea/40/39/ea4039270cca995480b5ebe42511007e.jpg";

    @PostConstruct
    public void initMercadoPagoConfig() {
        MercadoPagoConfig.setAccessToken(testToken);
    }

    public PreferenceMP createPreference(Pedido pedido) throws MPException, MPApiException {
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (DetallePedido detallePedido : pedido.getDetallePedidos()) {
            PreferenceItemRequest itemRequest = getItemRequest(detallePedido);
            items.add(itemRequest);
        }

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .autoReturn("approved")
                .additionalInfo("¡Gracias por comprar en El Buen Sabor!")
                .backUrls(
                        PreferenceBackUrlsRequest.builder()
                                .success(sucessUrl)
                                .pending(pendingUrl)
                                .failure(failureUrl)
                                .build()
                )
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return PreferenceMP.builder()
                .id(preference.getId())
                .preferenceId(preference.getId())
                .initPoint(preference.getInitPoint())
                .build();
    }

    private PreferenceItemRequest getItemRequest(DetallePedido detallePedido) {
        Instrumento instrumento = detallePedido.getInstrumento();

        return PreferenceItemRequest.builder()
                .id(instrumento.getId().toString())
                .title(instrumento.getInstrumento())
                .description(instrumento.getDescripcion())
                .pictureUrl(instrumento.getImagen())
                .quantity(detallePedido.getCantidad())
                .currencyId("ARS")
                .unitPrice(BigDecimal.valueOf(instrumento.getPrecio()))
                .build();
    }
}
