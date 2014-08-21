package net.flower.ixmsxms.rest.api;

import net.flower.ixmsxms.IxmsxmsConfig;
import net.flower.ixmsxms.model.Child;
import net.flower.ixmsxms.model.Now;
import net.flower.ixmsxms.rest.IxmsxmsRestClient;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Put;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientErrorHandling;
import org.androidannotations.api.rest.RestClientRootUrl;
import org.androidannotations.api.rest.RestClientSupport;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@IxmsxmsRestClient
@Rest(rootUrl = IxmsxmsConfig.DEFAULT_API_ROOT_URL,converters = {FormHttpMessageConverter.class,
        GsonHttpMessageConverter.class})
public interface ChildRestClient extends RestClientErrorHandling, RestClientRootUrl, RestClientSupport {
    @Post("/child/add")
    Child addChild(Child childForAdd);

    @Get("/child/{userId}")
    Child getChild(Long userId);

    @Get("/part/now/test")
    Now getTest();


}