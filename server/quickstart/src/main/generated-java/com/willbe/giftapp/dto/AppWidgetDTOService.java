/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/dto/EntityDTOService.java.e.vm
 */
package com.willbe.giftapp.dto;

import com.willbe.giftapp.domain.AppWidget;
import com.willbe.giftapp.domain.AppWidget_;
import com.willbe.giftapp.domain.App_;
import com.willbe.giftapp.dto.support.PageRequestByExample;
import com.willbe.giftapp.dto.support.PageResponse;
import com.willbe.giftapp.repository.AppWidgetRepository;
import com.willbe.giftapp.repository.App_Repository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple DTO Facility for AppWidget.
 */
@Service
public class AppWidgetDTOService {

    @Inject
    private AppWidgetRepository appWidgetRepository;
    @Inject
    private App_DTOService app_DTOService;
    @Inject
    private App_Repository app_Repository;

    @Transactional(readOnly = true)
    public AppWidgetDTO findOne(Integer id) {
        return toDTO(appWidgetRepository.findOne(id));
    }

    @Transactional(readOnly = true)
    public List<AppWidgetDTO> complete(String query, int maxResults) {
        List<AppWidget> results = appWidgetRepository.complete(query, maxResults);
        return results.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PageResponse<AppWidgetDTO> findAll(PageRequestByExample<AppWidgetDTO> req) {
        Example<AppWidget> example = null;
        AppWidget appWidget = toEntity(req.example);

        if (appWidget != null) {
            ExampleMatcher matcher = ExampleMatcher.matching() //
                    .withMatcher(AppWidget_.rule.getName(), match -> match.ignoreCase().startsWith());

            example = Example.of(appWidget, matcher);
        }

        Page<AppWidget> page;
        if (example != null) {
            page = appWidgetRepository.findAll(example, req.toPageable());
        } else {
            page = appWidgetRepository.findAll(req.toPageable());
        }

        List<AppWidgetDTO> content = page.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), content);
    }

    /**
     * Save the passed dto as a new entity or update the corresponding entity if any.
     */
    @Transactional
    public AppWidgetDTO save(AppWidgetDTO dto) {
        if (dto == null) {
            return null;
        }

        final AppWidget appWidget;

        if (dto.isIdSet()) {
            AppWidget appWidgetTmp = appWidgetRepository.findOne(dto.id);
            if (appWidgetTmp != null) {
                appWidget = appWidgetTmp;
            } else {
                appWidget = new AppWidget();
                appWidget.setId(dto.id);
            }
        } else {
            appWidget = new AppWidget();
        }

        appWidget.setRule(dto.rule);


        if (dto.app == null) {
            appWidget.setApp(null);
        } else {
            App_ app = appWidget.getApp();
            if (app == null || (app.getId().compareTo(dto.app.id) != 0)) {
                appWidget.setApp(app_Repository.findOne(dto.app.id));
            }
        }

        return toDTO(appWidgetRepository.save(appWidget));
    }

    /**
     * Converts the passed appWidget to a DTO.
     */
    public AppWidgetDTO toDTO(AppWidget appWidget) {
        return toDTO(appWidget, 1);
    }

    /**
     * Converts the passed appWidget to a DTO. The depth is used to control the
     * amount of association you want. It also prevents potential infinite serialization cycles.
     *
     * @param appWidget
     * @param depth the depth of the serialization. A depth equals to 0, means no x-to-one association will be serialized.
     *              A depth equals to 1 means that xToOne associations will be serialized. 2 means, xToOne associations of
     *              xToOne associations will be serialized, etc.
     */
    public AppWidgetDTO toDTO(AppWidget appWidget, int depth) {
        if (appWidget == null) {
            return null;
        }

        AppWidgetDTO dto = new AppWidgetDTO();

        dto.id = appWidget.getId();
        dto.rule = appWidget.getRule();
        if (depth-- > 0) {
            dto.app = app_DTOService.toDTO(appWidget.getApp(), depth);
        }

        return dto;
    }

    /**
     * Converts the passed dto to a AppWidget.
     * Convenient for query by example.
     */
    public AppWidget toEntity(AppWidgetDTO dto) {
        return toEntity(dto, 1);
    }

    /**
     * Converts the passed dto to a AppWidget.
     * Convenient for query by example.
     */
    public AppWidget toEntity(AppWidgetDTO dto, int depth) {
        if (dto == null) {
            return null;
        }

        AppWidget appWidget = new AppWidget();

        appWidget.setId(dto.id);
        appWidget.setRule(dto.rule);
        if (depth-- > 0) {
            appWidget.setApp(app_DTOService.toEntity(dto.app, depth));
        }

        return appWidget;
    }
}