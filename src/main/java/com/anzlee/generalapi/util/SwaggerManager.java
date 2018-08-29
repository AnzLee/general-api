/**
 * @author li.liangzhe
 * @create 2018-02-01 10:29
 **/
package com.anzlee.generalapi.util;

import com.anzlee.generalapi.pojo.DefaultRequestHandlerCombiner;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.RequestHandler;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.DocumentationPlugin;
import springfox.documentation.spi.service.RequestHandlerCombiner;
import springfox.documentation.spi.service.RequestHandlerProvider;
import springfox.documentation.spi.service.contexts.Defaults;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spi.service.contexts.DocumentationContextBuilder;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.DefaultConfiguration;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;
import springfox.documentation.spring.web.scanners.ApiDocumentationScanner;

import javax.servlet.ServletContext;
import java.util.List;

import static com.google.common.collect.FluentIterable.from;
import static springfox.documentation.builders.BuilderDefaults.nullToEmptyList;
import static springfox.documentation.spi.service.contexts.Orderings.pluginOrdering;

@Component
public class SwaggerManager {
    private static final Logger log = LoggerFactory.getLogger(SwaggerManager.class);
    private final DocumentationPluginsManager documentationPluginsManager;
    private final List<RequestHandlerProvider> handlerProviders;
    private final DocumentationCache scanned;
    private final ApiDocumentationScanner resourceListing;
    private final DefaultConfiguration defaultConfiguration;

    @Autowired(required = false)
    private RequestHandlerCombiner combiner;
    @Autowired(required = false)
    private List<AlternateTypeRuleConvention> typeConventions;

    @Autowired
    public SwaggerManager(
            DocumentationPluginsManager documentationPluginsManager,
            List<RequestHandlerProvider> handlerProviders,
            DocumentationCache scanned,
            ApiDocumentationScanner resourceListing,
            TypeResolver typeResolver,
            Defaults defaults,
            ServletContext servletContext) {

        this.documentationPluginsManager = documentationPluginsManager;
        this.handlerProviders = handlerProviders;
        this.scanned = scanned;
        this.resourceListing = resourceListing;
        this.defaultConfiguration = new DefaultConfiguration(defaults, typeResolver, servletContext);
    }

    private void scanDocumentation(DocumentationContext context) {
        scanned.addDocumentation(resourceListing.scan(context));
    }

    private DocumentationContext buildContext(DocumentationPlugin each) {
        return each.configure(defaultContextBuilder(each));
    }

    private Function<RequestHandlerProvider, ? extends Iterable<RequestHandler>> handlers() {
        return new Function<RequestHandlerProvider, Iterable<RequestHandler>>() {
            @Override
            public Iterable<RequestHandler> apply(RequestHandlerProvider input) {
                return input.requestHandlers();
            }
        };
    }

    private Function<AlternateTypeRuleConvention, List<AlternateTypeRule>> toRules() {
        return new Function<AlternateTypeRuleConvention, List<AlternateTypeRule>>() {
            @Override
            public List<AlternateTypeRule> apply(AlternateTypeRuleConvention input) {
                return input.rules();
            }
        };
    }

    private RequestHandlerCombiner combiner() {
        return Optional.fromNullable(combiner).or(new DefaultRequestHandlerCombiner());
    }

    private DocumentationContextBuilder defaultContextBuilder(DocumentationPlugin plugin) {
        DocumentationType documentationType = plugin.getDocumentationType();
        List<RequestHandler> requestHandlers = from(handlerProviders)
                .transformAndConcat(handlers())
                .toList();
        List<AlternateTypeRule> rules = from(nullToEmptyList(typeConventions))
                .transformAndConcat(toRules())
                .toList();
        return documentationPluginsManager
                .createContextBuilder(documentationType, defaultConfiguration)
                .rules(rules)
                .requestHandlers(combiner().combine(requestHandlers));
    }

    public void refreshSwagger(){
        List<DocumentationPlugin> plugins = pluginOrdering()
                .sortedCopy(documentationPluginsManager.documentationPlugins());
        for (DocumentationPlugin each : plugins) {
            DocumentationType documentationType = each.getDocumentationType();
            if (each.isEnabled()) {
                scanDocumentation(buildContext(each));
            } else {
                log.info("Skipping initializing disabled plugin bean {} v{}",
                        documentationType.getName(), documentationType.getVersion());
            }
        }
    }
}
