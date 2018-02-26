/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 *
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/rest/EntityResource.java.e.vm
 */
package com.willbe.giftapp.rest;

import com.willbe.giftapp.dto.AppWidgetDTO;
import com.willbe.giftapp.dto.AppWidgetDTOService;
import com.willbe.giftapp.dto.support.PageRequestByExample;
import com.willbe.giftapp.dto.support.PageResponse;
import com.willbe.giftapp.repository.AppWidgetRepository;
import com.willbe.giftapp.rest.support.AutoCompleteQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping("/api/appWidgets")
public class AppWidgetResource {

    private final Logger log = LoggerFactory.getLogger(AppWidgetResource.class);

    @Inject
    private AppWidgetRepository appWidgetRepository;
    @Inject
    private AppWidgetDTOService appWidgetDTOService;

    @RequestMapping(value = "/saveConfigAndGenApp", method = POST)
    public ResponseEntity saveConfigAndGenApp(@RequestBody List<AppWidgetDTO> appWidgetDTOS) {
        return ResponseEntity.ok().body(true);
    }

    /**
     * Find by id AppWidget.
     */
    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AppWidgetDTO> findById(@PathVariable Integer id) throws URISyntaxException {

        log.debug("Find by id AppWidget : {}", id);

        return Optional.ofNullable(appWidgetDTOService.findOne(id))
                .map(appWidgetDTO -> new ResponseEntity<>(appWidgetDTO, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Update AppWidget.
     */
    @RequestMapping(value = "/", method = PUT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AppWidgetDTO> update(@RequestBody AppWidgetDTO appWidgetDTO) throws URISyntaxException {

        log.debug("Update AppWidgetDTO : {}", appWidgetDTO);

        if (!appWidgetDTO.isIdSet()) {
            return create(appWidgetDTO);
        }

        AppWidgetDTO result = appWidgetDTOService.save(appWidgetDTO);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Create a new AppWidget.
     */
    @RequestMapping(value = "/", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AppWidgetDTO> create(@RequestBody AppWidgetDTO appWidgetDTO) throws URISyntaxException {

        log.debug("Create AppWidgetDTO : {}", appWidgetDTO);

        if (appWidgetDTO.isIdSet()) {
            return ResponseEntity.badRequest().header("Failure", "Cannot create AppWidget with existing ID").body(null);
        }

        AppWidgetDTO result = appWidgetDTOService.save(appWidgetDTO);

        return ResponseEntity.created(new URI("/api/appWidgets/" + result.id)).body(result);
    }

    /**
     * Find a Page of AppWidget using query by example.
     */
    @RequestMapping(value = "/page", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<AppWidgetDTO>> findAll(@RequestBody PageRequestByExample<AppWidgetDTO> prbe) throws URISyntaxException {
        PageResponse<AppWidgetDTO> pageResponse = appWidgetDTOService.findAll(prbe);
        return new ResponseEntity<>(pageResponse, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Auto complete support.
     */
    @RequestMapping(value = "/complete", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppWidgetDTO>> complete(@RequestBody AutoCompleteQuery acq) throws URISyntaxException {

        List<AppWidgetDTO> results = appWidgetDTOService.complete(acq.query, acq.maxResults);

        return new ResponseEntity<>(results, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Delete by id AppWidget.
     */
    @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) throws URISyntaxException {

        log.debug("Delete by id AppWidget : {}", id);

        try {
            appWidgetRepository.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception x) {
            // todo: dig exception, most likely org.hibernate.exception.ConstraintViolationException
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}