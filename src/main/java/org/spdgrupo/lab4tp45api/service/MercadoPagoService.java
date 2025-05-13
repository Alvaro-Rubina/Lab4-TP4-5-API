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
import org.spdgrupo.lab4tp45api.model.dto.InstrumentoDTO;
import org.spdgrupo.lab4tp45api.model.dto.detallepedido.DetallePedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MercadoPagoService {

    @Autowired
    private InstrumentoService instrumentoService;

    private String testToken = "APP_USR-2825980193305398-051118-5a14ef889bb1615f3eb307c0afe56eea-2432010473";

    private String sucessUrl = "https://i.pinimg.com/736x/34/0d/5e/340d5eecf76224e9440d4240afa16efc.jpg";
    private String pendingUrl = "https://i.pinimg.com/736x/1b/79/10/1b7910e231d6fc2c13621dd3d58a30e7.jpg";
    private String failureUrl = "https://i.pinimg.com/736x/ea/40/39/ea4039270cca995480b5ebe42511007e.jpg";

    @PostConstruct
    public void initMercadoPagoConfig() {
        MercadoPagoConfig.setAccessToken(testToken);
    }

    public Preference createPreference(List<DetallePedidoDTO> detallePedidos) throws MPException, MPApiException {
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (DetallePedidoDTO detallePedido : detallePedidos) {
            PreferenceItemRequest itemRequest = getItemRequest(detallePedido);
            items.add(itemRequest);
        }

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .autoReturn("approved")
                .additionalInfo("La empresa no se hace cargo de nada que le perjudique 😏")
                .backUrls(
                        PreferenceBackUrlsRequest.builder()
                                .success(sucessUrl)
                                .pending(pendingUrl)
                                .failure(failureUrl)
                                .build()
                )
                .build();

        PreferenceClient client = new PreferenceClient();
        return client.create(preferenceRequest);
    }

    private PreferenceItemRequest getItemRequest(DetallePedidoDTO detallePedido) {
        InstrumentoDTO instrumento = instrumentoService.getInstrumentoById(detallePedido.getInstrumentoId());

        return PreferenceItemRequest.builder()
                .id(instrumento.getId().toString())
                .title(instrumento.getInstrumento())
                .description(instrumento.getDescripcion())
                .pictureUrl(instrumento.getImagen())
                .quantity(detallePedido.getCantidad())
                .currencyId("ARG") // o ARS ver cual es el id correcto
                .unitPrice(instrumento.getPrecio())
                .build();
    }
}
